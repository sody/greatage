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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

/**
 * This class represents default process executor service implementation.
 *
 * @author Ivan Khalopik
 */
public abstract class AbstractProcessExecutor implements ProcessExecutor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ProcessExecutor.Options create() {
        return new Options();
    }

    protected int execute(final ProcessExecutor.Options options, final Runnable callback) {
        // execute command by command
        for (Command command : ((Options) options).commands) {
            final int returnCode = command.execute();

            // stop thread if process was finished with error code
            if (returnCode != 0) {
                return returnCode;
            }
        }

        if (callback != null) {
            callback.run();
        }
        return 0;
    }

    protected abstract Future<Integer> submit(final ProcessExecutor.Options options, final Runnable callback);

    private class Options implements ProcessExecutor.Options {
        private final List<Command> commands = new ArrayList<Command>();
        private Command currentCommand;

        @Override
        public Options command(final String command, final String... parameters) {
            currentCommand = new Command(command, parameters);

            commands.add(currentCommand);
            return this;
        }

        @Override
        public Options param(final String parameter) {
            currentCommand.params(parameter);
            return this;
        }

        @Override
        public Options param(final String parameter, final String value) {
            currentCommand.params(parameter, value);
            return this;
        }

        @Override
        public Options params(final String... parameters) {
            currentCommand.params(parameters);
            return this;
        }

        @Override
        public Options handler(final ContentHandler handler) {
            currentCommand.handler(handler);
            return this;
        }

        @Override
        public int execute() {
            return execute(null);
        }

        @Override
        public int execute(final Runnable callback) {
            return AbstractProcessExecutor.this.execute(this, callback);
        }

        @Override
        public Future<Integer> submit() {
            return submit(null);
        }

        @Override
        public Future<Integer> submit(final Runnable callback) {
            return AbstractProcessExecutor.this.submit(this, callback);
        }
    }

    private class Command {
        private final List<String> command;
        private ContentHandler handler;
        private String cachedToString;

        private Command(final String command, final String... parameters) {
            this.command = new ArrayList<String>(parameters.length + 1);
            this.command.add(command);

            params(parameters);
        }

        void handler(final ContentHandler handler) {
            this.handler = handler;
        }

        void params(final String... parameters) {
            Collections.addAll(this.command, parameters);
        }

        int execute() {
            logger.debug("Starting process: {}", this);
            // report handler about command execution
            if (handler != null) {
                handler.start(command);
            }

            int returnCode = 0;
            InputStream output = null;
            try {
                // start process
                final Process process = new ProcessBuilder(command).redirectErrorStream(true).start();
                // read process output
                output = process.getInputStream();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(output));
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.debug("{}: {}", this, line);
                    // report handler about new line in process output
                    if (handler != null) {
                        handler.line(line);
                    }
                    // notify about thread interruption immediately
                    // instead of waiting while output stream will end
                    if (Thread.interrupted()) {
                        process.destroy();
                        throw new InterruptedException("Process execution was interrupted.");
                    }
                }
                // wait until the process has terminated
                returnCode = process.waitFor();
            } catch (IOException e) {
                logger.error("Error reading stream", e);
                // report handler about error
                if (handler != null) {
                    handler.error(e);
                }
                // return code should not be 0
                returnCode = -1;
            } catch (InterruptedException e) {
                logger.warn("Cancelling process: {}", this);
                // report handler about error
                if (handler != null) {
                    handler.error(e);
                }
                Thread.currentThread().interrupt();
                // return code should not be 0
                returnCode = -1;
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        // do nothing
                    }
                }
            }
            // report handler about end of command execution
            if (handler != null) {
                handler.end(returnCode);
            }
            logger.debug("Finished process (exitcode={}): {}", returnCode, this);

            return returnCode;
        }

        @Override
        public String toString() {
            if (cachedToString == null) {
                final StringBuilder builder = new StringBuilder();
                final Iterator<String> iterator = command.iterator();
                if (iterator.hasNext()) {
                    builder.append(iterator.next());

                    while (iterator.hasNext()) {
                        builder.append(' ').append(iterator.next());
                    }
                }
                cachedToString = builder.toString();
            }
            return cachedToString;
        }
    }
}
