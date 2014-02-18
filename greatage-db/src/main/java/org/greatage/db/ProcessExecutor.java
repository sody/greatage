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

import java.util.List;
import java.util.concurrent.Future;

/**
 * This interface represents service for working with OS-level process executions. It can define list of commands with
 * their own options and event handlers. All this commands will asynchronously be executed in separate thread with a
 * link to {@link java.util.concurrent.Future} representing pending completion of the executed task.
 *
 * @author Ivan Khalopik
 */
public interface ProcessExecutor {

    /**
     * Creates options for new process execution. This options will consist of one or more commands with their own
     * parameters and content handler. All this commands will be executed one by one in the same order as they were
     * declared.
     *
     * @return newly created process execution options, not {@code null}
     */
    Options create();

    /**
     * This interface represents process execution options that consists of ordered list of commands with their own
     * parameters and content handler. All this commands will be executed one by one in the same order as they were
     * declared.
     *
     * @author Ivan Khalopik
     */
    interface Options {

        /**
         * Adds new command to process execution options with optional parameters.
         *
         * @param command    command to be executed, not {@code null}
         * @param parameters optional command parameters, can be empty
         * @return the same instance of process execution options, not {@code null}
         */
        Options command(String command, String... parameters);

        /**
         * Adds a single parameter to currently declared command. At least one command should be declared before using
         * this method or it will result with {@link IllegalStateException}.
         *
         * @param parameter parameter to add to currently declared command, not {@code null}
         * @return the same instance of process execution options, not {@code null}
         * @throws IllegalStateException when trying to use this method while no commands exist
         */
        Options param(String parameter);

        /**
         * Adds a key-value parameter to currently declared command. At least one command should be declared before
         * using this method or it will result with {@link IllegalStateException}.
         *
         * @param parameter key parameter to add to currently declared command, not {@code null}
         * @param value     value parameter to add to currently declared command, not {@code null}
         * @return the same instance of process execution options, not {@code null}
         * @throws IllegalStateException when trying to use this method while no commands exist
         */
        Options param(String parameter, String value);

        /**
         * Adds specified parameters to currently declared command. At least one command should be declared before using
         * this method or it will result with {@link IllegalStateException}.
         *
         * @param parameters parameters to add to currently declared command, not {@code null}
         * @return the same instance of process execution options, not {@code null}
         * @throws IllegalStateException when trying to use this method while no commands exist
         */
        Options params(String... parameters);

        /**
         * Defines output content handler for currently declared command. This handler will be notified when command is
         * started, ended, new line appears in output stream and error occurs while executing command. At least one
         * command should be declared before using this method or it will result with {@link IllegalStateException}.
         *
         * @param handler command content handler, can be {@code null}, but it has no sense
         * @return the same instance of process execution options, not {@code null}
         * @throws IllegalStateException when trying to use this method while no commands exist
         */
        Options handler(ContentHandler handler);

        /**
         * Executes all defined commands in the same thread synchronously. They all will be executed one by one in the
         * same order they were declared.
         *
         * @return process exit code
         */
        int execute();

        /**
         * Executes all defined commands in the same thread synchronously. They all will be executed one by one in the
         * same order they were declared. After all commands successful completion defined callback will be executed.
         *
         * @param callback callback function to be executed after all commands successful completion, can be {@code
         *                 null}, but has no sense
         * @return process exit code
         */
        int execute(Runnable callback);

        /**
         * Executes all defined commands in a new thread. They all will be executed one by one in the same order they
         * were declared.
         *
         * @return a {@link java.util.concurrent.Future} representing pending completion of the executed task, not
         * {@code null}
         */
        Future<Integer> submit();

        /**
         * Executes all defined commands in a new thread. They all will be executed one by one in the same order they
         * were declared. After all commands successful completion defined callback will be executed.
         *
         * @param callback callback function to be executed after all commands successful completion, can be {@code
         *                 null}, but has no sense
         * @return a {@link java.util.concurrent.Future} representing pending completion of the executed task, not
         * {@code null}
         */
        Future<Integer> submit(Runnable callback);
    }

    /**
     * This interface represents command execution event handler that listens to start, end, new line in output stream,
     * error during invocation events for command it was declared for.
     *
     * @author Ivan Khalopik
     */
    interface ContentHandler {

        /**
         * Notifies underlying implementation that command execution was started.
         *
         * @param command command with its parameters that was started, not {@code null}
         */
        void start(List<String> command);

        /**
         * Notifies underlying implementation that command execution was ended.
         *
         * @param returnCode the exit value of the ended process, by convention, {@code 0} indicates normal termination
         */
        void end(int returnCode);

        /**
         * Notifies underlying implementation that new line in command output stream appears.
         *
         * @param line line in output stream, not {@code null}
         */
        void line(String line);

        /**
         * Notifies underlying implementation that error occurs while executing command.
         *
         * @param e throwable that was occurred while executing command, not {@code null}
         */
        void error(Throwable e);
    }
}
