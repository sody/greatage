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
import org.greatage.inject.Key;
import org.greatage.inject.Marker;
import org.greatage.inject.internal.proxy.CGLibProxyFactory;
import org.greatage.inject.internal.proxy.InvocationImpl;
import org.greatage.inject.internal.proxy.JavassistProxyFactory;
import org.greatage.inject.internal.proxy.JdkProxyFactory;
import org.greatage.inject.services.ProxyFactory;
import org.greatage.inject.services.ServiceBuilder;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.ReflectionUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

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
						new MockServiceBuilder<MockIOCInterface>(
								MockIOCInterface.class,
								MockIOCInterfaceImpl1.class,
								null,
								"test1"
						), "test", "test1"
				},
				{
						new MockServiceBuilder<MockIOCInterface>(
								MockIOCInterface.class,
								MockIOCInterfaceImpl2.class,
								null,
								"test2"
						), "test", "test2"
				},
				{
						new MockServiceBuilder<MockIOCInterface>(
								MockIOCInterface.class,
								MockIOCInterfaceImpl2.class,
								null
						), "test", null
				},
				{
						new MockServiceBuilder<MockIOCInterface>(
								MockIOCInterface.class,
								MockIOCInterfaceImpl3.class,
								new MockInterceptor("invoke:")
						), "invoke:test", "invoke:" + MockIOCInterfaceImpl3.MESSAGE
				},
		};
	}

	@DataProvider
	public Object[][] proxyFactoryExtraData() {
		return new Object[][]{
				{
						new MockServiceBuilder<MockIOCInterfaceImpl2>(
								MockIOCInterfaceImpl2.class,
								MockIOCInterfaceImpl2.class,
								null
						), "test", null
				},
				{
						new MockServiceBuilder<MockIOCInterfaceImpl2>(
								MockIOCInterfaceImpl2.class,
								MockIOCInterfaceImpl2.class,
								null,
								"test5"
						), "test", "test5"
				},
				{
						new MockServiceBuilder<MockIOCInterfaceImpl3>(
								MockIOCInterfaceImpl3.class,
								MockIOCInterfaceImpl3.class,
								new MockInterceptor("invoke:")
						), "invoke:test", "invoke:" + MockIOCInterfaceImpl3.MESSAGE
				},
		};
	}

	@DataProvider
	public Object[][] proxyFactoryWrongData() {
		return new Object[][]{
				{
						new MockServiceBuilder<MockIOCInterfaceImpl1>(
								MockIOCInterfaceImpl1.class,
								MockIOCInterfaceImpl1.class,
								null,
								"test1"
						),
				},
				{
						new MockServiceBuilder<MockIOCInterfaceImpl4>(
								MockIOCInterfaceImpl4.class,
								MockIOCInterfaceImpl4.class,
								null,
								new MockIOCInterfaceImpl3()
						),
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
	void testCGLibProxyFactory(final ServiceBuilder<T> builder, final String expected1, final String expected2) {
		final MockIOCInterface proxy = cglibProxyFactory.createProxy(builder);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryExtraData")
	public <T extends MockIOCInterface>
	void testCGLibProxyFactoryExtra(final ServiceBuilder<T> builder, final String expected1, final String expected2) {
		final MockIOCInterface proxy = cglibProxyFactory.createProxy(builder);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryWrongData", expectedExceptions = IllegalArgumentException.class)
	public <T extends MockIOCInterface>
	void testCGLibProxyFactoryWrong(final ServiceBuilder<T> builder) {
		final MockIOCInterface proxy = cglibProxyFactory.createProxy(builder);
		proxy.say("test");
		proxy.say();
	}

	@Test(dataProvider = "proxyFactoryData")
	public <T extends MockIOCInterface>
	void testJavassistProxyFactory(final ServiceBuilder<T> builder, final String expected1, final String expected2) {
		final MockIOCInterface proxy = javassistProxyFactory.createProxy(builder);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryExtraData")
	public <T extends MockIOCInterface>
	void testJavassistProxyFactoryExtra(final ServiceBuilder<T> builder, final String expected1,
										final String expected2) {
		final MockIOCInterface proxy = javassistProxyFactory.createProxy(builder);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryWrongData", expectedExceptions = IllegalArgumentException.class)
	public <T extends MockIOCInterface>
	void testJavassistProxyFactoryWrong(final ServiceBuilder<T> builder) {
		final MockIOCInterface proxy = javassistProxyFactory.createProxy(builder);
		proxy.say("test");
		proxy.say();
	}

	@Test(dataProvider = "proxyFactoryData")
	public <T extends MockIOCInterface>
	void testJdkProxyFactory(final ServiceBuilder<T> builder, final String expected1, final String expected2) {
		final MockIOCInterface proxy = jdkProxyFactory.createProxy(builder);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryExtraData", expectedExceptions = IllegalArgumentException.class)
	public <T extends MockIOCInterface>
	void testJdkProxyFactoryExtra(final ServiceBuilder<T> builder, final String expected1, final String expected2) {
		final MockIOCInterface proxy = jdkProxyFactory.createProxy(builder);
		proxy.say("test");
		proxy.say();
	}

	@Test(dataProvider = "proxyFactoryWrongData", expectedExceptions = IllegalArgumentException.class)
	public <T extends MockIOCInterface>
	void testJdkProxyFactoryWrong(final ServiceBuilder<T> builder) {
		final MockIOCInterface proxy = jdkProxyFactory.createProxy(builder);
		proxy.say("test");
		proxy.say();
	}

	public static class MockServiceBuilder<T> implements ServiceBuilder<T> {
		private final Class<? extends T> implementationClass;
		private final Interceptor interceptor;
		private final Object[] constructionParameters;
		private final Marker<T> marker;

		public MockServiceBuilder(final Class<T> serviceClass,
								  final Class<? extends T> implementationClass,
								  final Interceptor interceptor,
								  final Object... constructionParameters) {
			this.implementationClass = implementationClass;
			this.interceptor = interceptor;
			this.constructionParameters = constructionParameters;

			marker = Key.get(serviceClass);
		}

		public Marker<T> getMarker() {
			return marker;
		}

		public boolean eager() {
			return false;
		}

		public boolean intercepts(final Method method) {
			return interceptor != null;
		}

		public Object invoke(final Method method, final Object... parameters) throws Throwable {
			final InvocationImpl invocation = new InvocationImpl(build(), method);
			return interceptor.invoke(invocation, parameters);
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
