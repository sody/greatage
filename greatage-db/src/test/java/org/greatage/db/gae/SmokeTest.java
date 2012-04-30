package org.greatage.db.gae;

import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SmokeTest extends AbstractGAEDBTest {

    @Test
    public void test() {
        new GAEDatabase(dataStore)
                .script("greatage-db/src/test/resources/db/test_change_log.groovy")
                .update();
        System.out.println();
    }
}
