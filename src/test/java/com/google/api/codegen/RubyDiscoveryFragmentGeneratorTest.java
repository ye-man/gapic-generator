/* Copyright 2016 Google Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.api.codegen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.util.List;

/**
 * Java discovery doc fragment generator baseline tests.
 */
@RunWith(Parameterized.class)
public class RubyDiscoveryFragmentGeneratorTest extends DiscoveryFragmentGeneratorTestBase {

  public RubyDiscoveryFragmentGeneratorTest(
      String name, String discoveryDocFileName, String[] gapicConfigFileNames) {
    super(name, discoveryDocFileName, gapicConfigFileNames);
  }

  // Skip this test, because the Ruby client library doesn't create client library for it.
  private static ImmutableSet<String> SKIP_TEST =
      ImmutableSet.<String>builder().add("dataflow.v1b3.json").build();

  /**
   * Declares test parameters, each one an array of values passed to the constructor, with the first
   * element a name, the second a discovery doc, and the third a partial GAPIC config.
   */
  @Parameters(name = "{0}")
  public static List<Object[]> testedConfigs() {
    File dir =
        new File(
            System.getProperty("user.dir"),
            "src/test/java/com/google/api/codegen/testdata/discoveries");
    ImmutableList.Builder<Object[]> builder = ImmutableList.<Object[]>builder();
    for (File file : dir.listFiles(new DiscoveryFile())) {
      String fileName = file.getName();
      if (SKIP_TEST.contains(fileName)) {
        continue;
      }
      builder.add(
          new Object[] {
            "ruby_" + fileName,
            "discoveries/" + fileName,
            new String[] {"com/google/api/codegen/ruby/ruby_discovery.yaml"}
          });
    }
    return builder.build();
  }

  @Before
  public void putTestDirectory() {
    getTestDataLocator().addTestDataSource(this.getClass(), "testdata/discoveries/ruby");
  }

  // Tests
  // =====

  @Test
  public void fragments() throws Exception {
    test();
  }
}