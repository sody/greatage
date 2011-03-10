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

package org.greatage.ioc.proxy;

import java.util.List;

/**
 * This class represents utility producing proxy creation around object builders that instantiates the actual object
 * only as needed with all configured method advices.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ProxyFactory {

	/**
	 * Creates proxy around specified object builder that instantiates the actual object only as needed with all configured
	 * method interceptors.
	 *
	 * @param objectBuilder object builder
	 * @param interceptors  method interceptors
	 * @param <T>           type of proxy object
	 * @return proxy around specified object builder
	 * @throws RuntimeException if proxy creation is impossible or if an error occurs instantiating it
	 */
	<T> T createProxy(ObjectBuilder<T> objectBuilder, List<Interceptor> interceptors);
}
