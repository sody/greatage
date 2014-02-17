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

import org.greatage.common.SimpleProcessExecutor;
import org.greatage.db.DatabaseManager;
import org.greatage.db.Evaluator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;

/**
 * @author Ivan Khalopik
 */
public class MongoDatabaseManager implements DatabaseManager {
    private final Evaluator evaluator;

    public MongoDatabaseManager(final String uri) {
        this.evaluator = new MongoEvaluator(new SimpleProcessExecutor(), uri);
    }

    @Override
    public void update(final String script) {
        final LineNumberReader reader = new LineNumberReader(new BufferedReader(new StringReader(script)));
        try {
            Evaluator.ChangeSet changeSet = null;

            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("//!")) {
                    // apply previous change
                    if (changeSet != null) {
                        changeSet.apply();
                    }
                    // create new change
                    changeSet = evaluator.changeSet(line.substring(3).trim());
                } else if (changeSet != null) {
                    // build script for current change
                    changeSet.append(line);
                }
                // next line
                line = reader.readLine();
            }

            // apply very last change
            if (changeSet != null) {
                changeSet.apply();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse script.", e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }

    public static void main(String[] args) {
        final DatabaseManager manager = new MongoDatabaseManager("mongodb://localhost/test.changes");
        manager.update("//! my-first-change\ndb.users.insert({ name: 'Test 3' });\n" +
                "//! second-change\ndb.users.insert({_id: 'Ded-Moroz'});\n");
    }
}
