package org.greatage.db.gae

import org.greatage.db.internal.InternalUtils

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class GAESmokeTest extends GAEDBSpecification {

    def "smoke test"() {
        when:
        database.update(InternalUtils.createScript("db/test_change_log.groovy"))
        then:
        noExceptionThrown()
    }
}
