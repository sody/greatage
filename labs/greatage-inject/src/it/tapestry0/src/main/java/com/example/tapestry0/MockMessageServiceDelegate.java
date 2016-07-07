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

package com.example.tapestry0;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockMessageServiceDelegate implements MockMessageService {
	private final MockMessageService delegate;
	private String messageDecoration;

	public MockMessageServiceDelegate(final MockMessageService delegate, final String messageDecoration) {
		assert delegate != null;

		this.delegate = delegate;
		this.messageDecoration = messageDecoration;
	}

	public String generateMessage() {
		return messageDecoration + delegate.generateMessage();
	}
}
