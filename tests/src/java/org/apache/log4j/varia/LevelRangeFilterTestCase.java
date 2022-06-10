/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.log4j.varia;

import junit.framework.TestCase;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class LevelRangeFilterTestCase extends TestCase {

  public void testAssertOnMatchTrue() {
    LevelRangeFilter sut = new LevelRangeFilter();
    sut.setLevelMin(Level.INFO);
    sut.setLevelMax(Level.ERROR);
    sut.setAcceptOnMatch(true);

    assertResult(Filter.DENY, sut, Level.ALL);
    assertResult(Filter.DENY, sut, Level.DEBUG);
    assertResult(Filter.ACCEPT, sut, Level.INFO);
    assertResult(Filter.ACCEPT, sut, Level.WARN);
    assertResult(Filter.ACCEPT, sut, Level.ERROR);
    assertResult(Filter.DENY, sut, Level.FATAL);
    assertResult(Filter.DENY, sut, Level.OFF);
  }

  public void testAssertOnMatchFalse() {
    LevelRangeFilter sut = new LevelRangeFilter();
    sut.setLevelMin(Level.INFO);
    sut.setLevelMax(Level.ERROR);
    sut.setAcceptOnMatch(false);

    assertResult(Filter.DENY, sut, Level.ALL);
    assertResult(Filter.DENY, sut, Level.DEBUG);
    assertResult(Filter.NEUTRAL, sut, Level.INFO);
    assertResult(Filter.NEUTRAL, sut, Level.WARN);
    assertResult(Filter.NEUTRAL, sut, Level.ERROR);
    assertResult(Filter.DENY, sut, Level.FATAL);
    assertResult(Filter.DENY, sut, Level.OFF);
  }

  public void testAssertOnMatchNull() {
    LevelRangeFilter sut = new LevelRangeFilter();
    sut.setLevelMin(Level.INFO);
    sut.setLevelMax(Level.ERROR);

    assertResult(Filter.DENY, sut, Level.ALL);
    assertResult(Filter.DENY, sut, Level.DEBUG);
    assertResult(Filter.NEUTRAL, sut, Level.INFO);
    assertResult(Filter.NEUTRAL, sut, Level.WARN);
    assertResult(Filter.NEUTRAL, sut, Level.ERROR);
    assertResult(Filter.DENY, sut, Level.FATAL);
    assertResult(Filter.DENY, sut, Level.OFF);
  }

  public void testLevelMinNull() {
    LevelRangeFilter sut = new LevelRangeFilter();
    sut.setLevelMax(Level.ERROR);
    sut.setAcceptOnMatch(true);

    assertResult(Filter.ACCEPT, sut, Level.ALL);
    assertResult(Filter.ACCEPT, sut, Level.DEBUG);
    assertResult(Filter.ACCEPT, sut, Level.INFO);
    assertResult(Filter.ACCEPT, sut, Level.WARN);
    assertResult(Filter.ACCEPT, sut, Level.ERROR);
    assertResult(Filter.DENY, sut, Level.FATAL);
    assertResult(Filter.DENY, sut, Level.OFF);
  }

  public void testLevelMaxNull() {
    LevelRangeFilter sut = new LevelRangeFilter();
    sut.setLevelMin(Level.INFO);
    sut.setAcceptOnMatch(true);

    assertResult(Filter.DENY, sut, Level.ALL);
    assertResult(Filter.DENY, sut, Level.DEBUG);
    assertResult(Filter.ACCEPT, sut, Level.INFO);
    assertResult(Filter.ACCEPT, sut, Level.WARN);
    assertResult(Filter.ACCEPT, sut, Level.ERROR);
    assertResult(Filter.ACCEPT, sut, Level.FATAL);
    assertResult(Filter.ACCEPT, sut, Level.OFF);
  }

  public void testLevelMinMaxSame() {
    LevelRangeFilter sut = new LevelRangeFilter();
    sut.setLevelMin(Level.INFO);
    sut.setLevelMax(Level.INFO);
    sut.setAcceptOnMatch(true);

    assertResult(Filter.DENY, sut, Level.ALL);
    assertResult(Filter.DENY, sut, Level.DEBUG);
    assertResult(Filter.ACCEPT, sut, Level.INFO);
    assertResult(Filter.DENY, sut, Level.WARN);
    assertResult(Filter.DENY, sut, Level.ERROR);
    assertResult(Filter.DENY, sut, Level.FATAL);
    assertResult(Filter.DENY, sut, Level.OFF);
  }

  public void testLevelMinMaxNull() {
    LevelRangeFilter sut = new LevelRangeFilter();
    sut.setAcceptOnMatch(true);

    assertResult(Filter.ACCEPT, sut, Level.ALL);
    assertResult(Filter.ACCEPT, sut, Level.DEBUG);
    assertResult(Filter.ACCEPT, sut, Level.INFO);
    assertResult(Filter.ACCEPT, sut, Level.WARN);
    assertResult(Filter.ACCEPT, sut, Level.ERROR);
    assertResult(Filter.ACCEPT, sut, Level.FATAL);
    assertResult(Filter.ACCEPT, sut, Level.OFF);
  }

  public void testLevelMinALL() {
    LevelRangeFilter sut = new LevelRangeFilter();
    sut.setLevelMin(Level.ALL);

    assertResult(Filter.NEUTRAL, sut, Level.ALL);
    assertResult(Filter.NEUTRAL, sut, Level.DEBUG);
    assertResult(Filter.NEUTRAL, sut, Level.INFO);
    assertResult(Filter.NEUTRAL, sut, Level.WARN);
    assertResult(Filter.NEUTRAL, sut, Level.ERROR);
    assertResult(Filter.NEUTRAL, sut, Level.FATAL);
    assertResult(Filter.NEUTRAL, sut, Level.OFF);
  }

  private static void assertResult(int expected, LevelRangeFilter filter, Priority level) {
    assertEquals(expected,
        filter.decide(new LoggingEvent(null, Logger.getRootLogger(), level, null, null)));
  }
}
