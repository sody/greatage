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
import org.greatage.db.Evaluator;
import org.greatage.db.ProcessExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 */
public class MongoChangeLog implements Evaluator.ChangeLog {
    private static final String DEFAULT_COLLECTION = "changelog";

    private static final String SCRIPT_HEADER = "// header\n" +
            "function processError(error) {\n" +
            "  if (error && error.err) {\n" +
            "    throw new Error('Error: ' + error.err);\n" +
            "  }\n" +
            "}\n\n" +
            "var changeSet;\n";
    private static final String SCRIPT_BODY = "// start change set #%2$s\n" +
            "changeSet = db.%1$s.findOne({_id: '%2$s'});\n" +
            "if (!changeSet) {\n" +
            "  db.resetError();\n" +
            "  (function () {\n\n" +
            "// --== BEGIN ==--\n" +
            "%3$s\n" +
            "// --==  END  ==--\n\n" +
            "  })();\n" +
            "  processError(db.getPrevError());\n" +
            "  db.%1$s.save({_id: '%2$s', checkSum: '%4$s', author: '%5$s', comment: '%6$s'});\n" +
            "  processError(db.getLastError());\n" +
            "} else if (!changeSet.checkSum) {\n" +
            "  db.%1$s.save({_id: '%2$s', checkSum: '%4$s', author: '%5$s', comment: '%6$s'});\n" +
            "  processError(db.getLastError());\n" +
            "} else if (changeSet.checkSum !== '%4$s') {\n" +
            "  throw new Error('Invalid checksum for changeset \\'%2$s\\'. (actual: \\'%4$s\\', expected: \\'' + changeSet.checkSum + '\\')');\n" +
            "}";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // change log settings
    private final ProcessExecutor executor;
    private final MongoClientURI uri;
    private final int batchSize;
    private final String collection;

    // change log runtime state
    private final List<String> ids;
    private StringBuilder batch = new StringBuilder(SCRIPT_HEADER);
    private int size;

    public MongoChangeLog(final ProcessExecutor executor, final MongoClientURI uri, final int batchSize) {
        this.executor = executor;
        this.uri = uri;
        this.batchSize = batchSize;

        collection = uri.getCollection() != null ? uri.getCollection() : DEFAULT_COLLECTION;
        ids = new ArrayList<String>(batchSize);
        batch = new StringBuilder(SCRIPT_HEADER);
        size = batchSize;
    }

    @Override
    public MongoChangeSet changeSet(final String id) {
        return new MongoChangeSet(this, id);
    }

    @Override
    public MongoChangeLog flush() {
        evaluate(ids, batch.toString());
        // reset
        ids.clear();
        batch = new StringBuilder(SCRIPT_HEADER);
        size = batchSize;
        return this;
    }

    private void apply(final String id, final String script) {
        ids.add(id);
        batch.append('\n').append(script);
        size--;
        if (size <= 0) {
            flush();
        }
    }

    private void evaluate(final List<String> ids, final String script) {
        logger.info("Evaluation changes: {}", ids);
        logger.debug("Script:\n{}", script);

        final StringBuilder out = new StringBuilder();
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
                        out.append(line).append('\n');
                    }

                    @Override
                    public void error(final Throwable e) {
                        logger.error("Error during script evaluation.", e);
                    }
                }).execute();

        if (exitCode != 0) {
            logger.error("Could not evaluate script. Output:\n{}", out);
            throw new RuntimeException(String.format("Could not evaluate script: exit code is %d.", exitCode));
        }
    }

    public class MongoChangeSet implements Evaluator.ChangeSet {
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
            changeLog.apply(id, String.format(SCRIPT_BODY,
                    collection, id, script, checkSum, author, comment));

            return changeLog;
        }
    }
}
