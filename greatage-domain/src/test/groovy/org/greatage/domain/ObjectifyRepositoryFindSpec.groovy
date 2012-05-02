package org.greatage.domain

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import com.googlecode.objectify.ObjectifyFactory
import com.googlecode.objectify.ObjectifyOpts
import org.example.model.Company
import org.example.objectify.CompanyImpl
import org.greatage.domain.objectify.ObjectifyExecutor
import org.greatage.domain.objectify.ObjectifyRepository
import spock.lang.Shared

import static org.example.model.Entities.company$

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class ObjectifyRepositoryFindSpec extends PropertyCriteriaSpecification {

	@Shared
	private LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	def setupSpec() {
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

		repository = new ObjectifyRepository(new ObjectifyExecutor(objectifyFactory, new ObjectifyOpts()), [
				(Company.class): CompanyImpl.class
		])
	}

	def cleanupSpec() {
		repository = null;
		helper.tearDown();
	}

	def "property criteria should throw exception if it is used with key column and null value"() {
		//todo: all this should work
		when:
		findIds(entityClass, criteria)
		then:
		thrown(NullPointerException.class)

		where:
		entityClass   | criteria
		Company.class | company$.id$.isNull()
		Company.class | company$.id$.eq(null)
		Company.class | company$.id$.notNull()
		Company.class | company$.id$.ne(null)
		Company.class | company$.id$.gt(null)
		Company.class | company$.id$.ge(null)
		Company.class | company$.id$.lt(null)
		Company.class | company$.id$.le(null)
//		Company.class | company$.id$.like(null)
		Company.class | company$.id$.in([null])
	}
}
