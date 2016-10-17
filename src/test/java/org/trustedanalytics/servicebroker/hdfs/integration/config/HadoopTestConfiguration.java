/**
 * Copyright (c) 2015 Intel Corporation
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
package org.trustedanalytics.servicebroker.hdfs.integration.config;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.trustedanalytics.servicebroker.hdfs.config.Qualifiers;

@org.springframework.context.annotation.Configuration
public class HadoopTestConfiguration {

  @Bean
  public Configuration getHadoopConfiguration() throws IOException {
    Configuration config = new Configuration();
    config.addResource(getTestResourcePath("hdfs-site.xml"));
    config.addResource(getTestResourcePath(("core-site.xml")));
    return config;
  }

  @Bean
  @Qualifier(Qualifiers.HGM_CONFIGURATION)
  public RestTemplate getHgmTestConfiguration() throws IOException {
    return new RestTemplate();
  }

  private Path getTestResourcePath(String name) {
    return new Path(Thread.currentThread().getContextClassLoader().getResource(name).getPath());
  }
}
