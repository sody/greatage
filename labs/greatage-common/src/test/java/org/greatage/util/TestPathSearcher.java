package org.greatage.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestPathSearcher extends Assert {
	@DataProvider
	public Object[][] findData() {
		final Set<String> SET_CONFIGS = CollectionUtils.newSet("config/**/*.xml", "config/**/*.properties");
		final Set<String> SET_XML = CollectionUtils.newSet("config/**/*.xml", "path/**/*.xml");
		final Set<String> SET_SPI = CollectionUtils.newSet("META-INF/services/*");

		final String classPathRoot0 = ClassLoader.getSystemResource("").getFile();
		final String classPathRoot1 = classPathRoot0.substring(0, classPathRoot0.length() - 1);

		final String classPathRoot2 = ClassLoader.getSystemResource("").toExternalForm();
		final String classPathRoot3 = classPathRoot2.substring(0, classPathRoot2.length() - 1);

		// path inside jar without ending slash
		final String jarPath0 = "jar:file:" + classPathRoot0 + "test0.jar!/META-INF/services/";
		final String jarPath1 = jarPath0.substring(0, jarPath0.length() - 1);

		final String jarPath2 = "jar:file:" + classPathRoot0 + "test0.jar!/";
		final String jarPath3 = jarPath2.substring(0, jarPath2.length() - 1);

		final String zipPath0 = "zip:file:" + classPathRoot0 + "test1.zip!/META-INF/services/";
		final String zipPath1 = zipPath0.substring(0, zipPath0.length() - 1);

		final String zipPath2 = "zip:file:" + classPathRoot0 + "test1.zip!/";
		final String zipPath3 = zipPath2.substring(0, zipPath2.length() - 1);

		return new Object[][] {
				{ classPathRoot0, SET_XML, null, new String[] { "config/subconfig/10.xml", "path/3.xml" } },
				{ classPathRoot0, SET_CONFIGS, null, new String[] { "config/9.properties", "config/subconfig/10.xml" } },
				{ classPathRoot0, SET_XML, SET_CONFIGS, new String[] { "path/3.xml"} },
				{ classPathRoot0, SET_CONFIGS, SET_XML, new String[] { "config/9.properties" } },

				{ classPathRoot1, SET_XML, null, new String[] { "config/subconfig/10.xml", "path/3.xml" } },
				{ classPathRoot1, SET_CONFIGS, null, new String[] { "config/9.properties", "config/subconfig/10.xml" } },
				{ classPathRoot1, SET_XML, SET_CONFIGS, new String[] { "path/3.xml" } },
				{ classPathRoot1, SET_CONFIGS, SET_XML, new String[] { "config/9.properties" } },

				{ classPathRoot2, SET_XML, null, new String[] { "config/subconfig/10.xml", "path/3.xml" } },
				{ classPathRoot2, SET_CONFIGS, null, new String[] { "config/9.properties", "config/subconfig/10.xml" } },
				{ classPathRoot2, SET_XML, SET_CONFIGS, new String[] { "path/3.xml" } },
				{ classPathRoot2, SET_CONFIGS, SET_XML, new String[] { "config/9.properties" } },

				{ classPathRoot3, SET_XML, null, new String[] { "config/subconfig/10.xml", "path/3.xml" } },
				{ classPathRoot3, SET_CONFIGS, null, new String[] { "config/9.properties", "config/subconfig/10.xml" } },
				{ classPathRoot3, SET_XML, SET_CONFIGS, new String[] { "path/3.xml" } },
				{ classPathRoot3, SET_CONFIGS, SET_XML, new String[] { "config/9.properties" } },

				{ jarPath0, null, null, new String[] { "com.example.Service", "com.example.AnotherService" } },
				{ jarPath1, null, null, new String[] { "com.example.Service", "com.example.AnotherService" } },
				{
						jarPath2, SET_SPI, null, new String[] {
						"META-INF/services/com.example.Service",
						"META-INF/services/com.example.AnotherService"
				}
				},
				{ jarPath3, null, null, new String[0] },

				{ zipPath0, null, null, new String[] { "com.example.Service" } },
				{ zipPath1, null, null, new String[] { "com.example.Service" } },
				{ zipPath2, null, null, new String[] { "META-INF/services/com.example.Service" } },
				{ zipPath3, null, null, new String[0] },

				{ classPathRoot0 + "config/subconfig/10.xml", null, null, new String[] { "" } },
				{ jarPath0 + "com.example.Service", null, null, new String[] { "" } },
		};
	}

	@Test(dataProvider = "findData", enabled = false)
	public void testFind(final String path, final Set<String> included, final Set<String> excluded,
						 final String[] expected) {
		final PathSearcher pathSearcher = PathSearcher.create(path).include(included).exclude(excluded);
		final Set<String> actual = pathSearcher.search();
		assertEquals(actual.size(), expected.length);
		for (String expect : expected) {
			assertTrue(actual.contains(expect));
		}
	}
}
