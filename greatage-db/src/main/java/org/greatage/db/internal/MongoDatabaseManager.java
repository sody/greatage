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
import org.greatage.common.SimpleProcessExecutor;
import org.greatage.db.DatabaseManager;
import org.greatage.db.Evaluator;

/**
 * @author Ivan Khalopik
 */
public class MongoDatabaseManager implements DatabaseManager {
    private static final String DEFAULT_COLLECTION = "changelog";
    private static final String SCRIPT_FORMAT = "// start change set #%2$s\n" +
            "var changeSet = db.%1$s.findOne({_id: '%2$s'});\n" +
            "if (!changeSet) {\n" +
            "  %3$s\n" +
            "}\n" +
            "if (!changeSet || !changeSet.checkSum) {\n" +
            "  db.%1$s.save({_id: '%2$s', checkSum: '%4$s'});\n" +
            "}";

    private final Evaluator evaluator;
    private final String collection;

    public MongoDatabaseManager(final String uri) {
        final MongoClientURI clientUri = new MongoClientURI(uri);

        this.evaluator = new MongoEvaluator(new SimpleProcessExecutor(), clientUri);
        this.collection = clientUri.getCollection() != null ? clientUri.getCollection() : DEFAULT_COLLECTION;
    }

    @Override
    public void update(final String script) {
        evaluate("1", script);
    }

    private void evaluate(final String id, final String script) {
        final String checkSum = InternalUtils.calculateCheckSum(id + "," + script);
        final String fullScript = String.format(SCRIPT_FORMAT, collection, id, script, checkSum);

        evaluator.evaluate(fullScript);
    }

    public static void main(String[] args) {
        final DatabaseManager manager = new MongoDatabaseManager("mongodb://localhost/test.changes");
        manager.update("db.test_users111.insert({ name: 'Test 3' });");
    }
}
