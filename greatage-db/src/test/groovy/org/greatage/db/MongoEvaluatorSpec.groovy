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
    uri = new MongoClientURI("mongodb://localhost/test.changes")
    client = new MongoClient(uri)
    evaluator = new MongoEvaluator(new SimpleProcessExecutor(1), uri, 3)

    // clear db before tests
    client.getDB(uri.database).dropDatabase()
  }

  void cleanup() {
    // clear db after each test
    client.getDB(uri.database).dropDatabase()
  }

  def "//! should be parsed as changeset id"() {
    given:
    def changes = client.getDB(uri.database).getCollection(uri.collection)

    when:
    evaluator.update(open("/scripts/1.js"))
    then:
    changes.count(new BasicDBObject("_id", "GA-1")) == 1

    when:
    evaluator.update(open("/scripts/2.js"))
    then:
    changes.count(new BasicDBObject("_id", "GA-2")) == 1
    changes.count(new BasicDBObject("_id", "GA-3")) == 1
    changes.count(new BasicDBObject("_id", "GA-4")) == 1
    changes.count(new BasicDBObject("_id", "GA-5")) == 1
  }

  def "//@ should be parsed as changeset author"() {
    given:
    def changes = client.getDB(uri.database).getCollection(uri.collection)

    when:
    evaluator.update(open("/scripts/1.js"))
    then:
    changes.findOne(new BasicDBObject("_id", "GA-1"))["author"] == "Vasya Pupkin"

    when:
    evaluator.update(open("/scripts/2.js"))
    then:
    changes.findOne(new BasicDBObject("_id", "GA-2"))["author"] == "Vasya Pupkin"
    changes.findOne(new BasicDBObject("_id", "GA-3"))["author"] == ""
    changes.findOne(new BasicDBObject("_id", "GA-4"))["author"] == "Vasya"
    changes.findOne(new BasicDBObject("_id", "GA-5"))["author"] == "Pupkin"
  }

  def "//# should be parsed as changeset comment"() {
    given:
    def changes = client.getDB(uri.database).getCollection(uri.collection)

    when:
    evaluator.update(open("/scripts/1.js"))
    then:
    changes.findOne(new BasicDBObject("_id", "GA-1"))["comment"] == "Sets address for all companies"

    when:
    evaluator.update(open("/scripts/2.js"))
    then:
    changes.findOne(new BasicDBObject("_id", "GA-2"))["comment"] == "Fake change"
    changes.findOne(new BasicDBObject("_id", "GA-3"))["comment"] == "Change 3"
    changes.findOne(new BasicDBObject("_id", "GA-4"))["comment"] == ""
    changes.findOne(new BasicDBObject("_id", "GA-5"))["comment"] == ""
  }

  def "all other content should be parsed as changeset script"() {
    given:
    def compamies = client.getDB(uri.database).getCollection("companies")

    when:
    evaluator.update(open("/scripts/1.js"))
    then:
    compamies.count(new BasicDBObject("_id", "company1")) == 1

    when:
    evaluator.update(open("/scripts/2.js"))
    then:
    compamies.count(new BasicDBObject("_id", "company1")) == 1
    compamies.count(new BasicDBObject("_id", "company3")) == 1
    compamies.count(new BasicDBObject("_id", "company5")) == 1
    compamies.findOne(new BasicDBObject("_id", "company1"))["address"] == "Belarus"
    compamies.findOne(new BasicDBObject("_id", "company3"))["address"] == "Belarus"
    compamies.findOne(new BasicDBObject("_id", "company5"))["address"] == null
  }

  private InputStream open(final String resource) {
    return getClass().getResourceAsStream(resource)
  }
}
