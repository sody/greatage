package org.greatage.domain

import org.example.model.Company
import spock.lang.Shared
import spock.lang.Specification

import static org.example.model.Entities.company$

/**
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
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
abstract class SortOptionSpecification extends Specification {
	private static final Query.Property ID_PROPERTY = new Query.Property() {
		String getPath() {
			return null
		}

		String getProperty() {
			return "id"
		}
	}

	@Shared
	protected Repository repository

	def "sort option should retrieve entities sorted by defined property"() {
		when:
		def actual = findIds(entityClass, property, ascending, ignoreCase)
		then:
		actual == expected

		where:
		entityClass   | property               | ascending | ignoreCase | expected
		Company.class | company$.id$           | true      | true       | [1, 2, 3, 4, 5, 6]
		Company.class | company$.name$         | true      | true       | [4, 5, 6, 1, 2, 3]
		Company.class | company$.registeredAt$ | true      | true       | [1, 3, 4, 5, 6, 2]
	}

	def "sort option should retrieve entities sorted by defined property with specified sort direction"() {
		when:
		def actual = findIds(entityClass, property, ascending, ignoreCase)
		then:
		actual == expected

		where:
		entityClass   | property               | ascending | ignoreCase | expected
		Company.class | company$.id$           | true      | true       | [1, 2, 3, 4, 5, 6]
		Company.class | company$.id$           | false     | true       | [6, 5, 4, 3, 2, 1]
		Company.class | company$.name$         | true      | true       | [4, 5, 6, 1, 2, 3]
		Company.class | company$.name$         | false     | true       | [3, 2, 1, 5, 6, 4]
		Company.class | company$.registeredAt$ | true      | true       | [1, 3, 4, 5, 6, 2]
		Company.class | company$.registeredAt$ | false     | true       | [2, 6, 5, 1, 3, 4]
	}

	protected Date date(final String input) {
		return Date.parse("yyyy-MM-dd", input)
	}

	protected <E extends Entity<Long>> List<Long> findIds(final Class<E> entityClass,
														  final Query.Property property,
														  final boolean ascending,
														  final boolean ignoreCase) {

		return toIds(repository.query(entityClass)
				.sort(property, ascending, ignoreCase)
				.sort(ID_PROPERTY, true, false)
				.list())
	}

	protected List<Long> toIds(final List<? extends Entity<Long>> entities) {
		def result = new ArrayList<Long>()
		for (Entity<Long> entity : entities) {
			result.add(entity.id)
		}
		return result
	}
}
