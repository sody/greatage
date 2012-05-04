package org.greatage.db.gae

import com.google.appengine.api.datastore.DatastoreService
import com.google.appengine.api.datastore.DatastoreServiceFactory
import com.google.appengine.api.datastore.Query
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import org.greatage.db.ChangeLog
import org.greatage.db.Database
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
abstract class GAEDBSpecification extends Specification {

    @Shared
    def helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig())

    @Shared
    def DatastoreService store

    @Shared
    def Database database

    def setup() {
        helper.setUp()
        store = DatastoreServiceFactory.getDatastoreService()
        database = new GAEDatabase(store)
    }

    def cleanup() {
        database = null
        store = null
        helper.tearDown()
    }

    def update(Closure closure) {
        database.update(new Database.Script() {
            @Override
            void execute(ChangeLog log) {
                closure(log)
            }
        })
    }

    protected int count(Query query) {
        store.prepare(query).countEntities()
    }
}
