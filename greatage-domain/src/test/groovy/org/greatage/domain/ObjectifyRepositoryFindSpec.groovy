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

	def "all criteria should find all entities"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria       | expected
		Company.class | company$.all() | [1, 2, 3, 4, 5, 6]
	}


	def "equal property criteria should find only entities with property value equal to specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                         | expected
		Company.class | company$.id$.equal(1)                            | [1]
		Company.class | company$.id$.eq(4)                               | [4]
		Company.class | company$.id$.eq(10)                              | []
		Company.class | company$.name$.equal("company")                  | [5, 6]
		Company.class | company$.name$.eq("company1")                    | [1]
		Company.class | company$.name$.eq("company8")                    | []
		Company.class | company$.name$.eq(null)                          | [4]
		Company.class | company$.registeredAt$.equal(date("2001-01-01")) | [5]
		Company.class | company$.registeredAt$.eq(date("2001-01-02"))    | []
		Company.class | company$.registeredAt$.eq(null)                  | [1, 3, 4]
	}

	def "not equal property criteria should find only entities with property value not equal to specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                      | expected
		Company.class | company$.id$.notEqual(1)                      | [2, 3, 4, 5, 6]
		Company.class | company$.id$.ne(10)                           | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.notEqual("company")            | [1, 2, 3, 4]
		Company.class | company$.name$.ne("company1")                 | [2, 3, 4, 5, 6]
		Company.class | company$.name$.ne("company8")                 | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.ne(null)                       | [1, 2, 3, 5, 6]
		Company.class | company$.registeredAt$.ne(date("2001-01-01")) | [1, 2, 3, 4, 6]
		Company.class | company$.registeredAt$.ne(date("2001-01-02")) | [1, 2, 3, 4, 5, 6]
		Company.class | company$.registeredAt$.notEqual(null)         | [2, 5, 6]
	}

	def "is null property criteria should find only entities with property value equal to null"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                        | expected
		Company.class | company$.name$.isNull()         | [4]
		Company.class | company$.name$.eq(null)         | [4]
		Company.class | company$.registeredAt$.isNull() | [1, 3, 4]
		Company.class | company$.registeredAt$.eq(null) | [1, 3, 4]
	}

	def "is not null property criteria should find only entities with property value not equal to null"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                         | expected
		Company.class | company$.name$.notNull()         | [1, 2, 3, 5, 6]
		Company.class | company$.name$.ne(null)          | [1, 2, 3, 5, 6]
		Company.class | company$.registeredAt$.notNull() | [2, 5, 6]
		Company.class | company$.registeredAt$.ne(null)  | [2, 5, 6]
	}

	def "greater than property criteria should find only entities with property value greater than specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                               | expected
		Company.class | company$.id$.greaterThan(4)                            | [5, 6]
		Company.class | company$.id$.gt(6)                                     | []
		Company.class | company$.id$.gt(10)                                    | []
		Company.class | company$.name$.greaterThan("company")                  | [1, 2, 3]
		Company.class | company$.name$.gt("a")                                 | [1, 2, 3, 5, 6]
		Company.class | company$.name$.gt("z")                                 | []
		Company.class | company$.name$.gt(null)                                | [1, 2, 3, 5, 6]
		Company.class | company$.registeredAt$.greaterThan(date("2001-01-01")) | [2, 6]
		Company.class | company$.registeredAt$.gt(date("2010-10-10"))          | []
		Company.class | company$.registeredAt$.gt(date("2012-10-10"))          | []
		Company.class | company$.registeredAt$.gt(null)                        | [2, 5, 6]
	}

	def "greater or equal property criteria should find only entities with property value greater or equal to specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                                  | expected
		Company.class | company$.id$.greaterOrEqual(4)                            | [4, 5, 6]
		Company.class | company$.id$.ge(6)                                        | [6]
		Company.class | company$.id$.ge(10)                                       | []
		Company.class | company$.name$.greaterOrEqual("company")                  | [1, 2, 3, 5, 6]
		Company.class | company$.name$.ge("a")                                    | [1, 2, 3, 5, 6]
		Company.class | company$.name$.ge("z")                                    | []
		Company.class | company$.name$.ge(null)                                   | [1, 2, 3, 4, 5, 6]
		Company.class | company$.registeredAt$.greaterOrEqual(date("2001-01-01")) | [2, 5, 6]
		Company.class | company$.registeredAt$.ge(date("2010-10-10"))             | [2]
		Company.class | company$.registeredAt$.ge(date("2012-10-10"))             | []
		Company.class | company$.registeredAt$.ge(null)                           | [1, 2, 3, 4, 5, 6]
	}

	def "less than property criteria should find only entities with property value less than specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                            | expected
		Company.class | company$.id$.lessThan(4)                            | [1, 2, 3]
		Company.class | company$.id$.lt(1)                                  | []
		Company.class | company$.id$.lt(10)                                 | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.lessThan("company")                  | [4]
		Company.class | company$.name$.lt("a")                              | [4]
		Company.class | company$.name$.lt("z")                              | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.lt(null)                             | []
		Company.class | company$.registeredAt$.lessThan(date("2001-01-01")) | [1, 3, 4]
		Company.class | company$.registeredAt$.lt(date("2010-10-10"))       | [1, 3, 4, 5, 6]
		Company.class | company$.registeredAt$.lt(date("2012-10-10"))       | [1, 2, 3, 4, 5, 6]
		Company.class | company$.registeredAt$.lt(null)                     | []
	}

	def "less or equal property criteria should find only entities with property value less or equal to specified"() {
		when:
		def actual = toIds(repository.find(entityClass, criteria, Pagination.ALL))
		then:
		actual == expected

		where:
		entityClass   | criteria                                               | expected
		Company.class | company$.id$.lessOrEqual(4)                            | [1, 2, 3, 4]
		Company.class | company$.id$.le(1)                                     | [1]
		Company.class | company$.id$.le(10)                                    | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.lessOrEqual("company")                  | [4, 5, 6]
		Company.class | company$.name$.le("a")                                 | [4]
		Company.class | company$.name$.le("z")                                 | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.le(null)                                | [4]
		Company.class | company$.registeredAt$.lessOrEqual(date("2001-01-01")) | [1, 3, 4, 5]
		Company.class | company$.registeredAt$.le(date("2010-10-10"))          | [1, 2, 3, 4, 5, 6]
		Company.class | company$.registeredAt$.le(date("2012-10-10"))          | [1, 2, 3, 4, 5, 6]
		Company.class | company$.registeredAt$.le(null)                        | [1, 3, 4]
	}


	def "property criteria should throw exception if it is used with key column and null value"() {
		when:
		repository.find(entityClass, criteria, Pagination.ALL)
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
		Company.class | company$.id$.like(null)
		Company.class | company$.id$.in([null])
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
