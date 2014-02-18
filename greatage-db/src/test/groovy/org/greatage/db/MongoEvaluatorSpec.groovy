/*
 * Copyright (c) 2008-2014 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.db

import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import org.greatage.db.internal.MongoEvaluator
import org.greatage.db.internal.SimpleProcessExecutor
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Ivan Khalopik
 */
class MongoEvaluatorSpec extends Specification {

  @Shared
  private MongoClient client

  @Shared
  private MongoClientURI uri

  @Shared
  private MongoEvaluator evaluator

  void setupSpec() {
    uri = new MongoClientURI("mongodb://localhost/test")
    client = new MongoClient(uri)
    evaluator = new MongoEvaluator(new SimpleProcessExecutor(1), uri)
  }

  void cleanup() {
    client.getDB(uri.database).dropDatabase()
  }

  def "should apply single changeset"() {
    given:
    def companies = client.getDB(uri.database).getCollection("companies")

    when:
    evaluator.changeLog()
            .changeSet("GA-1")
            .append("db.companies.insert({_id: 'company1'});")
            .apply()
            .flush()
    then:
    companies.count(new BasicDBObject("_id", "company1")) == 1
  }

  def "should not apply changeset if it has already been applied"() {
    given:
    def companies = client.getDB(uri.database).getCollection("companies")

    when:
    evaluator.changeLog()
            .changeSet("GA-1")
            .append("db.companies.insert({name: 'company1'});")
            .apply()
            .flush()
    then:
    companies.count(new BasicDBObject("name", "company1")) == 1

    when:
    evaluator.changeLog()
            .changeSet("GA-1")
            .append("db.companies.insert({name: 'company1'});")
            .apply()
            .flush()
    then:
    companies.count(new BasicDBObject("name", "company1")) == 1
  }

  def "should fail if changeset checksum changed"() {
    given:
    def companies = client.getDB(uri.database).getCollection("companies")

    when:
    evaluator.changeLog()
            .changeSet("GA-1")
            .append("db.companies.insert({name: 'company1'});")
            .apply()
            .flush()
    then:
    companies.count(new BasicDBObject("name", "company1")) == 1

    when:
    evaluator.changeLog()
            .changeSet("GA-1")
            .append("db.companies.insert({name: 'company2'});")
            .apply()
            .flush()
    then:
    thrown(RuntimeException)
  }

  def "should not fail if changeset checksum doesn't changed"() {
    given:
    def companies = client.getDB(uri.database).getCollection("companies")

    when:
    evaluator.changeLog()
            .changeSet("GA-1")
            .append("db.companies.insert({name: 'company1'});")
            .apply()
            .flush()
    then:
    companies.count(new BasicDBObject("name", "company1")) == 1

    when:
    evaluator.changeLog()
            .changeSet("GA-1")
            .author("Test User")
            .append("db.companies.insert({name: 'company1'});")
            .apply()
            .flush()
    then:
    noExceptionThrown()

    when:
    evaluator.changeLog()
            .changeSet("GA-1")
            .comment("Add Company1")
            .append("db.companies.insert({name: 'company1'});")
            .apply()
            .flush()
    then:
    noExceptionThrown()
  }

  def "should apply multiple changesets"() {
    given:
    def companies = client.getDB(uri.database).getCollection("companies")

    when:
    evaluator.changeLog()
            .changeSet("GA-1")
            .append("db.companies.insert({_id: 'company1'});")
            .apply()
            .changeSet("GA-2")
            .append("db.companies.insert({_id: 'company2'});")
            .apply()
            .changeSet("GA-3")
            .append("db.companies.insert({_id: 'company3'});")
            .apply()
            .flush()
    then:
    companies.count(new BasicDBObject("_id", "company1")) == 1
    companies.count(new BasicDBObject("_id", "company2")) == 1
    companies.count(new BasicDBObject("_id", "company3")) == 1
  }

  def "should add record in changelog collection for single changeset"() {
    given:
    def changelog = client.getDB(uri.database).getCollection("changelog")

    when:
    evaluator.changeLog()
            .changeSet("GA-1")
            .append("db.companies.insert({_id: 'company1'});")
            .apply()
            .flush()
    then:
    changelog.count(new BasicDBObject("_id", "GA-1")) == 1
  }

  def "should add record in changelog collection for each changeset"() {
    given:
    def changelog = client.getDB(uri.database).getCollection("changelog")

    when:
    evaluator.changeLog()
            .changeSet("GA-1")
            .append("db.companies.insert({_id: 'company1'});")
            .apply()
            .changeSet("GA-2")
            .append("db.companies.insert({_id: 'company2'});")
            .apply()
            .changeSet("GA-3")
            .append("db.companies.insert({_id: 'company3'});")
            .apply()
            .flush()
    then:
    changelog.count(new BasicDBObject("_id", "GA-1")) == 1
    changelog.count(new BasicDBObject("_id", "GA-2")) == 1
    changelog.count(new BasicDBObject("_id", "GA-3")) == 1
  }
}
