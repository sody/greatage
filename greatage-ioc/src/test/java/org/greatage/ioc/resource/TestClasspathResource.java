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
	private static final Locale FINLAND = new Locale("fi");
	private static final Locale FINLAND_1 = new Locale("fi", "FI");

	@DataProvider
	public Object[][] childData() {
		return new Object[][] {
				{ "", "", "" },
				{ "", "META-INF", "META-INF" },
				{ "", "META-INF/", "META-INF" },
				{ "", "META-INF//", "META-INF" },
				{ "", "META-INF///", "META-INF" },
				{ "", "META-INF/services/", "META-INF/services" },
				{ "", "META-INF/services/some_file.txt", "META-INF/services/some_file.txt" },

				{ "/", "", "/" },
				{ "/", "META-INF", "/META-INF" },
				{ "/", "META-INF/services/", "/META-INF/services" },
				{ "/", "META-INF/services//", "/META-INF/services" },
				{ "/", "META-INF/services///", "/META-INF/services" },
				{ "/", "META-INF/services/some_file.txt", "/META-INF/services/some_file.txt" },
		};
	}

	@DataProvider
	public Object[][] childWrongData() {
		return new Object[][] {
				{ "", "/META-INF" },
				{ "", null },
				{ "/", "/" },
		};
	}

	@Test(dataProvider = "childData")
	public void testChild(final String parent, final String child, final String expected) {
		final Resource resource = ClasspathResource.get(parent).child(child);
		final String actual = resource.getPath();
		assertEquals(actual, expected);
	}

	@Test(dataProvider = "childWrongData", expectedExceptions = AssertionError.class)
	public void testChildWrong(final String parent, final String child) {
		ClasspathResource.get(parent).child(child);
	}

	@DataProvider
	public Object[][] createData() {
		return new Object[][] {
				//resource, path, location, name, type, locale
				{ "", "", null, "", null, null },
				{ "/", "/", null, "/", null, null },

				{ "META-INF", "META-INF", null, "META-INF", null, null },
				{ "META-INF/", "META-INF", null, "META-INF", null, null },
				{ "/META-INF", "/META-INF", "/", "META-INF", null, null },
				{ "/META-INF/", "/META-INF", "/", "META-INF", null, null },

				{ "META-INF/properties", "META-INF/properties", "META-INF", "properties", null, null },
				{ "META-INF/properties/", "META-INF/properties", "META-INF", "properties", null, null },
				{ "/META-INF/properties", "/META-INF/properties", "/META-INF", "properties", null, null },
				{ "/META-INF/properties/", "/META-INF/properties", "/META-INF", "properties", null, null },

				{
						"META-INF/properties/file.properties", "META-INF/properties/file.properties",
						"META-INF/properties", "file", "properties", null
				},
				{
						"/META-INF/properties/file.properties", "/META-INF/properties/file.properties",
						"/META-INF/properties", "file", "properties", null
				},

				{ "some-file", "some-file", null, "some-file", null, null },
				{ "some-file.ext", "some-file.ext", null, "some-file", "ext", null },
				{ "some-file.new.extension", "some-file.new.extension", null, "some-file.new", "extension", null },
				{ "some-file.", "some-file", null, "some-file", null, null },

				{ "some-file_fi.ext", "some-file_fi.ext", null, "some-file", "ext", FINLAND },
				{ "some-file_fi_fi.ext", "some-file_fi_fi.ext", null, "some-file_fi", "ext", FINLAND },
				{ "some-file_fi_FI.ext", "some-file_fi_FI.ext", null, "some-file", "ext", FINLAND_1 },

				{ "some-file_.ext", "some-file_.ext", null, "some-file_", "ext", null },
				{ "some-file_f.ext", "some-file_f.ext", null, "some-file_f", "ext", null },
				{ "some-file_fis.ext", "some-file_fis.ext", null, "some-file_fis", "ext", null },
				{ "some-file_fi_.ext", "some-file_fi_.ext", null, "some-file_fi_", "ext", null },
				{ "some-file_fi_F.ext", "some-file_fi_F.ext", null, "some-file_fi_F", "ext", null },
				{ "some-file_fi_FIS.ext", "some-file_fi_FIS.ext", null, "some-file_fi_FIS", "ext", null },
				{ "some-file_fi_FI_var.ext", "some-file_fi_FI_var.ext", null, "some-file_fi_FI_var", "ext", null },
		};
	}

	@Test(dataProvider = "createData")
	public void testCreate(final String path,
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

	@Test
	public void testChildren() {
		// some resources were not resolved
		Resource resource = ClasspathResource.get("META-INF/services");
		Set<Resource> children = resource.children("com.example.*");
		assertEquals(children.size(), 5);

		// the best situation, all resources were resolved
		resource = ClasspathResource.get("META-INF/");
		children = resource.children("services/com.example.*");
		assertEquals(children.size(), 5);

		resource = ClasspathResource.root();
		children = resource.children("META-INF/services/com.example.*");
		//todo: shit happens, maybe to resolve resources by different class loaders?
		assertEquals(children.size(), 2);
	}
}
