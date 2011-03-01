/*
 * Copyright 2011 Ivan Khalopik
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

package org.greatage.ioc.resource;

/**
 * This class represents {@link ResourceLocator} implementation that works with classpath resources.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ClasspathResourceLocator extends AbstractResourceLocator {

	/**
	 * Creates new instance of classpath resource locator with defined root classpath resource.
	 */
	public ClasspathResourceLocator() {
		super(new ClasspathResource(Thread.currentThread().getContextClassLoader(), null, "", null));
	}
}
