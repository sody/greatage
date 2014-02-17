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
import org.greatage.common.ProcessExecutor;
import org.greatage.db.Evaluator;

import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class MongoEvaluator implements Evaluator {
    private static final String DEFAULT_COLLECTION = "changelog";
    private static final String SCRIPT = "// start change set #%2$s\n" +
            "var changeSet = db.%1$s.findOne({_id: '%2$s'});\n" +
            "if (!changeSet) {\n" +
            "  %3$s\n" +
            "  db.%1$s.save({_id: '%2$s', checkSum: '%4$s', author: '%5$s', comment: '%6$s'});\n" +
            "} else if (!changeSet.checkSum) {\n" +
            "  db.%1$s.save({_id: '%2$s', checkSum: '%4$s', author: '%5$s', comment: '%6$s'});\n" +
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

    public MongoChangeSet changeSet(final String id) {
        return new MongoChangeSet(id);
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
                    }
                }).execute();

        if (exitCode != 0) {
            throw new RuntimeException(String.format("Could not evaluate script: exit code is %d.", exitCode));
        }
    }

    public class MongoChangeSet implements ChangeSet {
        private final String id;
        private final StringBuilder scriptBuilder = new StringBuilder();

        private String author = "";
        private String comment = "";

        private MongoChangeSet(final String id) {
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

        public MongoChangeSet append(final String script) {
            this.scriptBuilder.append(script);
            return this;
        }

        public MongoEvaluator apply() {
            final String script = scriptBuilder.toString();
            final String checkSum = InternalUtils.calculateCheckSum(id, script);
            final String fullScript = String.format(SCRIPT,
                    collection, id, script, checkSum, author, comment);
            evaluate(fullScript);

            return MongoEvaluator.this;
        }
    }
}
