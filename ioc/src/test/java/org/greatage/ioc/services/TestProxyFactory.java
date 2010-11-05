package org.greatage.ioc.services;

import org.greatage.ioc.internal.proxy.CGLibProxyFactory;
import org.greatage.ioc.internal.proxy.JavaAssistProxyFactory;
import org.greatage.ioc.internal.proxy.JdkProxyFactory;
import org.greatage.mock.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestProxyFactory {
	private static final ProxyFactory JDK_PROXY_FACTORY = new JdkProxyFactory();
	private static final ProxyFactory JAVA_ASSIST_PROXY_FACTORY = new JavaAssistProxyFactory();
	private static final ProxyFactory CGLIB_PROXY_FACTORY = new CGLibProxyFactory();

	@DataProvider
	public Object[][] createProxyData() {
		return new Object[][]{
				{JDK_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl1("test1");
					}
				}, "test1"},
				{JDK_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl2("test2");
					}
				}, "test2"},
				{JDK_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl2();
					}
				}, null},
				{JDK_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl3();
					}
				}, MockInterfaceImpl3.MESSAGE},
				{JDK_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl4(new MockInterfaceImpl3());
					}
				}, MockInterfaceImpl3.MESSAGE},
				{JAVA_ASSIST_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl1("test6");
					}
				}, "test6"},
				{JAVA_ASSIST_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl2("test7");
					}
				}, "test7"},
				{JAVA_ASSIST_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl2();
					}
				}, null},
				{JAVA_ASSIST_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl2>() {
					public Class<MockInterfaceImpl2> getObjectClass() {
						return MockInterfaceImpl2.class;
					}

					public MockInterfaceImpl2 build() {
						return new MockInterfaceImpl2("test9");
					}
				}, "test9"},
				{JAVA_ASSIST_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl2>() {
					public Class<MockInterfaceImpl2> getObjectClass() {
						return MockInterfaceImpl2.class;
					}

					public MockInterfaceImpl2 build() {
						return new MockInterfaceImpl2();
					}
				}, null},
				{JAVA_ASSIST_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl3();
					}
				}, MockInterfaceImpl3.MESSAGE},
				{JAVA_ASSIST_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl3>() {
					public Class<MockInterfaceImpl3> getObjectClass() {
						return MockInterfaceImpl3.class;
					}

					public MockInterfaceImpl3 build() {
						return new MockInterfaceImpl3();
					}
				}, MockInterfaceImpl3.MESSAGE},
				{JAVA_ASSIST_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl4(new MockInterfaceImpl3());
					}
				}, MockInterfaceImpl3.MESSAGE},
				{CGLIB_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl1("test14");
					}
				}, "test14"},
				{CGLIB_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl2("test15");
					}
				}, "test15"},
				{CGLIB_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl2();
					}
				}, null},
				{CGLIB_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl2>() {
					public Class<MockInterfaceImpl2> getObjectClass() {
						return MockInterfaceImpl2.class;
					}

					public MockInterfaceImpl2 build() {
						return new MockInterfaceImpl2("test17");
					}
				}, "test17"},
				{CGLIB_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl2>() {
					public Class<MockInterfaceImpl2> getObjectClass() {
						return MockInterfaceImpl2.class;
					}

					public MockInterfaceImpl2 build() {
						return new MockInterfaceImpl2();
					}
				}, null},
				{CGLIB_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl3();
					}
				}, MockInterfaceImpl3.MESSAGE},
				{CGLIB_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl3>() {
					public Class<MockInterfaceImpl3> getObjectClass() {
						return MockInterfaceImpl3.class;
					}

					public MockInterfaceImpl3 build() {
						return new MockInterfaceImpl3();
					}
				}, MockInterfaceImpl3.MESSAGE},
				{CGLIB_PROXY_FACTORY, new ObjectBuilder<MockInterface>() {
					public Class<MockInterface> getObjectClass() {
						return MockInterface.class;
					}

					public MockInterface build() {
						return new MockInterfaceImpl4(new MockInterfaceImpl3());
					}
				}, MockInterfaceImpl3.MESSAGE},
		};
	}

	@DataProvider
	public Object[][] createProxyWrongData() {
		return new Object[][]{
				{JDK_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl1>() {
					public Class<MockInterfaceImpl1> getObjectClass() {
						return MockInterfaceImpl1.class;
					}

					public MockInterfaceImpl1 build() {
						return new MockInterfaceImpl1("test1");
					}
				}},
				{JDK_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl2>() {
					public Class<MockInterfaceImpl2> getObjectClass() {
						return MockInterfaceImpl2.class;
					}

					public MockInterfaceImpl2 build() {
						return new MockInterfaceImpl2();
					}
				}},
				{JDK_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl2>() {
					public Class<MockInterfaceImpl2> getObjectClass() {
						return MockInterfaceImpl2.class;
					}

					public MockInterfaceImpl2 build() {
						return new MockInterfaceImpl2("test3");
					}
				}},
				{JDK_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl3>() {
					public Class<MockInterfaceImpl3> getObjectClass() {
						return MockInterfaceImpl3.class;
					}

					public MockInterfaceImpl3 build() {
						return new MockInterfaceImpl3();
					}
				}},
				{JDK_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl4>() {
					public Class<MockInterfaceImpl4> getObjectClass() {
						return MockInterfaceImpl4.class;
					}

					public MockInterfaceImpl4 build() {
						return new MockInterfaceImpl4(new MockInterfaceImpl3());
					}
				}},
				{JAVA_ASSIST_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl1>() {
					public Class<MockInterfaceImpl1> getObjectClass() {
						return MockInterfaceImpl1.class;
					}

					public MockInterfaceImpl1 build() {
						return new MockInterfaceImpl1("test6");
					}
				}},
				{JAVA_ASSIST_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl4>() {
					public Class<MockInterfaceImpl4> getObjectClass() {
						return MockInterfaceImpl4.class;
					}

					public MockInterfaceImpl4 build() {
						return new MockInterfaceImpl4(new MockInterfaceImpl3());
					}
				}},
				{CGLIB_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl1>() {
					public Class<MockInterfaceImpl1> getObjectClass() {
						return MockInterfaceImpl1.class;
					}

					public MockInterfaceImpl1 build() {
						return new MockInterfaceImpl1("test8");
					}
				}},
				{CGLIB_PROXY_FACTORY, new ObjectBuilder<MockInterfaceImpl4>() {
					public Class<MockInterfaceImpl4> getObjectClass() {
						return MockInterfaceImpl4.class;
					}

					public MockInterfaceImpl4 build() {
						return new MockInterfaceImpl4(new MockInterfaceImpl3());
					}
				}},
		};
	}

	@Test(dataProvider = "createProxyData")
	public void testCreateProxy(final ProxyFactory proxyFactory,
								final ObjectBuilder<? extends MockInterface> builder,
								final String expected) {
		final MockInterface proxy = proxyFactory.createProxy(builder);
		Assert.assertEquals(proxy.say(), expected);
		Assert.assertEquals(proxy.say(), expected);
	}

	@Test(dataProvider = "createProxyWrongData", expectedExceptions = IllegalArgumentException.class)
	public void testCreateProxyWrong(final ProxyFactory proxyFactory,
									 final ObjectBuilder<? extends MockInterface> builder) {
		final MockInterface proxy = proxyFactory.createProxy(builder);
		proxy.say();
		proxy.say();
	}
}
