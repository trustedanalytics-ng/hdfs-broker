[![Build Status](https://travis-ci.org/trustedanalytics/hdfs-broker.svg?branch=master)](https://travis-ci.org/trustedanalytics/hdfs-broker)
[![Dependency Status](https://www.versioneye.com/user/projects/5723690aba37ce0031fc1e1d/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5723690aba37ce0031fc1e1d)

hdfs-broker
===========

Broker for resource creation on Hadoop Distributed File System.

# How to use it?
To use hdfs-broker, you need to build it from sources configure, deploy, create instance and bind it to your app. Follow steps described below. 

## Build 
Run command for compile and package.: 
```
mvn clean package
```
Run optional command for create docker image:
```
mvn docker:build
```

## Plans

  * Plain-dir : Create directory on HDFS within storage space shared across your organization
  * Encrypted-dir : Create encrypted directory on HDFS within storage space shared across your organization

## Configure

###Profiles 
Each profile describes authentication used during communication with Hadoop, at least one is required: 

  * simple
  * kerberos
  
### Broker library
Broker library is java spring library, which simplifies broker store implementation. Currently hdfs-broker uses zookeeper-broker store implementation, which stores information about every instance in secured znode.

* obligatory
  * simple
    * STORE_CLUSTER : zookeeper quorum address (example: host1:2181,host2:2181,host:2181)
    * STORE_USER : user used to authenticate with zookeeper broker store
    * STORE_PASSWORD : password for store user
  * kerberos
    * STORE_KEYTABPATH : path to the keytab file, which will be used to authenticate STORE_USER in kerberos

### Other
* obligatory
  * simple
    * USER_PASSWORD - password to interact with service broker Rest API
    * BASE_GUID - base id for catalog plan creation
    * CATALOG_SERVICENAME - service name in catalog (default: hdfs)
    * CATALOG_SERVICEID - service id in catalog (default: hdfs)
    * HDFS_USER : name of the superuser on hdfs wich will be used to create encryption zones
    * SYSTEM_USER : name of the regular user which will be used to create hdfs directories
    * HDFS_CONFIGURATION_PATH : path of the hdfs-conf directory
    * BROKER_USERSPACE_PATH : directory where instances will be created
  * kerberos
    * KRB_REALM : Kerberos Realm (kerberos profile required)
    * KRB_KDC : Key Distribution Center Adddress (kerberos profile required)
    * HDFS_KEYTAB_PATH : path to the keytab file, which will be used to authenticate HDFS_USER in kerberos
    * SYSTEM_USER_KEYTAB_PATH : path to the keytab file, which will be used to authenticate SYSTEM_USER in kerberos

## Useful links

Offering template for TAP platform:
 * https://github.com/intel-data/tap-deploy/blob/master/roles/tap-marketplace-offerings/templates/hdfs/offering.json

Broker library:
 * https://github.com/intel-data/broker-lib    

Cloud foundry resources that are helpful when troubleshooting service brokers : 
 * http://docs.cloudfoundry.org/services/api.html
 * http://docs.cloudfoundry.org/devguide/services/managing-services.html#update_service
 * http://docs.cloudfoundry.org/services/access-control.html

## On the app side

For spring applications use https://github.com/trustedanalytics/hadoop-spring-utils. 

For regular java applications use https://github.com/trustedanalytics/hadoop-utils. 
