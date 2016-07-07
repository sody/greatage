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

package org.greatage.inject.guice;

import com.google.inject.Provider;
import org.greatage.inject.Marker;
import org.greatage.inject.ServiceLocator;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GreatAgeProvider<T> implements Provider<T> {
	private final ServiceLocator locator;
	private final Marker<T> marker;

	GreatAgeProvider(final ServiceLocator locator, final Marker<T> marker) {
		this.locator = locator;
		this.marker = marker;
	}

	public T get() {
		return locator.getService(marker);
	}
}
