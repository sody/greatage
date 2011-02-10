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
}
