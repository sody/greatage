package org.greatage.domain.jdo

import org.greatage.domain.JunctionCriteriaSpecification
import spock.lang.Shared

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class JDOJunctionCriteriaSpec {

    @Shared
    private JDOTestData data = new JDOTestData();

    def setupSpec() {
        repository = data.setup()
    }

    def cleanupSpec() {
        repository = data.cleanup()
    }
}
