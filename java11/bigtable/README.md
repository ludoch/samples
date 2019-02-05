Bigtable-hello-j11
=================

<a href="https://console.cloud.google.com/cloudshell/open?git_repo=https://github.com/ludoch/samples&page=editor&open_in_editor==java11/bigtable/README.md">
<img alt="Open in Cloud Shell" src ="http://gstatic.com/cloudssh/images/open-btn.png"></a>

Bigtable Hello World application to Google App Engine Standard for Java 11.

* [Maven](https://maven.apache.org/download.cgi)
* [Google Cloud SDK](https://cloud.google.com/sdk/) (aka gcloud)

Initialize the Google Cloud SDK using:

    gcloud init

    gcloud auth application-default login

Then you need to [Create a Cloud Bigtable Instance](https://cloud.google.com/bigtable/docs/creating-instance)

* If you are using the Google Cloud Shell, change the default JDK to Java11:

```
   sudo update-alternatives --config java
   # And select the usr/lib/jvm/java-11-openjdk-amd64/bin/java version.
   # Also, set the JAVA_HOME variable for Maven to pick the correct JDK:
   export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
```

## Using Maven

First, edit the  private static final String INSTANCEID in the [Main.java](src/main/java/com/example.bigtable/Main.java) with the correct INSTANCEID you configured.

### Deploy to App Engine Standard for Java 11

    mvn appengine:deploy -Dapp.deploy.projectId=<your-project-id>


### When done

Cloud Bigtable Instances should be [deleted](https://cloud.google.com/bigtable/docs/deleting-instance)
when they are no longer being used as they use significant resources.
