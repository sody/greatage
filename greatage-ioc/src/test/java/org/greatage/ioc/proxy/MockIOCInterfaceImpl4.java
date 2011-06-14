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

package org.greatage.ioc.proxy;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockIOCInterfaceImpl4 implements MockIOCInterface {
	private final MockIOCInterface delegate;

	public MockIOCInterfaceImpl4(final MockIOCInterface delegate) {
		this.delegate = delegate;
	}

	public String say(final String message) {
		return delegate.say(message);
	}

	public String say() {
		return delegate.say();
	}
}
