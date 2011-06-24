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

package org.greatage.inject.proxy;

import org.greatage.inject.Interceptor;
import org.greatage.inject.Invocation;
import org.greatage.inject.internal.proxy.CGLibProxyFactory;
import org.greatage.inject.internal.proxy.JavassistProxyFactory;
import org.greatage.inject.internal.proxy.JdkProxyFactory;
import org.greatage.inject.services.ObjectBuilder;
import org.greatage.inject.services.ProxyFactory;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.ReflectionUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestProxyFactory extends Assert {
	private ProxyFactory jdkProxyFactory;
	private ProxyFactory cglibProxyFactory;
	private ProxyFactory javassistProxyFactory;

	@DataProvider
	public Object[][] proxyFactoryData() {
		return new Object[][]{
				{
						MockIOCInterface.class,
						new MockObjectBuilder<MockIOCInterface>(MockIOCInterfaceImpl1.class, "test1"),
						null,
						"test", "test1"
				},
				{
						MockIOCInterface.class,
						new MockObjectBuilder<MockIOCInterface>(MockIOCInterfaceImpl2.class, "test2"),
						null,
						"test", "test2"
				},
				{
						MockIOCInterface.class,
						new MockObjectBuilder<MockIOCInterface>(MockIOCInterfaceImpl2.class),
						null,
						"test", null
				},
				{
						MockIOCInterface.class,
						new MockObjectBuilder<MockIOCInterface>(MockIOCInterfaceImpl3.class),
						new MockInterceptor("invoke:"),
						"invoke:test", "invoke:" + MockIOCInterfaceImpl3.MESSAGE
				},
		};
	}

	@DataProvider
	public Object[][] proxyFactoryExtraData() {
		return new Object[][]{
				{
						MockIOCInterfaceImpl2.class,
						new MockObjectBuilder<MockIOCInterfaceImpl2>(MockIOCInterfaceImpl2.class),
						null,
						"test", null
				},
				{
						MockIOCInterfaceImpl2.class,
						new MockObjectBuilder<MockIOCInterfaceImpl2>(MockIOCInterfaceImpl2.class, "test5"),
						null,
						"test", "test5"
				},
				{
						MockIOCInterfaceImpl3.class,
						new MockObjectBuilder<MockIOCInterfaceImpl3>(MockIOCInterfaceImpl3.class),
						new MockInterceptor("invoke:"),
						"invoke:test", "invoke:" + MockIOCInterfaceImpl3.MESSAGE
				},
		};
	}

	@DataProvider
	public Object[][] proxyFactoryWrongData() {
		return new Object[][]{
				{
						MockIOCInterfaceImpl1.class,
						new MockObjectBuilder<MockIOCInterfaceImpl1>(MockIOCInterfaceImpl1.class, "test1"),
						null,
				},
				{
						MockIOCInterfaceImpl4.class,
						new MockObjectBuilder<MockIOCInterfaceImpl4>(MockIOCInterfaceImpl4.class, new MockIOCInterfaceImpl3()),
						null,
				},
		};
	}

	@BeforeClass
	public void setupProxyFactory() {
		jdkProxyFactory = new JdkProxyFactory();
		cglibProxyFactory = new CGLibProxyFactory();
		javassistProxyFactory = new JavassistProxyFactory();
	}

	@Test(dataProvider = "proxyFactoryData")
	public <T extends MockIOCInterface>
	void testCGLibProxyFactory(final Class<T> objectClass,
							   final ObjectBuilder<T> builder,
							   final Interceptor interceptor,
							   final String expected1,
							   final String expected2) {
		final MockIOCInterface proxy = cglibProxyFactory.createProxy(objectClass, builder, interceptor);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryExtraData")
	public <T extends MockIOCInterface>
	void testCGLibProxyFactoryExtra(final Class<T> objectClass,
									final ObjectBuilder<T> builder,
									final Interceptor interceptor,
									final String expected1,
									final String expected2) {
		final MockIOCInterface proxy = cglibProxyFactory.createProxy(objectClass, builder, interceptor);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryWrongData", expectedExceptions = IllegalArgumentException.class)
	public <T extends MockIOCInterface>
	void testCGLibProxyFactoryWrong(final Class<T> objectClass,
									final ObjectBuilder<T> builder,
									final Interceptor interceptor) {
		final MockIOCInterface proxy = cglibProxyFactory.createProxy(objectClass, builder, interceptor);
		proxy.say("test");
		proxy.say();
	}

	@Test(dataProvider = "proxyFactoryData")
	public <T extends MockIOCInterface>
	void testJavassistProxyFactory(final Class<T> objectClass,
								   final ObjectBuilder<T> builder,
								   final Interceptor interceptor,
								   final String expected1,
								   final String expected2) {
		final MockIOCInterface proxy = javassistProxyFactory.createProxy(objectClass, builder, interceptor);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryExtraData")
	public <T extends MockIOCInterface>
	void testJavassistProxyFactoryExtra(final Class<T> objectClass,
										final ObjectBuilder<T> builder,
										final Interceptor interceptor,
										final String expected1,
										final String expected2) {
		final MockIOCInterface proxy = javassistProxyFactory.createProxy(objectClass, builder, interceptor);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryWrongData", expectedExceptions = IllegalArgumentException.class)
	public <T extends MockIOCInterface>
	void testJavassistProxyFactoryWrong(final Class<T> objectClass,
										final ObjectBuilder<T> builder,
										final Interceptor interceptor) {
		final MockIOCInterface proxy = javassistProxyFactory.createProxy(objectClass, builder, interceptor);
		proxy.say("test");
		proxy.say();
	}

	@Test(dataProvider = "proxyFactoryData")
	public <T extends MockIOCInterface>
	void testJdkProxyFactory(final Class<T> objectClass,
							 final ObjectBuilder<T> builder,
							 final Interceptor interceptor,
							 final String expected1,
							 final String expected2) {
		final MockIOCInterface proxy = jdkProxyFactory.createProxy(objectClass, builder, interceptor);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryExtraData", expectedExceptions = IllegalArgumentException.class)
	public <T extends MockIOCInterface>
	void testJdkProxyFactoryExtra(final Class<T> objectClass,
								  final ObjectBuilder<T> builder,
								  final Interceptor interceptor,
								  final String expected1,
								  final String expected2) {
		final MockIOCInterface proxy = jdkProxyFactory.createProxy(objectClass, builder, interceptor);
		proxy.say("test");
		proxy.say();
	}

	@Test(dataProvider = "proxyFactoryWrongData", expectedExceptions = IllegalArgumentException.class)
	public <T extends MockIOCInterface>
	void testJdkProxyFactoryWrong(final Class<T> objectClass,
								  final ObjectBuilder<T> builder,
								  final Interceptor interceptor) {
		final MockIOCInterface proxy = jdkProxyFactory.createProxy(objectClass, builder, interceptor);
		proxy.say("test");
		proxy.say();
	}

	public static class MockObjectBuilder<T> implements ObjectBuilder<T> {
		private final Class<? extends T> implementationClass;
		private final Object[] constructionParameters;

		public MockObjectBuilder(final Class<? extends T> implementationClass,
								 final Object... constructionParameters) {
			this.implementationClass = implementationClass;
			this.constructionParameters = constructionParameters;
		}

		public T build() {
			return ReflectionUtils.newInstance(implementationClass, constructionParameters);
		}

		@Override
		public String toString() {
			final DescriptionBuilder builder = new DescriptionBuilder(getClass());
			builder.append("implementation", implementationClass);
			return builder.toString();
		}
	}

	public static class MockInterceptor implements Interceptor {
		private final String message;

		public MockInterceptor(final String message) {
			this.message = message;
		}

		public Object invoke(final Invocation invocation, final Object... parameters) throws Throwable {
			return message + invocation.proceed(parameters);
		}
	}

	public interface MockIOCInterface {

		String say(final String message);

		String say();
	}

	public static class MockIOCInterfaceImpl1 implements MockIOCInterface {
		private final String message;

		public MockIOCInterfaceImpl1(final String message) {
			this.message = message;
		}

		public String say(final String message) {
			return message;
		}

		public String say() {
			return message;
		}
	}

	public static class MockIOCInterfaceImpl2 implements MockIOCInterface {
		private final String message;

		public MockIOCInterfaceImpl2() {
			this(null);
		}

		public MockIOCInterfaceImpl2(final String message) {
			this.message = message;
		}

		public String say(final String message) {
			return message;
		}

		public String say() {
			return message;
		}
	}

	public static class MockIOCInterfaceImpl3 implements MockIOCInterface {
		public static final String MESSAGE = "mock-message";

		public String say(final String message) {
			return message;
		}

		public String say() {
			return MESSAGE;
		}
	}

	public static class MockIOCInterfaceImpl4 implements MockIOCInterface {
		private final MockIOCInterface delegate;

		public MockIOCInterfaceImpl4(final MockIOCInterface delegate) {
			this.delegate = delegate;
		}

		public String say(final String message) {
			return delegate.say(message);
		}

		public String say() {
			return delegate.say();
		}
	}
}
