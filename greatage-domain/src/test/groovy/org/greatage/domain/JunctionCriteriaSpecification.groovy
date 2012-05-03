package org.greatage.domain

import org.example.model.Company
import spock.lang.Shared
import spock.lang.Specification

import static org.example.model.Entities.company$

/**
 * @author Ivan Khalopik
 * @since 1.0
 *
 * +-------------------------------+
 * |            COMPANY            |
 * +----+----------+---------------+
 * | ID | NAME     | REGISTERED_AT |
 * +----+----------+---------------+
 * | 1  | company1 |          null |
 * | 2  | company2 |    2010-10-10 |
 * | 3  | company3 |          null |
 * | 4  |     null |          null |
 * | 5  |  company |    2001-01-01 |
 * | 6  |  company |    2010-02-02 |
 * +----+----------+---------------+
 */
abstract class JunctionCriteriaSpecification extends Specification {

	@Shared
	protected Repository repository

	def "all criteria should find all entities"() {
		when:
		def actual = findIds(entityClass, criteria)
		then:
		actual == expected

		where:
		entityClass   | criteria       | expected
		Company.class | company$.all() | [1, 2, 3, 4, 5, 6]
	}

	def "and criteria should find only entities than match each child criteria"() {
		when:
		def actual = findIds(entityClass, criteria)
		then:
		actual == expected

		where:
		entityClass   | criteria                                                                                                 | expected
		Company.class | company$.name$.eq("company1").and(company$.id$.eq(1l))                                                   | [1]
		Company.class | company$.name$.eq("company1").and(company$.id$.eq(2l))                                                   | []
		Company.class | company$.name$.eq("company").and(company$.id$.le(6l))                                                    | [5, 6]
		Company.class | company$.name$.eq("company").and(company$.id$.lt(5l))                                                    | []
		Company.class | company$.name$.notNull().and(company$.id$.eq(4l))                                                        | []
		Company.class | company$.name$.eq("company").and(company$.id$.gt(2l)).and(company$.registeredAt$.eq(date("2001-01-01"))) | [5]
		Company.class | company$.name$.eq("company").and(company$.id$.gt(2l)).and(company$.registeredAt$.eq(date("2001-02-02"))) | [6]
		Company.class | company$.name$.eq("company").and(company$.id$.gt(2l)).and(company$.registeredAt$.eq(date("2011-02-02"))) | []
	}

	def "or criteria should find only entities than match at least one child criteria"() {
		when:
		def actual = findIds(entityClass, criteria)
		then:
		actual == expected

		where:
		entityClass   | criteria                                                                                               | expected
		Company.class | company$.name$.eq("company1").or(company$.id$.eq(1l))                                                  | [1]
		Company.class | company$.name$.eq("company1").or(company$.id$.eq(2l))                                                  | [1, 2]
		Company.class | company$.name$.eq("company").or(company$.id$.le(6l))                                                   | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.eq("company").or(company$.id$.lt(5l))                                                   | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.notNull().or(company$.id$.eq(4l))                                                       | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$.eq("company").or(company$.id$.eq(1l)).or(company$.registeredAt$.eq(date("2010-10-10"))) | [1, 2, 5, 6]
	}

	protected Date date(final String input) {
		return Date.parse("yyyy-MM-dd", input)
	}

	protected <E extends Entity<Long>> List<Long> findIds(final Class<E> entityClass, final Repository.Criteria<Long, E> criteria) {
		return toIds(repository.query(entityClass).filter(criteria).list());
	}

	protected List<Long> toIds(final List<? extends Entity<Long>> entities) {
		def result = new ArrayList<Long>()
		for (Entity<Long> entity : entities) {
			result.add(entity.id)
		}
		Collections.sort(result)
		return result
	}
}