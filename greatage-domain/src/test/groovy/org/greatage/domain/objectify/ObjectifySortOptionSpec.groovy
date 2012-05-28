package org.greatage.domain.objectify

import org.greatage.domain.SortOptionSpecification
import spock.lang.Shared

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class ObjectifySortOptionSpec extends SortOptionSpecification {

	@Shared
	private ObjectifyTestData data = new ObjectifyTestData();

	def setupSpec() {
		repository = data.setup()
	}

	def cleanupSpec() {
		repository = data.cleanup()
	}
}
