package org.greatage.db.gae;

import org.greatage.db.ChangeLog;
import org.greatage.db.DatabaseException;
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
								begin("1");
								insert("company").set("name", "company1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								insert("company").set("name", "company2");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								insert("company").set("name", "company1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								insert("company").set("description", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								insert("company").set("name", "company1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								insert("department").set("name", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								insert("company").set("name", "company1");
								insert("company").set("name", "company2");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								insert("company").set("name", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								insert("company").set("name", "company1");
								insert("company").set("name", "company2");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								insert("company").set("name", "company2");
								insert("company").set("name", "company1");
							}
						}
				},

				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company2");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("description", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("department").set("name", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1")
										.where(condition("name").equal("company"));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1")
										.where(condition("name").equal("company"));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1")
										.where(condition("name").notEqual("company"));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1")
										.where(condition("name").equal("company"));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1")
										.where(condition("description").equal("company"));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1")
										.where(condition("name").equal("company").and(
												condition("description").equal("company")
										));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1")
										.where(condition("name").equal("company"));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1")
										.where(condition("name").equal("company").and(
												condition("description").equal("company")
										));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1")
										.where(condition("description").equal("company").and(
												condition("name").equal("company")
										));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1");
								update("company").set("name", "company2");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company1");
								update("company").set("name", "company2");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								update("company").set("name", "company2");
								update("company").set("name", "company1");
							}
						}
				},

				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("company");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("department");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("company")
										.where(condition("name").equal("company"));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("company")
										.where(condition("name").notEqual("company"));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("company")
										.where(condition("name").equal("company"));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("company")
										.where(condition("description").equal("company"));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("company")
										.where(condition("name").equal("company").and(
												condition("description").equal("company")
										));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("company")
										.where(condition("name").equal("company"));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("company")
										.where(condition("name").equal("company").and(
												condition("description").equal("company")
										));
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("company")
										.where(condition("description").equal("company").and(
												condition("name").equal("company")
										));
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("company");
								delete("department");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("company");
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("company");
								delete("department");
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1");
								delete("department");
								delete("company");
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
