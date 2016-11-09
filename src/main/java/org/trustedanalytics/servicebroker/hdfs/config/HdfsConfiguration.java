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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.security.auth.login.LoginException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.trustedanalytics.hadoop.kerberos.KrbLoginManager;
import org.trustedanalytics.hadoop.kerberos.KrbLoginManagerFactory;
import org.trustedanalytics.servicebroker.framework.kerberos.KerberosProperties;

import sun.security.krb5.KrbException;

@Profile("!integration-test")
@org.springframework.context.annotation.Configuration
public class HdfsConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(HdfsConfiguration.class);

  @Autowired
  private KerberosProperties kerberosProperties;

  @Autowired
  private ExternalConfiguration configuration;

  @Autowired
  private Configuration hadoopConfiguration;

  @Bean
  @Profile(Qualifiers.SIMPLE)
  @Qualifier(Qualifiers.USER_QUALIFIER)
  public FileSystem getUserFileSystem()
      throws InterruptedException, URISyntaxException, LoginException, IOException {
    return getInsecureFileSystem(configuration.getBrokerUser());
  }

  @Bean
  @Profile(Qualifiers.SIMPLE)
  @Qualifier(Qualifiers.SUPER_USER_QUALIFIER)
  public FileSystem getAdminFileSystem()
      throws InterruptedException, URISyntaxException, LoginException, IOException, KrbException {
    return getInsecureFileSystem(configuration.getHdfsSuperUser());
  }

  @Bean
  @Profile(Qualifiers.KERBEROS)
  @Qualifier(Qualifiers.USER_QUALIFIER)
  public FileSystem getUserSecureFileSystem()
      throws InterruptedException, URISyntaxException, LoginException, IOException, KrbException {
   loginToKerberos(configuration.getBrokerUser(), configuration.getBrokerUserKeytabPath());
    return getFileSystemForUser(hadoopConfiguration, configuration.getBrokerUser());
  }

  @Bean
  @Profile(Qualifiers.KERBEROS)
  @Qualifier(Qualifiers.SUPER_USER_QUALIFIER)
  public FileSystem getAdminSecureFileSystem()
      throws InterruptedException, URISyntaxException, LoginException, IOException, KrbException {
    loginToKerberos(configuration.getHdfsSuperUser(), configuration.getKeytabPath());
    return getFileSystemForUser(hadoopConfiguration, configuration.getHdfsSuperUser());
  }

  private void loginToKerberos(String user, String keytabPath) throws LoginException, KrbException, IOException {
    KrbLoginManager loginManager = KrbLoginManagerFactory.getInstance()
        .getKrbLoginManagerInstance(kerberosProperties.getKdc(), kerberosProperties.getRealm());
    loginManager.loginInHadoop(loginManager.loginWithKeyTab(user, keytabPath), hadoopConfiguration);
  }

  private FileSystem getInsecureFileSystem(String user)
      throws InterruptedException, URISyntaxException, LoginException, IOException {
    return getFileSystemForUser(hadoopConfiguration, user);
  }

  private FileSystem getFileSystemForUser(Configuration config, String user)
      throws URISyntaxException, IOException, InterruptedException {
    LOGGER.info("Creating filesytem with for brokerUser: " + user);
    return FileSystem.get(new URI(config.getRaw(HdfsConstants.HADOOP_DEFAULT_FS)), config, user);
  }

}
