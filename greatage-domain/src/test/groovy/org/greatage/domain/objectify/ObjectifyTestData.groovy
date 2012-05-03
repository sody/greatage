package org.greatage.domain.objectify

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import com.googlecode.objectify.ObjectifyFactory
import com.googlecode.objectify.ObjectifyOpts
import org.example.model.Company
import org.example.objectify.CompanyImpl
import org.greatage.domain.Repository

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class ObjectifyTestData {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	public Repository setup() {
		helper.setUp()

		def objectifyFactory = new ObjectifyFactory()
		objectifyFactory.register(CompanyImpl.class)
		objectifyFactory.begin().put([
				new CompanyImpl(id: 1, name: "company1"),
				new CompanyImpl(id: 2, name: "company2", registeredAt: date("2010-10-10")),
				new CompanyImpl(id: 3, name: "company3"),
				new CompanyImpl(id: 4, name: null),
				new CompanyImpl(id: 5, name: "company", registeredAt: date("2001-01-01")),
				new CompanyImpl(id: 6, name: "company", registeredAt: date("2001-02-02"))
		])

		return new ObjectifyRepository(new ObjectifyExecutor(objectifyFactory, new ObjectifyOpts()), [
				(Company.class): CompanyImpl.class
		])
	}

	public Repository cleanup() {
		helper.tearDown()
		return null
	}

	private Date date(final String input) {
		return Date.parse("yyyy-MM-dd", input)
	}
}
