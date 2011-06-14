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

package org.greatage.ioc.proxy;

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
		return new Object[][] {
				{
						new MockObjectBuilder<MockIOCInterface>(
								MockIOCInterface.class, MockIOCInterfaceImpl1.class,
								null, "test1"
						),
						"test", "test1"
				},
				{
						new MockObjectBuilder<MockIOCInterface>(
								MockIOCInterface.class, MockIOCInterfaceImpl2.class,
								null,
								"test2"
						),
						"test", "test2"
				},
				{
						new MockObjectBuilder<MockIOCInterface>(
								MockIOCInterface.class, MockIOCInterfaceImpl2.class,
								null
						),
						"test", null
				},
				{
						new MockObjectBuilder<MockIOCInterface>(
								MockIOCInterface.class, MockIOCInterfaceImpl3.class,
								new MockInterceptor("invoke:")
						),
						"invoke:test", "invoke:" + MockIOCInterfaceImpl3.MESSAGE
				},
		};
	}

	@DataProvider
	public Object[][] proxyFactoryExtraData() {
		return new Object[][] {
				{
						new MockObjectBuilder<MockIOCInterfaceImpl2>(
								MockIOCInterfaceImpl2.class, MockIOCInterfaceImpl2.class,
								null
						),
						"test", null
				},
				{
						new MockObjectBuilder<MockIOCInterfaceImpl2>(
								MockIOCInterfaceImpl2.class, MockIOCInterfaceImpl2.class,
								null,
								"test5"
						),
						"test", "test5"
				},
				{
						new MockObjectBuilder<MockIOCInterfaceImpl3>(
								MockIOCInterfaceImpl3.class, MockIOCInterfaceImpl3.class,
								new MockInterceptor("invoke:")
						),
						"invoke:test", "invoke:" + MockIOCInterfaceImpl3.MESSAGE
				},
		};
	}

	@DataProvider
	public Object[][] proxyFactoryWrongData() {
		return new Object[][] {
				{
						new MockObjectBuilder<MockIOCInterfaceImpl1>(
								MockIOCInterfaceImpl1.class, MockIOCInterfaceImpl1.class,
								null,
								"test1"
						)
				},
				{
						new MockObjectBuilder<MockIOCInterfaceImpl4>(
								MockIOCInterfaceImpl4.class, MockIOCInterfaceImpl4.class,
								null,
								new MockIOCInterfaceImpl3()
						)
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
	public void testCGLibProxyFactory(final ObjectBuilder<? extends MockIOCInterface> builder,
									  final String expected1,
									  final String expected2) {
		final MockIOCInterface proxy = cglibProxyFactory.createProxy(builder);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryExtraData")
	public void testCGLibProxyFactoryExtra(final ObjectBuilder<? extends MockIOCInterface> builder,
										   final String expected1,
										   final String expected2) {
		final MockIOCInterface proxy = cglibProxyFactory.createProxy(builder);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryWrongData", expectedExceptions = IllegalArgumentException.class)
	public void testCGLibProxyFactoryWrong(final ObjectBuilder<? extends MockIOCInterface> builder) {
		final MockIOCInterface proxy = cglibProxyFactory.createProxy(builder);
		proxy.say("test");
		proxy.say();
	}

	@Test(dataProvider = "proxyFactoryData")
	public void testJavassistProxyFactory(final ObjectBuilder<? extends MockIOCInterface> builder,
										  final String expected1,
										  final String expected2) {
		final MockIOCInterface proxy = javassistProxyFactory.createProxy(builder);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryExtraData")
	public void testJavassistProxyFactoryExtra(final ObjectBuilder<? extends MockIOCInterface> builder,
											   final String expected1,
											   final String expected2) {
		final MockIOCInterface proxy = javassistProxyFactory.createProxy(builder);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryWrongData", expectedExceptions = IllegalArgumentException.class)
	public void testJavassistProxyFactoryWrong(final ObjectBuilder<? extends MockIOCInterface> builder) {
		final MockIOCInterface proxy = javassistProxyFactory.createProxy(builder);
		proxy.say("test");
		proxy.say();
	}

	@Test(dataProvider = "proxyFactoryData")
	public void testJdkProxyFactory(final ObjectBuilder<? extends MockIOCInterface> builder,
									final String expected1,
									final String expected2) {
		final MockIOCInterface proxy = jdkProxyFactory.createProxy(builder);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "proxyFactoryExtraData", expectedExceptions = IllegalArgumentException.class)
	public void testJdkProxyFactoryExtra(final ObjectBuilder<? extends MockIOCInterface> builder,
										 final String expected1,
										 final String expected2) {
		final MockIOCInterface proxy = jdkProxyFactory.createProxy(builder);
		proxy.say("test");
		proxy.say();
	}

	@Test(dataProvider = "proxyFactoryWrongData", expectedExceptions = IllegalArgumentException.class)
	public void testJdkProxyFactoryWrong(final ObjectBuilder<? extends MockIOCInterface> builder) {
		final MockIOCInterface proxy = jdkProxyFactory.createProxy(builder);
		proxy.say("test");
		proxy.say();
	}
}
