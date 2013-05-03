package org.greatage.domain.hibernate

import org.greatage.domain.EntityCriteriaSpecification
import spock.lang.Shared

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class HibernateEntityCriteriaSpec extends EntityCriteriaSpecification {

    @Shared
    private HibernateTestData data = new HibernateTestData();

    def setupSpec() {
        repository = data.setup()
    }

    def cleanupSpec() {
        repository = data.cleanup()
    }
}
