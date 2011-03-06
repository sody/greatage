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

package org.greatage.ioc.mock.modules;

import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.annotations.Intercept;
import org.greatage.ioc.annotations.Order;
import org.greatage.ioc.mock.*;
import org.greatage.ioc.proxy.Invocation;
import org.greatage.ioc.proxy.MethodAdvice;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockInterceptModule {

	@Build
	public MockMessageService buildMessageService() {
		return new MockMessageServiceImpl("hello");
	}

	@Build(id = "talkService1")
	public MockTalkService buildTalkService(final MockMessageService messageService) {
		return new MockTalkServiceImpl(messageService);
	}

	@Build(id = "talkService2")
	public MockTalkService buildTalkService2(final MockMessageService messageService) {
		final MockTalkServiceImpl service = new MockTalkServiceImpl(messageService);
		return new MockTalkServiceDelegate(service, "[", "]");
	}

	@Intercept(service = MockTalkService.class)
	@Order("first")
	public MethodAdvice interceptTalkService() {
		return new MethodAdvice() {
			public Object advice(final Invocation invocation, final Object... parameters) throws Throwable {
				final Deprecated annotation = invocation.getAnnotation(Deprecated.class);
				if (annotation != null) {
					return "deprecated1:" + invocation.proceed(parameters);
				}
				return invocation.proceed(parameters);
			}
		};
	}

	@Intercept(service = MockTalkService.class)
	@Order(value = "second", constraints = "after:first")
	public MethodAdvice interceptTalkService2() {
		return new MethodAdvice() {
			public Object advice(final Invocation invocation, final Object... parameters) throws Throwable {
				final Deprecated annotation = invocation.getAnnotation(Deprecated.class);
				if (annotation != null) {
					return "deprecated2:" + invocation.proceed(parameters);
				}
				return invocation.proceed(parameters);
			}
		};
	}

}
