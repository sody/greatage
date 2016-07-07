package org.greatage.domain.hibernate

import org.example.hibernate.AccountImpl
import org.example.model.Account
import org.greatage.domain.RepositorySpecification
import spock.lang.Shared

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class HibernateRepositorySpec extends RepositorySpecification {

    @Shared
    private HibernateTestData data = new HibernateTestData();

    def setupSpec() {
        repository = data.setup()
    }

    def cleanupSpec() {
        repository = data.cleanup()
    }

    @Override
    protected Account createAccount(final long id) {
        return new AccountImpl(id);
    }
}
