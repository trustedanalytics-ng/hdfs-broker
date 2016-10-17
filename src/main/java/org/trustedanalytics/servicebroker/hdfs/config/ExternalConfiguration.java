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
package org.trustedanalytics.servicebroker.hdfs.config;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
public class ExternalConfiguration {

  @Value("${store.user}")
  @NotNull
  @Getter @Setter
  private String user;

  @Value("${store.password}")
  @NotNull
  @Getter @Setter
  private String password;

  @Value("${store.keytabPath}")
  @NotNull
  @Getter @Setter
  private String keytabPah;

  @Value("${hdfs.userspace.chroot}")
  @NotNull
  @Getter @Setter
  private String userspaceChroot;

  @Value("${hdfs.superuser}")
  @NotNull
  @Getter @Setter
  private String hdfsSuperuser;

  @Value("${hdfs.keytab.path}")
  @NotNull
  @Getter @Setter
  private String keytabPath;

  @Value("${hdfs.configuration.path}")
  @NotNull
  @Getter @Setter
  private String configurationPath;
}
