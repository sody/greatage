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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author Ivan Khalopik
 */
public class MongoDatabaseManager implements Evaluator {
    private static final int DEFAULT_BATCH_SIZE = 10;

    private static final String ID_PREFIX = "//!";
    private static final String AUTHOR_PREFIX = "//@";
    private static final String COMMENT_PREFIX = "//#";

    private final ProcessExecutor executor;
    private final MongoClientURI uri;
    private final int batchSize;

    public MongoDatabaseManager(final String uri) {
        this(new SimpleProcessExecutor(), uri);
    }

    public MongoDatabaseManager(final ProcessExecutor executor, final String uri) {
        this(executor, new MongoClientURI(uri), DEFAULT_BATCH_SIZE);
    }

    public MongoDatabaseManager(final ProcessExecutor executor, final MongoClientURI uri, final int batchSize) {
        this.executor = executor;
        this.uri = uri;
        this.batchSize = batchSize;
    }

    @Override
    public MongoDatabaseManager update(final String script) {
        final StringReader reader = new StringReader(script);
        try {
            process(reader);
            return this;
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse script.", e);
        } finally {
            reader.close();
        }
    }

    @Override
    public MongoDatabaseManager update(final File file) {
        InputStreamReader reader = null;
        try {
            reader = new FileReader(file);
            process(reader);
            return this;
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse script.", e);
        } finally {
            close(reader);
        }
    }

    @Override
    public MongoDatabaseManager update(final InputStream stream) {
        final InputStreamReader reader = new InputStreamReader(stream);
        try {
            process(reader);
            return this;
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse script.", e);
        } finally {
            close(reader);
        }
    }

    @Override
    public MongoDatabaseManager update(final Reader reader) {
        try {
            process(reader);
            return this;
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse script.", e);
        } finally {
            close(reader);
        }
    }

    @Override
    public MongoChangeLog changeLog() {
        return new MongoChangeLog(executor, uri, batchSize);
    }

    private void process(final Reader reader) throws IOException {
        final Evaluator.ChangeLog changeLog = changeLog();
        final LineNumberReader lineReader = new LineNumberReader(new BufferedReader(reader));

        Evaluator.ChangeSet changeSet = null;
        String line = lineReader.readLine();
        while (line != null) {
            // skip empty lines
            if (!line.trim().isEmpty()) {
                if (line.startsWith(ID_PREFIX)) {
                    // apply previous change
                    if (changeSet != null) {
                        changeSet.apply();
                    }
                    // create new change
                    changeSet = changeLog.changeSet(line.substring(ID_PREFIX.length()).trim());
                } else if (changeSet == null) {
                    throw new RuntimeException("Script should start with '//! id-of-change-set'.");
                } else if (line.startsWith(AUTHOR_PREFIX)) {
                    // setup author
                    changeSet.author(line.substring(AUTHOR_PREFIX.length()).trim());
                } else if (line.startsWith(COMMENT_PREFIX)) {
                    // setup comment
                    changeSet.comment(line.substring(COMMENT_PREFIX.length()).trim());
                } else {
                    // build script for current change
                    changeSet.append(line).append("\n");
                }
            }
            // next line
            line = lineReader.readLine();
        }

        // apply very last change
        if (changeSet != null) {
            changeSet.apply();
        }
        // flush changes
        changeLog.flush();
    }

    private void close(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }
}
