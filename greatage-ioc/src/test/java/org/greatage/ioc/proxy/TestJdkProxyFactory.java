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

import org.greatage.ioc.mock.*;
import org.greatage.util.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestJdkProxyFactory extends Assert {
	private ProxyFactory proxyFactory;

	@DataProvider
	public Object[][] createProxyData() {
		return new Object[][]{
				{
						new MockObjectBuilder<MockIOCInterface>(MockIOCInterface.class, MockIOCInterfaceImpl1.class, "test1"),
						CollectionUtils.newList(),
						"test", "test1"
				},
				{
						new MockObjectBuilder<MockIOCInterface>(MockIOCInterface.class, MockIOCInterfaceImpl2.class, "test2"),
						null,
						"test", "test2"
				},
				{
						new MockObjectBuilder<MockIOCInterface>(MockIOCInterface.class, MockIOCInterfaceImpl2.class),
						null,
						"test", null
				},
				{
						new MockObjectBuilder<MockIOCInterface>(MockIOCInterface.class, MockIOCInterfaceImpl3.class),
						CollectionUtils.newList(
								new MethodAdvice() {
									public Object advice(final Invocation invocation, final Object... parameters) throws Throwable {
										return "advice:" + invocation.proceed(parameters);
									}
								}
						),
						"advice:test", "advice:" + MockIOCInterfaceImpl3.MESSAGE
				},
				{
						new MockObjectBuilder<MockIOCInterface>(MockIOCInterface.class, MockIOCInterfaceImpl4.class, new MockIOCInterfaceImpl3()),
						CollectionUtils.newList(
								new MethodAdvice() {
									public Object advice(final Invocation invocation, final Object... parameters) throws Throwable {
										return "advice1:" + invocation.proceed(parameters);
									}
								},
								new MethodAdvice() {
									public Object advice(final Invocation invocation, final Object... parameters) throws Throwable {
										return "advice2:" + invocation.proceed(parameters);
									}
								}
						),
						"advice2:advice1:test", "advice2:advice1:" + MockIOCInterfaceImpl3.MESSAGE
				},
		};
	}

	@DataProvider
	public Object[][] createProxyWrongData() {
		return new Object[][]{
				{
						new MockObjectBuilder<MockIOCInterfaceImpl1>(MockIOCInterfaceImpl1.class, MockIOCInterfaceImpl1.class, "test1"),
						null
				},
				{
						new MockObjectBuilder<MockIOCInterfaceImpl2>(MockIOCInterfaceImpl2.class, MockIOCInterfaceImpl2.class),
						null
				},
				{
						new MockObjectBuilder<MockIOCInterfaceImpl2>(MockIOCInterfaceImpl2.class, MockIOCInterfaceImpl2.class, "test3"),
						null
				},
				{
						new MockObjectBuilder<MockIOCInterfaceImpl3>(MockIOCInterfaceImpl3.class, MockIOCInterfaceImpl3.class),
						null
				},
				{
						new MockObjectBuilder<MockIOCInterfaceImpl4>(MockIOCInterfaceImpl4.class, MockIOCInterfaceImpl4.class, new MockIOCInterfaceImpl3()),
						null
				},
		};
	}

	@BeforeClass
	public void setupProxyFactory() {
		proxyFactory = new JdkProxyFactory();
	}

	@Test(dataProvider = "createProxyData")
	public void testCreateProxy(final ObjectBuilder<? extends MockIOCInterface> builder,
								final List<MethodAdvice> advices,
								final String expected1,
								final String expected2) {
		final MockIOCInterface proxy = proxyFactory.createProxy(builder, advices);
		Assert.assertEquals(proxy.say("test"), expected1);
		Assert.assertEquals(proxy.say(), expected2);
	}

	@Test(dataProvider = "createProxyWrongData", expectedExceptions = IllegalArgumentException.class)
	public void testCreateProxyWrong(final ObjectBuilder<? extends MockIOCInterface> builder,
									 final List<MethodAdvice> advices) {
		final MockIOCInterface proxy = proxyFactory.createProxy(builder, advices);
		proxy.say("test");
		proxy.say();
	}

}
