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

import org.greatage.ioc.OrderedConfiguration;
import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.annotations.Contribute;
import org.greatage.ioc.annotations.Inject;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class MockGreatAgeModule {

	@Build(id = "GreatAgeMessageService")
	public MockMessageService buildGreatAgeMessageService(final List<String> messages) {
		return new MockMessageServiceImpl(messages);
	}

	@Build(id = "GreatAgeMessageServiceDelegate")
	public MockMessageService buildGreatAgeMessageServiceDelegate(
			@Inject(id = "GuiceMessageService") final MockMessageService messageService) {
		return new MockMessageServiceDelegate(messageService, "Invocation from greatage:");
	}

	@Contribute(id = "GreatAgeMessageService")
	public void contributeGreatAgeMessageService(final OrderedConfiguration<String> configuration) {
		configuration.add("Great", "great");
		configuration.add("Age", "age");
		configuration.add("Rulezz", "rulez");
	}
}
