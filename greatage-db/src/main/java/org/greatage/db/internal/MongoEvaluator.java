/*
 * Copyright (c) 2008-2014 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.db.internal;

import com.mongodb.MongoClientURI;
import org.greatage.db.ProcessExecutor;
import org.greatage.db.Evaluator;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class MongoEvaluator implements Evaluator {
    private static final String DEFAULT_COLLECTION = "changelog";
    private static final int DEFAULT_BATCH_SIZE = 10;

    private static final String SCRIPT_HEADER = "var changeSet;\n";
    private static final String SCRIPT_BODY = "// start change set #%2$s\n" +
            "changeSet = db.%1$s.findOne({_id: '%2$s'});\n" +
            "if (!changeSet) {\n\n" +
            "// BEGIN\n" +
            "%3$s" +
            "// END\n\n" +
            "  db.%1$s.save({_id: '%2$s', checkSum: '%4$s', author: '%5$s', comment: '%6$s'});\n" +
            "  db.getLastError();\n" +
            "} else if (!changeSet.checkSum) {\n" +
            "  db.%1$s.save({_id: '%2$s', checkSum: '%4$s', author: '%5$s', comment: '%6$s'});\n" +
            "  db.getLastError();\n" +
            "} else if (changeSet.checkSum !== '%4$s') {\n" +
            "  throw new Error('Invalid checksum for changeset \\'%2$s\\'. (actual: \\'%4$s\\', expected: \\'' + changeSet.checkSum + '\\')');\n" +
            "}";

    private final ProcessExecutor executor;
    private final MongoClientURI uri;
    private final String collection;

    public MongoEvaluator(final ProcessExecutor executor, final String uri) {
        this(executor, new MongoClientURI(uri));
    }

    public MongoEvaluator(final ProcessExecutor executor, final MongoClientURI uri) {
        this.executor = executor;
        this.uri = uri;
        this.collection = uri.getCollection() != null ? uri.getCollection() : DEFAULT_COLLECTION;
    }

    @Override
    public ChangeLog changeLog() {
        return new MongoChangeLog(DEFAULT_BATCH_SIZE);
    }

    private void evaluate(final String script) {
        final int exitCode = executor.create()
                .command("mongo", uri.getDatabase(), "--eval", script)
                .handler(new ProcessExecutor.ContentHandler() {
                    @Override
                    public void start(final List<String> command) {
                    }

                    @Override
                    public void end(final int returnCode) {
                    }

                    @Override
                    public void line(final String line) {
                        System.out.println(line);
                    }

                    @Override
                    public void error(final Throwable e) {
                        System.err.println(e.getMessage());
                    }
                }).execute();

        if (exitCode != 0) {
            throw new RuntimeException(String.format("Could not evaluate script: exit code is %d.", exitCode));
        }
    }

    public class MongoChangeLog implements ChangeLog {
        private final int batchSize;
        private StringBuilder batch = new StringBuilder(SCRIPT_HEADER);

        private int size;

        public MongoChangeLog(final int batchSize) {
            this.batchSize = batchSize;

            size = batchSize;
        }

        @Override
        public ChangeSet changeSet(final String id) {
            return new MongoChangeSet(this, id);
        }

        @Override
        public ChangeLog flush() {
            evaluate(batch.toString());
            batch = new StringBuilder(SCRIPT_HEADER);
            size = batchSize;
            return this;
        }

        private MongoChangeLog apply(final String script) {
            batch.append('\n').append(script);
            size--;
            if (size <= 0) {
                flush();
            }
            return this;
        }
    }

    public class MongoChangeSet implements ChangeSet {
        private final MongoChangeLog changeLog;

        private final String id;
        private final StringBuilder scriptBuilder = new StringBuilder();

        private String author = "";
        private String comment = "";

        private MongoChangeSet(final MongoChangeLog changeLog, final String id) {
            this.changeLog = changeLog;
            this.id = id;
        }

        @Override
        public MongoChangeSet author(final String author) {
            this.author = author;
            return this;
        }

        @Override
        public MongoChangeSet comment(final String comment) {
            this.comment = comment;
            return this;
        }

        @Override
        public MongoChangeSet append(final String script) {
            this.scriptBuilder.append(script);
            return this;
        }

        @Override
        public MongoChangeLog apply() {
            final String script = scriptBuilder.toString();
            final String checkSum = InternalUtils.calculateCheckSum(id, script);
            final String fullScript = String.format(SCRIPT_BODY,
                    collection, id, script, checkSum, author, comment);

            return changeLog.apply(fullScript);
        }
    }
}
