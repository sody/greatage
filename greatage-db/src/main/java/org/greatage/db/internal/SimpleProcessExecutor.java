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

import org.greatage.db.ProcessExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Simple JDK-powered implementation of {@code AbstractProcessExecutor}.
 *
 * @author Ivan Khalopik
 * @see AbstractProcessExecutor
 * @see org.greatage.db.ProcessExecutor
 */
public class SimpleProcessExecutor extends AbstractProcessExecutor {
    private static final int MAX_EXECUTORS = 10;

    private ExecutorService executor;

    public SimpleProcessExecutor() {
        executor = Executors.newFixedThreadPool(MAX_EXECUTORS);
    }

    public SimpleProcessExecutor(int maxExecutors) {
        executor = Executors.newFixedThreadPool(maxExecutors > 0 ? maxExecutors : MAX_EXECUTORS);
    }

    @Override
    protected Future<Integer> submit(final ProcessExecutor.Options options, final Runnable callback) {
        return executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return execute(options, callback);
            }
        });
    }
}