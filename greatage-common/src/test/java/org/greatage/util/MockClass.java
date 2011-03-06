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

package org.greatage.util;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockClass {
	private final String parameter1;
	private final boolean parameter2;
	private final CharSequence parameter3;

	public MockClass() {
		this(null);
	}

	public MockClass(String parameter1) {
		this(parameter1, false);
	}

	public MockClass(String parameter1, boolean parameter2) {
		this(parameter1, parameter2, (CharSequence) null);
	}

	private MockClass(String parameter1, String parameter3) {
		this(parameter1, false, (CharSequence) parameter3);
	}

	public MockClass(String parameter1, boolean parameter2, String parameter3) {
		throw new IllegalStateException();
	}

	public MockClass(String parameter1, boolean parameter2, CharSequence parameter3) {
		this.parameter1 = parameter1;
		this.parameter2 = parameter2;
		this.parameter3 = parameter3;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final MockClass mockClass = (MockClass) o;
		return parameter2 == mockClass.parameter2 &&
				!(parameter1 != null ? !parameter1.equals(mockClass.parameter1) : mockClass.parameter1 != null) &&
				!(parameter3 != null ? !parameter3.equals(mockClass.parameter3) : mockClass.parameter3 != null);
	}
}