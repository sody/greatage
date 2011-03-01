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

package org.greatage.ioc.mock;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockTalkServiceDelegate implements MockTalkService {
	private final MockTalkService delegate;
	private final String prefix;
	private final String suffix;

	public MockTalkServiceDelegate(final MockTalkService delegate, final String prefix, final String suffix) {
		this.delegate = delegate;
		this.prefix = prefix;
		this.suffix = suffix;
	}

	@Deprecated
	public String say() {
		return prefix + delegate.say() + suffix;
	}
}
