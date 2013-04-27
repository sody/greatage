/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
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

package org.greatage.domain.objectify;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyOpts;
import org.greatage.domain.internal.AbstractSessionManager;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ObjectifySessionManager extends AbstractSessionManager<Objectify> {
    private final ObjectifyFactory objectifyFactory;
    private final ObjectifyOpts options;

    public ObjectifySessionManager(final ObjectifyFactory objectifyFactory, final ObjectifyOpts options) {
        this.objectifyFactory = objectifyFactory;
        this.options = options;
    }

    @Override
    protected Objectify openSession() {
        return objectifyFactory.begin(options);
    }

    @Override
    protected void flushSession(final Objectify session) {
        // do nothing
    }

    @Override
    protected void closeSession(final Objectify session) {
        // do nothing
    }
}
