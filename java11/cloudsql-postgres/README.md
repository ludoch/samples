# Postgres SQL sample for Google App Engine Java11

<a href="https://console.cloud.google.com/cloudshell/open?git_repo=https://github.com/ludoch/samples&page=editor&open_in_editor==java11/cloudsql-postgres/README.md">
<img alt="Open in Cloud Shell" src ="http://gstatic.com/cloudssh/images/open-btn.png"></a>

This sample demonstrates how to use [PostgreSql](https://cloud.google.com/sql/) on Google App
Engine standard Java 11

## Setup

* If you haven't already, Download and initialize the [Cloud SDK](https://cloud.google.com/sdk/)

    `gcloud init`


* If you are using the Google Cloud Shell, change the default JDK to Java11:

```
   sudo update-alternatives --config java
   # And select the usr/lib/jvm/java-11-openjdk-amd64/bin/java version.
   # Also, set the JAVA_HOME variable for Maven to pick the correct JDK:
   export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
```

* If you haven't already, Create an App Engine app within the current Google Cloud Project

    `gcloud app create`

* If you haven't already, Setup [Application Default Credentials](https://developers.google.com/identity/protocols/application-default-credentials)

    `gcloud auth application-default login`


* [Create an instance](https://cloud.google.com/sql/docs/postgres/create-instance)

* [Create a Database](https://cloud.google.com/sql/docs/postgres/create-manage-databases)

* [Create a user](https://cloud.google.com/sql/docs/postgres/create-manage-users)

* Note the **Instance connection name** under Overview > properties

## Configuring the Env Variables in app.yaml

Edit the [app.yaml](src/mail/appengine/app.yaml) file and change the 4 env variables to match your environment:

```
env_variables:
  DB_INSTANCE: PROJECT:us-central1:instance
  DB_DATABASE: DBname
  DB_USER: postgres
  DB_PASSWORD: password
  # See bug: https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory/issues/110
  CLOUD_SQL_FORCE_UNIX_SOCKET: true
```

## Postgresql Driver dependencies

See the dependencies in the pom.xml: the first one is the standard Postgresql driver
and the second one (1.0.12 or later for Java11 support) is the Google Postgresql Socket factory
that is used to connect to the Java11 runtime socket configured for accessing Google
Cloud Postgresql.

```
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <version>42.2.5</version>
    </dependency>

    <dependency>
      <groupId>com.google.cloud.sql</groupId>
      <artifactId>postgres-socket-factory</artifactId>
      <version>1.0.12</version>
    </dependency>
```

As you can see in the sample, we also added a connection pool (from Open Source HikariCP).

## Deploying

```bash
 mvn clean package appengine:deploy -Dapp.deploy.projectId=<your-project-id>
```


## Cleaning up

* [Delete your Instance](https://cloud.google.com/sql/docs/postgres/delete-instance)

