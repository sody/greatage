package org.greatage.db.gae

import com.google.appengine.api.datastore.Query
import org.greatage.db.ChangeLog

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
class GAEInsertSpec extends GAEDBSpecification {

    def "<INSERT-CHANGE> should insert row into defined table"() {
        when:
        update({ ChangeLog log ->
            log.begin("1.0.0")
            log.insert("test")
        })
        then:
        count(new Query("test")) == 1
    }

    def "<INSERT-CHANGE> should insert row with specified properties into defined table"() {
        when:
        update({ ChangeLog log ->
            log.begin("1.0.0")
            log.insert("test")
                    .set("property", "Hello!")
                    .set("property_1", 111)
                    .set("property_2", true)
        })
        then:
        count(new Query("test")) == 1
        count(new Query("test")
                .addFilter("property", Query.FilterOperator.EQUAL, "Hello!")
                .addFilter("property_1", Query.FilterOperator.EQUAL, 111)
                .addFilter("property_2", Query.FilterOperator.EQUAL, true)
        ) == 1
    }

    def "<INSERT-CHANGE> should insert row into defined table for each specified value"() {
        when:
        update({ ChangeLog log ->
            log.begin("1.0.0")
            log.insert("test")
                    .into("property", "property_1", "property_2")
                    .values("Hello", 111, true)
                    .values(" ", 222, false)
                    .values("World!", 333, true)
        })
        then:
        count(new Query("test")) == 3
        count(new Query("test")
                .addFilter("property", Query.FilterOperator.EQUAL, "Hello")
                .addFilter("property_1", Query.FilterOperator.EQUAL, 111)
                .addFilter("property_2", Query.FilterOperator.EQUAL, true)
        ) == 1
        count(new Query("test")
                .addFilter("property", Query.FilterOperator.EQUAL, " ")
                .addFilter("property_1", Query.FilterOperator.EQUAL, 222)
                .addFilter("property_2", Query.FilterOperator.EQUAL, false)
        ) == 1
        count(new Query("test")
                .addFilter("property", Query.FilterOperator.EQUAL, "World!")
                .addFilter("property_1", Query.FilterOperator.EQUAL, 333)
                .addFilter("property_2", Query.FilterOperator.EQUAL, true)
        ) == 1
    }

    def "<INSERT-CHANGE> should insert row into defined table for each specified value with all specified properties"() {
        when:
        update({ ChangeLog log ->
            log.begin("1.0.0")
            log.insert("test")
                    .set("property", "Hello!")
                    .into("property_1", "property_2")
                    .values(111, true)
                    .values(222, false)
                    .values(333, true)
        })
        then:
        count(new Query("test")) == 3
        count(new Query("test")
                .addFilter("property", Query.FilterOperator.EQUAL, "Hello!")
                .addFilter("property_1", Query.FilterOperator.EQUAL, 111)
                .addFilter("property_2", Query.FilterOperator.EQUAL, true)
        ) == 1
        count(new Query("test")
                .addFilter("property", Query.FilterOperator.EQUAL, "Hello!")
                .addFilter("property_1", Query.FilterOperator.EQUAL, 222)
                .addFilter("property_2", Query.FilterOperator.EQUAL, false)
        ) == 1
        count(new Query("test")
                .addFilter("property", Query.FilterOperator.EQUAL, "Hello!")
                .addFilter("property_1", Query.FilterOperator.EQUAL, 333)
                .addFilter("property_2", Query.FilterOperator.EQUAL, true)
        ) == 1
    }
}
