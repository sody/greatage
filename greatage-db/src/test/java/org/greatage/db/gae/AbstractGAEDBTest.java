package org.greatage.db.gae;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractGAEDBTest extends Assert {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    protected DatastoreService dataStore;

    @BeforeMethod
    public void setUp() {
        helper.setUp();
        dataStore = DatastoreServiceFactory.getDatastoreService();
    }

    @AfterMethod
    public void tearDown() {
        dataStore = null;
        helper.tearDown();
    }

    protected void assertExist(final Query query) {
        assertCount(query, 1, "Requested entity doesn't exist");
    }

    protected void assertNotExist(final Query query) {
        assertCount(query, 0, "Requested entity exists");
    }

    protected void assertCount(final Query query, final int expected) {
        assertCount(query, expected, null);
    }

    protected void assertCount(final Query query, final int expected, final String message) {
        final int count = dataStore.prepare(query).countEntities(FetchOptions.Builder.withDefaults());
        assertEquals(count, expected, message);
    }
}
