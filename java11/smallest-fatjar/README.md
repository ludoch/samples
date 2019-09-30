# Smallest Google App Engine Java 11 Standard Application


## Setup

Before running and deploying this sample, take the following steps:

**App Engine Java 11 private Alpha** Please, become part of the App Engine [Alpha program](https://docs.google.com/forms/d/e/1FAIpQLSf5uE5eknJjFEmcVBI6sMitBU0QQ1LX_J7VrA_OTQabo6EEEw/viewform) for Java11 and wait for approval.

**App Engine Project** Use the [GCP Console](https://console.cloud.google.com/projectselector/appengine/create?lang=java) to create a new App Engine application 
You can skip this if you already have an App Engine Project.

**Cloud SDK** Download and configure the Google Cloud SDK from [here](https://cloud.google.com/sdk).

Configure the gcloud command-line environment:

```
gcloud init
gcloud auth application-default login
```

If you are using the Google Cloud Shell, change the default JDK to Java11:

```
   sudo update-alternatives --config java
   # And select the usr/lib/jvm/java-11-openjdk-amd64/bin/java version.
   # Also, set the JAVA_HOME variable for Maven to pick the correct JDK:
   export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
```

## Deploying

```bash
 mvn clean package appengine:deploy -Dapp.deploy.projectId=<your-project-id>
```

## See the application page
Navigate to http://yourprojectid.appspot.com URL, you ahould see the following page:

```
Hello World from Google App Engine Java 11.
```

Here is the app.yaml:

```
runtime: java11
```

There is no entrypoint in the configuration file, as the application is a single fat jar with the correct MANIFEST MainClass field.


