# Cloud SQL sample for Google App Engine Java 11

<a href="https://console.cloud.google.com/cloudshell/open?git_repo=https://github.com/ludoch/samples&page=editor&open_in_editor==java11/cloudsql/README.md">
<img alt="Open in Cloud Shell" src ="http://gstatic.com/cloudssh/images/open-btn.png"></a>

This sample demonstrates how to use [Cloud SQL](https://cloud.google.com/cloudsql/) on Google App
Engine standard Java 11
More configuration information [at this page](https://cloud.google.com/sql/docs/mysql/connect-app-engine)

## Setup

* If you haven't already, Download and initialize the [Cloud SDK](https://cloud.google.com/sdk/)

    `gcloud init`


* If you are using the Google Cloud Shell, change the default JDK to Java11:
```
   sudo update-alternatives --config java
   # And select the /usr/lib/jvm/zulu-11-amd64/bin/java version.
```

* If this is your first time creating an App engine application:
```
   gcloud app create
```

* If you haven't already, Setup
[Application Default Credentials](https://developers.google.com/identity/protocols/application-default-credentials)

    `gcloud auth application-default login`


* [Create an instance](https://cloud.google.com/sql/docs/mysql/create-instance)

* [Create a Database](https://cloud.google.com/sql/docs/mysql/create-manage-databases)

* Note the **Instance connection name** under Overview > properties


## Deploying

Make sure you edit the env variables defined in [app.yaml](src/mail/appengine/app.yaml) to correspond to your database.
```bash
$ mvn clean package appengine:deploy
```

## Cleaning up

* [Delete your Instance](https://cloud.google.com/sql/docs/mysql/delete-instance)

