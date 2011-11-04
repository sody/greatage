package org.greatage.db.gae;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import org.greatage.db.ChangeLog;
import org.greatage.db.DatabaseException;
import org.greatage.db.Trick;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestGAEDatabase extends AbstractGAEDBTest {
	@Test
	public void gae_insert() {
		assertNotExist(new Query("company"));
		assertNotExist(new Query("department"));
		assertNotExist(new Query("employee"));

		final ChangeLog changeLog = new ChangeLog() {
			@Override
			protected void init() {
				final Trick trick = begin("1").trick();
				trick.insert("company").set("name", "company1");
				trick.insert("company").set("name", "company2");
				trick.insert("company").set("name", "company3");

				begin("2", "author").trick()
						.insert("department").set("name", "department1").set("company", 10l);

				begin("3", "author").comment("test employee").trick()
						.insert("employee").set("name", "employee1").set("department", 10l);
			}
		};

		database.update(changeLog);

		assertCount(new Query("company"), 3);
		assertExist(new Query("company")
				.addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertExist(new Query("company")
				.addFilter("name", Query.FilterOperator.EQUAL, "company2"));
		assertExist(new Query("company")
				.addFilter("name", Query.FilterOperator.EQUAL, "company3"));

		assertCount(new Query("department"), 1);
		assertExist(new Query("department")
				.addFilter("name", Query.FilterOperator.EQUAL, "department1")
				.addFilter("company", Query.FilterOperator.EQUAL, 10l));

		assertCount(new Query("employee"), 1);
		assertExist(new Query("employee")
				.addFilter("name", Query.FilterOperator.EQUAL, "employee1")
				.addFilter("department", Query.FilterOperator.EQUAL, 10l));
	}

	@Test
	public void gae_update() {
		assertNotExist(new Query("company"));
		assertNotExist(new Query("department"));
		assertNotExist(new Query("employee"));

		Entity entity = new Entity("company");
		entity.setProperty("name", "company1");
		dataStore.put(entity);
		entity = new Entity("company");
		entity.setProperty("name", "company2");
		dataStore.put(entity);
		entity = new Entity("company");
		entity.setProperty("name", "company3");
		dataStore.put(entity);

		entity = new Entity("department");
		entity.setProperty("name", "department1");
		dataStore.put(entity);
		entity = new Entity("department");
		entity.setProperty("name", "department2");
		dataStore.put(entity);

		entity = new Entity("employee");
		entity.setProperty("name", "employee1");
		dataStore.put(entity);

		final ChangeLog changeLog = new ChangeLog() {
			@Override
			protected void init() {
				begin("1").trick()
						.update("company").set("name", "company");

				Trick trick = begin("2", "author").comment("test employee").trick();
				trick.update("employee").set("name", "employee111")
						.where(trick.condition("department").equal(100l));

				trick = begin("3", "author").trick();
				trick.update("department").set("name", "department11")
						.where(trick.condition("name").equal("department1"));
			}
		};

		database.update(changeLog);

		assertCount(new Query("company"), 3);
		assertCount(new Query("company")
				.addFilter("name", Query.FilterOperator.EQUAL, "company"), 3);

		assertCount(new Query("department"), 2);
		assertExist(new Query("department")
				.addFilter("name", Query.FilterOperator.EQUAL, "department11"));
		assertNotExist(new Query("department")
				.addFilter("name", Query.FilterOperator.EQUAL, "department1"));

		assertCount(new Query("employee"), 1);
		assertNotExist(new Query("employee")
				.addFilter("name", Query.FilterOperator.EQUAL, "employee111"));
	}

	@Test
	public void gae_delete() {
		assertNotExist(new Query("company"));
		assertNotExist(new Query("department"));
		assertNotExist(new Query("employee"));

		Entity entity = new Entity("company");
		entity.setProperty("name", "company1");
		dataStore.put(entity);
		entity = new Entity("company");
		entity.setProperty("name", "company2");
		dataStore.put(entity);
		entity = new Entity("company");
		entity.setProperty("name", "company3");
		dataStore.put(entity);

		entity = new Entity("department");
		entity.setProperty("name", "department1");
		dataStore.put(entity);
		entity = new Entity("department");
		entity.setProperty("name", "department2");
		dataStore.put(entity);

		entity = new Entity("employee");
		entity.setProperty("name", "employee1");
		dataStore.put(entity);

		final ChangeLog changeLog = new ChangeLog() {
			@Override
			protected void init() {
				begin("1").trick()
						.delete("company");

				Trick trick = begin("2", "author").comment("test employee").trick();
				trick.delete("employee")
						.where(trick.condition("department").equal(100l));

				trick = begin("3", "author").trick();
				trick.delete("department")
						.where(trick.condition("name").equal("department2"));

			}
		};

		database.update(changeLog);

		assertNotExist(new Query("company"));

		assertCount(new Query("department"), 1);
		assertExist(new Query("department")
				.addFilter("name", Query.FilterOperator.EQUAL, "department1"));
		assertNotExist(new Query("department")
				.addFilter("name", Query.FilterOperator.EQUAL, "department2"));

		assertCount(new Query("employee"), 1);
	}

	@Test
	public void gae_twice_update() {
		assertNotExist(new Query("company"));

		final ChangeLog changeLog = new ChangeLog() {
			@Override
			protected void init() {
				Trick trick = begin("1").trick();
				trick.insert("company").set("name", "company1");
				trick.insert("company").set("name", "company2");
				trick.insert("company").set("name", "company3");

				trick = begin("2").trick();
				trick.update("company").set("name", "company22")
						.where(trick.condition("company").equal("company2"));

				trick = begin("3").trick();
				trick.update("company").set("name", "company2")
						.set("country", trick.select("country").where(trick.condition("id").equal(1)))
						.where(trick.condition("company").equal("company22"));

				trick = begin("4").trick();
				trick.delete("company")
						.where(trick.condition("name").equal("company1"));
			}
		};

		database.update(changeLog);

		assertCount(new Query("company"), 2);
		assertNotExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company2"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company3"));

		database.update(changeLog);

		assertCount(new Query("company"), 2);
		assertNotExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company2"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company3"));
	}

	@Test
	public void gae_not_ended_statement() {
		assertNotExist(new Query("company"));

		ChangeLog changeLog = new ChangeLog() {
			@Override
			protected void init() {
				begin("1").trick().insert("company").set("name", "company1");
				begin("2").trick().insert("company").set("name", "company2");
			}
		};

		database.update(changeLog);
		assertCount(new Query("company"), 2);

		changeLog = new ChangeLog() {
			@Override
			protected void init() {
				begin("3").trick().insert("company").set("name", "company3");
				begin("4").trick().insert("company").set("name", "company4");
			}
		};

		database.update(changeLog);
		assertCount(new Query("company"), 4);
	}

	@Test(expectedExceptions = DatabaseException.class, expectedExceptionsMessageRegExp = "^ChangeSet .* has already been executed")
	public void gae_duplicate_change_set() {
		final ChangeLog changeLog = new ChangeLog() {
			@Override
			protected void init() {
				begin("1").trick().insert("company").set("name", "company1");
				begin("2").trick().insert("company").set("name", "company2");
				begin("3").trick().insert("company").set("name", "company3");
				begin("4").trick().insert("company").set("name", "company4");
				begin("1").trick().insert("company").set("name", "company5");
			}
		};
		database.update(changeLog);
	}

	@Test(expectedExceptions = DatabaseException.class, expectedExceptionsMessageRegExp = "^Already locked.*$")
	public void gae_already_locked() {
		database.update(new ChangeLog() {
			@Override
			protected void init() {
				location("test");
				begin("1").trick().insert("company").set("name", "company1");
				database.update(this);
			}
		});
	}

	@Test
	public void gae_unlock_when_failed() {
		try {
			database.update(new ChangeLog() {
				@Override
				protected void init() {
					location("test");
					begin("1").trick().insert("company").set("name", "company1");
					throw new IllegalStateException("Fail");
				}
			});
		} catch (Exception e) {
			// pass
		}
		assertNotExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));

		database.update(new ChangeLog() {
			@Override
			protected void init() {
				location("test");
				begin("1").trick().insert("company").set("name", "company1");
				begin("2").trick().insert("company").set("name", "company2");
			}
		});
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company1"));
		assertExist(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company2"));
	}

	@Test
	public void gae_lot_of_data() {
		assertNotExist(new Query("company"));

		final int count = 3000;
		database.update(new ChangeLog() {
			@Override
			protected void init() {
				location("test");
				for (int i = 0; i < count; i++) {
					begin("change_" + i).trick().insert("company").set("name", "company" + i);
				}
			}
		});

		assertCount(new Query("company"), count);

		database.update(new ChangeLog() {
			@Override
			protected void init() {
				location("test");
				begin("update").trick().update("company").set("name", "company");
			}
		});

		assertCount(new Query("company").addFilter("name", Query.FilterOperator.EQUAL, "company"), count);

		database.update(new ChangeLog() {
			@Override
			protected void init() {
				location("test");
				begin("delete").trick().delete("company");
			}
		});

		assertNotExist(new Query("company"));
	}
}
