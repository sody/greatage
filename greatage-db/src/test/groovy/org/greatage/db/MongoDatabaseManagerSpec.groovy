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

import org.greatage.db.internal.MongoDatabaseManager
import spock.lang.Specification

/**
 * @author Ivan Khalopik
 */
class MongoDatabaseManagerSpec extends Specification {

  def "//! should be parsed as changeset id"() {
    given:
    def evaluator = Mock(Evaluator)
    def changeLog = Mock(Evaluator.ChangeLog)
    def changeSet = Mock(Evaluator.ChangeSet)
    def manager = new MongoDatabaseManager(evaluator)
    // training
    evaluator.changeLog() >> changeLog
    changeLog.flush() >> changeLog
    changeSet.apply() >> changeLog
    changeSet.author(_) >> changeSet
    changeSet.comment(_) >> changeSet
    changeSet.append(_) >> changeSet

    when:
    manager.update(open("/scripts/1.js"))
    then:
    1 * changeLog.changeSet("GA-1") >> changeSet

    when:
    manager.update(open("/scripts/2.js"))
    then:
    1 * changeLog.changeSet("GA-2") >> changeSet
    1 * changeLog.changeSet("GA-3") >> changeSet
    1 * changeLog.changeSet("GA-4") >> changeSet
    1 * changeLog.changeSet("GA-5") >> changeSet
  }

  def "//@ should be parsed as changeset author"() {
    given:
    def evaluator = Mock(Evaluator)
    def changeLog = Mock(Evaluator.ChangeLog)
    def changeSet = Mock(Evaluator.ChangeSet)
    def manager = new MongoDatabaseManager(evaluator)
    // training
    evaluator.changeLog() >> changeLog
    changeLog.flush() >> changeLog
    changeLog.changeSet(_) >> changeSet
    changeSet.apply() >> changeLog
    changeSet.comment(_) >> changeSet
    changeSet.append(_) >> changeSet

    when:
    manager.update(open("/scripts/1.js"))
    then:
    1 * changeSet.author("Vasya Pupkin") >> changeSet

    when:
    manager.update(open("/scripts/2.js"))
    then:
    1 * changeSet.author("Vasya Pupkin") >> changeSet
    1 * changeSet.author("Vasya") >> changeSet
    1 * changeSet.author("Pupkin") >> changeSet
  }

  def "//# should be parsed as changeset comment"() {
    given:
    def evaluator = Mock(Evaluator)
    def changeLog = Mock(Evaluator.ChangeLog)
    def changeSet = Mock(Evaluator.ChangeSet)
    def manager = new MongoDatabaseManager(evaluator)
    // training
    evaluator.changeLog() >> changeLog
    changeLog.flush() >> changeLog
    changeLog.changeSet(_) >> changeSet
    changeSet.apply() >> changeLog
    changeSet.author(_) >> changeSet
    changeSet.append(_) >> changeSet

    when:
    manager.update(open("/scripts/1.js"))
    then:
    1 * changeSet.comment("Sets address for all companies") >> changeSet

    when:
    manager.update(open("/scripts/2.js"))
    then:
    1 * changeSet.comment("Fake change") >> changeSet
    1 * changeSet.comment("Change 3") >> changeSet
  }

  def "all other content should be parsed as changeset script"() {
    given:
    def evaluator = Mock(Evaluator)
    def changeLog = Mock(Evaluator.ChangeLog)
    def changeSet = Mock(Evaluator.ChangeSet)
    def manager = new MongoDatabaseManager(evaluator)
    // training
    evaluator.changeLog() >> changeLog
    changeLog.flush() >> changeLog
    changeLog.changeSet(_) >> changeSet
    changeSet.apply() >> changeLog
    changeSet.author(_) >> changeSet
    changeSet.comment(_) >> changeSet
    changeSet.append(_) >> changeSet

    when:
    manager.update(open("/scripts/2.js"))
    then:
    1 * changeSet.append("db.companies.insert({_id: 'company3'});") >> changeSet
    1 * changeSet.append("db.companies.insert({_id: 'company4'});") >> changeSet
    1 * changeSet.append("db.companies.insert({_id: 'company5'});") >> changeSet
  }

  private InputStream open(final String resource) {
    return getClass().getResourceAsStream(resource)
  }
}
