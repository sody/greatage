package org.greatage.domain.jdo

import org.greatage.domain.PropertyCriteriaSpecification
import spock.lang.Shared

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class JDOPropertyCriteriaSpec extends PropertyCriteriaSpecification {

	@Shared
	private JDOTestData data = new JDOTestData();

	def setupSpec() {
		repository = data.setup()
	}

	def cleanupSpec() {
		repository = data.cleanup()
	}
}
