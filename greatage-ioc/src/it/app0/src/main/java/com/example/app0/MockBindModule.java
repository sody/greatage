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

package com.example.app0;

import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Build;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockBindModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(MockTalkServiceImpl1.class).named("service1");
		binder.bind(MockTalkService.class, MockTalkServiceImpl.class).named("service2");
	}

	@Build
	public MockMessageService buildMessageService() {
		return new MockMessageServiceImpl("hello");
	}
}
