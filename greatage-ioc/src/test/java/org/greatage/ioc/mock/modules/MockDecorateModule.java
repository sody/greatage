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

package org.greatage.ioc.mock.modules;

import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.annotations.Decorate;
import org.greatage.ioc.annotations.Order;
import org.greatage.ioc.mock.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockDecorateModule {

	@Build
	public MockMessageService buildMessageService() {
		return new MockMessageServiceImpl("hello");
	}

	@Build
	public MockTalkService buildTalkService(final MockMessageService messageService) {
		return new MockTalkServiceImpl(messageService);
	}

	@Decorate(MockTalkService.class)
	@Order("first")
	public MockTalkService decorateTalkService(final MockTalkService talkService) {
		return new MockTalkServiceDelegate(talkService, "[", "]");
	}

	@Decorate(MockTalkService.class)
	@Order(value = "second", constraints = "after:first")
	public MockTalkService redecorateTalkService(final MockTalkService talkService) {
		return new MockTalkServiceDelegate(talkService, "{", "}");
	}
}

