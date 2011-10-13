package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeLog;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestGAEContexts extends AbstractGAEDBTest {
	private static final ChangeLog CHANGE_LOG = new ChangeLog() {
		@Override
		protected void init() {
			begin("1")
					.insert("company").set("name", "company1").end()
					.end();
			begin("2").context("test1")
					.insert("company").set("name", "company2").end()
					.end();
			begin("3").context("test2")
					.insert("company").set("name", "company3").end()
					.end();
			begin("4").context("test1", "test2")
					.insert("company").set("name", "company4").end()
					.end();
		}
	};

	@Test
	public void gae_empty_run_context() {
		assertNotExist(new Query("company"));
		database.update(CHANGE_LOG);
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company2"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company3"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company4"));
	}

	@Test
	public void gae_test1_run_context() {
		assertNotExist(new Query("company"));
		database.update(CHANGE_LOG, "test1");
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company2"));
		assertNotExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company3"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company4"));
	}

	@Test
	public void gae_test2_run_context() {
		assertNotExist(new Query("company"));
		database.update(CHANGE_LOG, "test2");
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertNotExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company2"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company3"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company4"));
	}

	@Test
	public void gae_test3_run_context() {
		assertNotExist(new Query("company"));
		database.update(CHANGE_LOG, "test3");
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertNotExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company2"));
		assertNotExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company3"));
		assertNotExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company4"));
	}

	@Test
	public void gae_test1_test2_run_context() {
		assertNotExist(new Query("company"));
		database.update(CHANGE_LOG, "test1", "test2");
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertNotExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company2"));
		assertNotExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company3"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company4"));
	}
}
