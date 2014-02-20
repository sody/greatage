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

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

/**
 * @author Ivan Khalopik
 */
public interface Evaluator {

    Evaluator update(String script);

    Evaluator update(File script);

    Evaluator update(InputStream stream);

    Evaluator update(Reader reader);

    ChangeLog changeLog();

    public interface ChangeLog {

        ChangeSet changeSet(String id);

        ChangeLog flush();
    }

    public interface ChangeSet {

        ChangeSet author(String author);

        ChangeSet comment(String comment);

        ChangeSet append(String script);

        ChangeLog apply();
    }
}
