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

package org.greatage.db;

import com.mongodb.MongoClientURI;
import org.greatage.common.SimpleProcessExecutor;

/**
 * @author Ivan Khalopik
 */
public class MongoDatabaseManager implements DatabaseManager {
    private final Evaluator evaluator;
    private final MongoClientURI uri;

    public MongoDatabaseManager(final String uri) {
        this.evaluator = new MongoEvaluator(new SimpleProcessExecutor(), uri);
        this.uri = new MongoClientURI(uri);
    }

    @Override
    public void update(final String script) {
        evaluate(script);
    }

    private void evaluate(final String... changeLog) {
        for (String changeSet : changeLog) {
            final String checkSum = CheckSumUtils.calculateCheckSum(changeSet);
            evaluator.evaluate(
                    "var changeSet = db.changelog.findOne({_id: '" + checkSum + "'});\n" +
                            "if (!changeSet) {\n" +
                            changeSet + "\n" +
                            "db.changelog.insert({_id: '" + checkSum + "'});\n" +
                            "}");
        }
    }

    public static void main(String[] args) {
        final DatabaseManager manager = new MongoDatabaseManager("mongodb://localhost/test");
        manager.update("db.test_users.insert({ name: 'Test 3' });");
    }
}
