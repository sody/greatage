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
								begin("1").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").comment("some comment").end();
							}
						}
				},

				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").insert("company").set("name", "company1").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").insert("company").set("name", "company2").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").insert("company").set("name", "company1").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").insert("company").set("description", "company1").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").insert("company").set("name", "company1").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").insert("department").set("name", "company1").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1")
										.insert("company").set("name", "company1").end()
										.insert("company").set("name", "company2").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").insert("company").set("name", "company1").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1")
										.insert("company").set("name", "company1").end()
										.insert("company").set("name", "company2").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1")
										.insert("company").set("name", "company2").end()
										.insert("company").set("name", "company1").end();
							}
						}
				},

				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company2").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("description", "company1").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("department").set("name", "company1").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1").where("name").equal("company").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1").where("name").equal("company").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1").where("name").notEqual("company").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1").where("name").equal("company").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1").where("description").equal("company").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1")
										.where("name").equal("company")
										.and("description").equal("company").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1").where("name").equal("company").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1")
										.where("name").equal("company")
										.and("description").equal("company").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1")
										.where("description").equal("company")
										.and("name").equal("company").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1")
										.update("company").set("name", "company1").end()
										.update("company").set("name", "company2").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1").update("company").set("name", "company1").end();
							}
						}
				},
				{
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1")
										.update("company").set("name", "company1").end()
										.update("company").set("name", "company2").end();
							}
						},
						new ChangeLog() {
							@Override
							protected void init() {
								location("test");
								begin("1")
										.update("company").set("name", "company2").end()
										.update("company").set("name", "company1").end();
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
