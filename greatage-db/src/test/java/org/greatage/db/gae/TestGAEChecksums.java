package org.greatage.db.gae;

import org.greatage.db.ChangeLog;
import org.greatage.db.DatabaseException;
import org.greatage.db.Trick;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestGAEChecksums extends AbstractGAEDBTest {

	@DataProvider
	public Object[][] checksumFailingData() {
		return new Object[][]{
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").comment("some comment");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").context("test1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").context("test1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").context("test1", "test2");
							}
						}
				},

				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.insert("company").set("name", "company1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.insert("company").set("name", "company2");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.insert("company").set("name", "company1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.insert("company").set("description", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.insert("company").set("name", "company1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.insert("department").set("name", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.insert("company").set("name", "company1");
								trick.insert("company").set("name", "company2");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.insert("company").set("name", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.insert("company").set("name", "company1");
								trick.insert("company").set("name", "company2");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.insert("company").set("name", "company2");
								trick.insert("company").set("name", "company1");
							}
						}
				},

				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.update("company").set("name", "company1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.update("company").set("name", "company2");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.update("company").set("name", "company1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.update("company").set("description", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.update("company").set("name", "company1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.update("department").set("name", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.update("company").set("name", "company1")
										.where(trick.condition("name").equal("company"));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick()
										.update("company").set("name", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.update("company").set("name", "company1")
										.where(trick.condition("name").equal("company"));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.update("company").set("name", "company1")
										.where(trick.condition("name").notEqual("company"));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.update("company").set("name", "company1")
										.where(trick.condition("name").equal("company"));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.update("company").set("name", "company1")
										.where(trick.condition("description").equal("company"));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.update("company").set("name", "company1")
										.where(trick.condition("name").equal("company").and(
												trick.condition("description").equal("company")
										));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.update("company").set("name", "company1")
										.where(trick.condition("name").equal("company"));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.update("company").set("name", "company1")
										.where(trick.condition("name").equal("company").and(
												trick.condition("description").equal("company")
										));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.update("company").set("name", "company1")
										.where(trick.condition("description").equal("company").and(
												trick.condition("name").equal("company")
										));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.update("company").set("name", "company1");
								trick.update("company").set("name", "company2");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick().update("company").set("name", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.update("company").set("name", "company1");
								trick.update("company").set("name", "company2");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.update("company").set("name", "company2");
								trick.update("company").set("name", "company1");
							}
						}
				},

				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick().delete("company");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").trick().delete("department");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.delete("company")
										.where(trick.condition("name").equal("company"));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.delete("company")
										.where(trick.condition("name").notEqual("company"));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.delete("company")
										.where(trick.condition("name").equal("company"));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.delete("company")
										.where(trick.condition("description").equal("company"));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.delete("company")
										.where(trick.condition("name").equal("company").and(
												trick.condition("description").equal("company")
										));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.delete("company")
										.where(trick.condition("name").equal("company"));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.delete("company")
										.where(trick.condition("name").equal("company").and(
												trick.condition("description").equal("company")
										));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.delete("company")
										.where(trick.condition("description").equal("company").and(
												trick.condition("name").equal("company")
										));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.delete("company");
								trick.delete("department");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.delete("company");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.delete("company");
								trick.delete("department");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								final Trick trick = begin("1").trick();
								trick.delete("department");
								trick.delete("company");
							}
						}
				},
		};
	}

	@Test(dataProvider = "checksumFailingData", expectedExceptions = DatabaseException.class, expectedExceptionsMessageRegExp = "^CheckSum check failed .+$")
	public void gae_checksum_failing(final ChangeLog first, final ChangeLog second) {
		database.update(first);
		database.update(second);
	}
}
