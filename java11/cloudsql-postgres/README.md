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
   # And select the /usr/lib/jvm/zulu-11-amd64/bin/java version.
```

* If you haven't already, Create an App Engine app within the current Google Cloud Project

    `gcloud app create`

* If you haven't already, Setup [Application Default Credentials](https://developers.google.com/identity/protocols/application-default-credentials)

    `gcloud auth application-default login`


* [Create an instance](https://cloud.google.com/sql/docs/postgres/create-instance)

* [Create a Database](https://cloud.google.com/sql/docs/postgres/create-manage-databases)

* [Create a user](https://cloud.google.com/sql/docs/postgres/create-manage-users)

* Note the **Instance connection name** under Overview > properties

## Deploying: First, edit the [app.yaml](src/main/appengine/app.yaml) with the correct database configuration.

```bash
$ mvn clean appengine:deploy
```


## Cleaning up

* [Delete your Instance](https://cloud.google.com/sql/docs/postgres/delete-instance)

