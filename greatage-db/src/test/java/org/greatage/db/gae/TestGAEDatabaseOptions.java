package org.greatage.db.gae;

import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeLogSupport;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestGAEDatabaseOptions extends AbstractGAEDBTest {
	private static final ChangeLogSupport CHANGE_LOG = new ChangeLogSupport() {
		@Override
		protected void init() {
			begin("1");
			insert("company").set("name", "company1");

			begin("2").context("test1");
			insert("company").set("name", "company2");

			begin("3").context("test2");
			insert("company").set("name", "company3");

			begin("4").context("test1", "test2");
			insert("company").set("name", "company4");
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

	@Test
	public void gae_clear_checksum() {
		assertNotExist(new Query("company"));
		database.update(new ChangeLogSupport() {
			@Override
			protected void init() {
				location("test");

				begin("1");
				insert("company").set("name", "company1");
			}
		});
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));

		database.options().clearCheckSums().update(new ChangeLogSupport() {
			@Override
			protected void init() {
				location("test");

				begin("1");
				insert("company").set("name", "company2");
			}
		});
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertNotExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company2"));
	}

	@Test
	public void gae_drop_first() {
		assertNotExist(new Query("company"));
		database.update(new ChangeLogSupport() {
			@Override
			protected void init() {
				location("test");
				begin("1");
				insert("company").set("name", "company1");

				begin("2");
				insert("company").set("name", "company2");

				begin("3");
				insert("company").set("name", "company3");
			}
		});
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company2"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company3"));

		database.options().dropFirst().update(new ChangeLogSupport() {
			@Override
			protected void init() {
				location("test");

				begin("1");
				insert("department").set("name", "dep1");
			}
		});
		assertNotExist(new Query("company"));
		assertExist(new Query("department").addFilter("name", Query.FilterOperator.EQUAL, "dep1"));
	}

	@Test
	public void gae_drop_first_lot_of_data() {
		assertNotExist(new Query("company"));
		assertNotExist(new Query("department"));
		assertNotExist(new Query("employee"));

		final int count = 3000;
		database.update(new ChangeLogSupport() {
			@Override
			protected void init() {
				location("test");

				begin("1");
				for (int i = 0; i < count; i++) {
					insert("company").set("name", "company" + i);
					insert("department").set("name", "department" + i).set("company", i);
					insert("employee").set("name", "employee" + i + "1").set("department", i);
					insert("employee").set("name", "employee2" + i + "2").set("department", i);
					insert("employee").set("name", "employee3" + i + "3").set("department", i);
				}
			}
		});

		assertCount(new Query("company"), count);
		assertCount(new Query("department"), count);
		assertCount(new Query("employee"), count * 3);

		database.options().dropFirst().update(new ChangeLogSupport() {
			@Override
			protected void init() {
				location("test");

				begin("1");
				insert("test");
				//To change body of implemented methods use File | Settings | File Templates.
			}
		});
		assertNotExist(new Query("company"));
		assertNotExist(new Query("department"));
		assertNotExist(new Query("employee"));
		assertExist(new Query("test"));
	}
}
