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

package org.greatage.ioc.logging;

/**
 * This interface represents utility producing Loggers for various logging APIs by their class or name.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface LoggerSource {

	/**
	 * Creates or retrieves logger based on specified class.
	 *
	 * @param clazz class
	 * @return logger instance
	 */
	Logger getLogger(Class clazz);

	/**
	 * Creates or retrieves logger based on specified name.
	 *
	 * @param name name
	 * @return logger instance
	 */
	Logger getLogger(String name);
}
