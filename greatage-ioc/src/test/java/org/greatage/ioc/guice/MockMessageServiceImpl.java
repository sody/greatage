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

package org.greatage.ioc.guice;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class MockMessageServiceImpl implements MockMessageService {
	private final Random random = new Random();
	private final List<String> messages;

	public MockMessageServiceImpl(final String... messages) {
		this(Arrays.asList(messages));
	}

	public MockMessageServiceImpl(final List<String> messages) {
		assert messages != null;

		this.messages = messages;
	}

	public String generateMessage() {
		return messages.get(random.nextInt(messages.size()));
	}
}
