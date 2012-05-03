package org.greatage.domain.objectify

import org.greatage.domain.JunctionCriteriaSpecification
import spock.lang.Shared

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class ObjectifyJunctionCriteriaSpec extends JunctionCriteriaSpecification {

	@Shared
	private ObjectifyTestData data = new ObjectifyTestData();

	def setupSpec() {
		repository = data.setup()
	}

	def cleanupSpec() {
		repository = data.cleanup()
	}
}
