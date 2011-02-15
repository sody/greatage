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
