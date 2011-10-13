package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeLog;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestGAEDatabaseOptions extends AbstractGAEDBTest {

	@Test
	public void gae_clear_checksum() {
		assertNotExist(new Query("company"));
		database.update(new ChangeLog() {
			@Override
			protected void init() {
				location("test");
				begin("1").insert("company").set("name", "company1");
			}
		});
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));

		database.options().clearCheckSums().update(new ChangeLog() {
			@Override
			protected void init() {
				location("test");
				begin("1").insert("company").set("name", "company2");
			}
		});
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertNotExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company2"));
	}

	@Test(enabled = false)
	public void gae_drop_first() {
		assertNotExist(new Query("company"));
		database.update(new ChangeLog() {
			@Override
			protected void init() {
				location("test");
				begin("1").insert("company").set("name", "company1");
				begin("2").insert("company").set("name", "company2");
				begin("3").insert("company").set("name", "company3");
			}
		});
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company2"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company3"));

		database.options().dropFirst().update(new ChangeLog() {
			@Override
			protected void init() {
				location("test");
				begin("1").insert("department").set("name", "dep1");
			}
		});
		assertNotExist(new Query("company"));
		assertExist(new Query("department").addFilter("name", Query.FilterOperator.EQUAL, "dep1"));
	}
}
