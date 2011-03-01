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

package org.greatage.util;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestLocker extends Assert {

	@Test
	public void testLocker() {
		final Locker locker = new Locker();
		locker.check();
		locker.lock();
	}

	@Test
	public void testLocker1() {
		final Locker locker = new Locker();
		locker.check();
		locker.check();
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void testLocker2() {
		final Locker locker = new Locker();
		locker.lock();
		locker.lock();
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void testLocker3() {
		final Locker locker = new Locker();
		locker.lock();
		locker.check();
	}

	@Test
	public void testLockerToString() {
		final Locker locker = new Locker();
		locker.check();
		assertEquals(locker.toString(), "Locker(locked=false)");
		locker.lock();
		assertEquals(locker.toString(), "Locker(locked=true)");
	}

	@Test
	public void testMultiLocker() {
		final MultiLocker<Class> locker = new MultiLocker<Class>();
		locker.check(getClass());
		locker.lock(getClass());

		locker.check(MultiLocker.class);
		locker.lock(MultiLocker.class);
	}

	@Test
	public void testMultiLocker1() {
		final MultiLocker<Class> locker = new MultiLocker<Class>();
		locker.check(getClass());
		locker.check(getClass());

		locker.check(MultiLocker.class);
		locker.check(MultiLocker.class);
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void testMultiLocker2() {
		final MultiLocker<Class> locker = new MultiLocker<Class>();
		locker.check(getClass());
		locker.lock(getClass());
		locker.lock(MultiLocker.class);
		locker.lock(getClass());
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void testMultiLocker3() {
		final MultiLocker<Class> locker = new MultiLocker<Class>();
		locker.lock(getClass());
		locker.lock(MultiLocker.class);
		locker.check(getClass());
	}

	@Test
	public void testMultiLockerToString() {
		final MultiLocker<Class> locker = new MultiLocker<Class>();
		locker.check(getClass());
		assertEquals(locker.toString(), "MultiLocker(lockers={})");
		locker.lock(getClass());
		assertEquals(locker.toString(), "MultiLocker(lockers={class org.greatage.util.TestLocker=Locker(locked=true)})");
	}
}
