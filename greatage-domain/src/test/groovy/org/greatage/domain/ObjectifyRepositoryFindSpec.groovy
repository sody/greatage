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
import spock.lang.Specification

import static org.example.model.Entities.company$

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class ObjectifyRepositoryFindSpec extends Specification {

	@Shared
	private LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	@Shared
	private EntityRepository repository;

	def setupSpec() {
		helper.setUp()

		def objectifyFactory = new ObjectifyFactory()
		objectifyFactory.register(CompanyImpl.class)
		objectifyFactory.begin().put([
				new CompanyImpl(id: 1l, name: "company1"),
				new CompanyImpl(id: 2l, name: "company2", registeredAt: date("2010-10-10")),
				new CompanyImpl(id: 3l, name: "company3"),
				new CompanyImpl(id: 4l, name: null),
				new CompanyImpl(id: 5l, name: "company", registeredAt: date("2001-01-01")),
				new CompanyImpl(id: 6l, name: "company", registeredAt: date("2001-02-02"))
		])

		repository = new ObjectifyRepository(new ObjectifyExecutor(objectifyFactory, new ObjectifyOpts()), [
				(Company.class): CompanyImpl.class
		])
	}

	def cleanupSpec() {
		repository = null;
		helper.tearDown();
	}

	def "all criteria should find all entities"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL));
		then:
		actual == expected

		where:
		entityClass   | criteria       | expected
		Company.class | company$.all() | [1l, 2l, 3l, 4l, 5l, 6l]
	}


	def "equals property criteria should find only entities with property value equal to specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL));
		then:
		actual == expected

		where:
		entityClass   | criteria                                      | expected
		Company.class | company$.id$.eq(1l)                           | [1l]
		Company.class | company$.id$.eq(4l)                           | [4l]
		Company.class | company$.id$.eq(10l)                          | []
		Company.class | company$.name$.eq("company")                  | [5l, 6l]
		Company.class | company$.name$.eq("company1")                 | [1l]
		Company.class | company$.name$.eq("company8")                 | []
		Company.class | company$.name$.eq(null)                       | [4l]
		Company.class | company$.registeredAt$.eq(date("2001-01-01")) | [5l]
		Company.class | company$.registeredAt$.eq(date("2001-01-02")) | []
		Company.class | company$.registeredAt$.eq(null)               | [1l, 3l, 4l]
	}

	def "not equals property criteria should find only entities with property value not equal to specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL));
		then:
		actual == expected

		where:
		entityClass   | criteria                                      | expected
		Company.class | company$.id$.ne(1l)                           | [2l, 3l, 4l, 5l, 6l]
		Company.class | company$.id$.ne(10l)                          | [1l, 2l, 3l, 4l, 5l, 6l]
		Company.class | company$.name$.ne("company")                  | [1l, 2l, 3l, 4l]
		Company.class | company$.name$.ne("company1")                 | [2l, 3l, 4l, 5l, 6l]
		Company.class | company$.name$.ne("company8")                 | [1l, 2l, 3l, 4l, 5l, 6l]
		Company.class | company$.name$.ne(null)                       | [1l, 2l, 3l, 5l, 6l]
		Company.class | company$.registeredAt$.ne(date("2001-01-01")) | [1l, 2l, 3l, 4l, 6l]
		Company.class | company$.registeredAt$.ne(date("2001-01-02")) | [1l, 2l, 3l, 4l, 5l, 6l]
		Company.class | company$.registeredAt$.ne(null)               | [2l, 5l, 6l]
	}

	private Date date(final String input) {
		return Date.parse("yyyy-MM-dd", input)
	}

	private List<Long> toIds(final List<? extends Entity<Long>> entities) {
		def result = new ArrayList<Long>()
		for (Entity<Long> entity : entities) {
			result.add(entity.id)
		}
		Collections.sort(result)
		return result
	}
}
