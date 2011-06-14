package org.greatage.ioc.resource;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Locale;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestClasspathResource extends Assert {
	private static final Locale ROOT = new Locale("");
	private static final Locale RUSSIAN = new Locale("ru");
	private static final Locale RUSSIAN_1 = new Locale("ru", "RU");

	@DataProvider
	public Object[][] childData() {
		return new Object[][] {
				{"", "", ""},
				{"", null, ""},
				{"", "META-INF", "META-INF"},
				{"", "/META-INF", "META-INF"},
				{"", "META-INF/", "META-INF"},
				{"", "META-INF//", "META-INF"},
				{"", "META-INF///", "META-INF"},
				{"", "META-INF/services/", "META-INF/services"},
				{"", "/META-INF/services/", "META-INF/services"},
				{"", "META-INF/services/some_file.txt", "META-INF/services/some_file.txt"},

				{"/", "", ""},
				{"/", null, ""},
				{"/", "META-INF", "META-INF"},
				{"/", "/META-INF", "META-INF"},
				{"/", "META-INF/services/", "META-INF/services"},
				{"/", "META-INF////services//", "META-INF/services"},
				{"/", "META-INF/services///", "META-INF/services"},
				{"/", "/META-INF/services///", "META-INF/services"},
				{"/", "META-INF/services/some_file.txt", "META-INF/services/some_file.txt"},

				{"META-INF", "", "META-INF"},
				{"META-INF", null, "META-INF"},
				{"META-INF", "services/", "META-INF/services"},
				{"META-INF", "/services/", "META-INF/services"},
				{"META-INF", "services//", "META-INF/services"},
				{"META-INF", "services///", "META-INF/services"},
				{"META-INF", "services//some_file.txt", "META-INF/services/some_file.txt"},

				{"/META-INF", "", "META-INF"},
				{"/META-INF", "services/", "META-INF/services"},
				{"/META-INF", "services//", "META-INF/services"},
				{"/META-INF", "services///", "META-INF/services"},
				{"/META-INF", "services//some_file.txt", "META-INF/services/some_file.txt"},

				{"META-INF/services/", "", "META-INF/services"},
				{"META-INF/services/", null, "META-INF/services"},
				{"META-INF/services//", "some_file.txt", "META-INF/services/some_file.txt"},
				{"META-INF/services//", "/some_file.txt", "META-INF/services/some_file.txt"},
		};
	}

	@Test(dataProvider = "childData")
	public void testChild(final String parent, final String child, final String expected) {
		final Resource resource = ClasspathResource.get(parent).child(child);
		final String actual = resource.getPath();
		assertEquals(actual, expected);
	}

	@DataProvider
	public Object[][] getResourceData() {
		return new Object[][] {
				//resource, path, location, name, type, locale
				{"", "", null, "", null, ROOT},
				{"/", "", null, "", null, ROOT},

				{"META-INF", "META-INF", null, "META-INF", null, ROOT},
				{"META-INF/", "META-INF", null, "META-INF", null, ROOT},
				{"/META-INF", "META-INF", null, "META-INF", null, ROOT},
				{"/META-INF/", "META-INF", null, "META-INF", null, ROOT},

				{"META-INF/properties", "META-INF/properties", "META-INF", "properties", null, ROOT},
				{"META-INF/properties/", "META-INF/properties", "META-INF", "properties", null, ROOT},
				{"/META-INF/properties", "META-INF/properties", "META-INF", "properties", null, ROOT},
				{"/META-INF/properties/", "META-INF/properties", "META-INF", "properties", null, ROOT},

				{
						"META-INF/properties/file.properties", "META-INF/properties/file.properties",
						"META-INF/properties", "file", "properties", ROOT
				},
				{
						"/META-INF/properties/file.properties", "META-INF/properties/file.properties",
						"META-INF/properties", "file", "properties", ROOT
				},

				{"some-file", "some-file", null, "some-file", null, ROOT},
				{"some-file.ext", "some-file.ext", null, "some-file", "ext", ROOT},
				{"some-file.new.extension", "some-file.new.extension", null, "some-file.new", "extension", ROOT},
				{"some-file.", "some-file", null, "some-file", null, ROOT},

				{"some-file_ru.ext", "some-file_ru.ext", null, "some-file", "ext", RUSSIAN},
				{"some-file_ru_ru.ext", "some-file_ru_ru.ext", null, "some-file_ru", "ext", RUSSIAN},
				{"some-file_ru_RU.ext", "some-file_ru_RU.ext", null, "some-file", "ext", RUSSIAN_1},

				{"some-file_.ext", "some-file_.ext", null, "some-file_", "ext", ROOT},
				{"some-file_r.ext", "some-file_r.ext", null, "some-file_r", "ext", ROOT},
				{"some-file_rus.ext", "some-file_rus.ext", null, "some-file_rus", "ext", ROOT},
				{"some-file_ru_.ext", "some-file_ru_.ext", null, "some-file_ru_", "ext", ROOT},
				{"some-file_ru_R.ext", "some-file_ru_R.ext", null, "some-file_ru_R", "ext", ROOT},
				{"some-file_ru_RUS.ext", "some-file_ru_RUS.ext", null, "some-file_ru_RUS", "ext", ROOT},
				{"some-file_ru_RU_var.ext", "some-file_ru_RU_var.ext", null, "some-file_ru_RU_var", "ext", ROOT},
		};
	}

	@Test(dataProvider = "getResourceData")
	public void testGetResource(final String path,
								final String expectedPath,
								final String expectedLocation,
								final String expectedName,
								final String expectedType,
								final Locale expectedLocale) throws IOException {
		final Resource resource = ClasspathResource.get(path);
		assertNotNull(resource);
		assertEquals(resource.getPath(), expectedPath);
		assertEquals(resource.getLocation(), expectedLocation);
		assertEquals(resource.getName(), expectedName);
		assertEquals(resource.getType(), expectedType);
		assertEquals(resource.getLocale(), expectedLocale);
	}

	@DataProvider
	public Object[][] childrenData() {
		return new Object[][] {
				{"META-INF/services", "com.example.*", 5},
				{"META-INF/services/", "com.example.*", 5},
				{"META-INF", "services/com.example.*", 5},
				{"META-INF/", "**/com.example.*", 5},
				//todo: classloader doesn't resolve resources inside jars for path ""
				{"", "**/com.example.*", 2},
				{"", "META-INF/services/com.example.*", 2},
		};
	}

	@Test(dataProvider = "childrenData")
	public void testChildren(final String path, final String include, final int expectedSize) {
		final Resource resource = ClasspathResource.get(path);
		Set<Resource> children = resource.children(include);
		assertEquals(children.size(), expectedSize);
	}
}
