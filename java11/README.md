# Google App Engine Standard Environment Samples for Java 11

<a href="https://console.cloud.google.com/cloudshell/open?git_repo=https://github.com/ludoch/samples&page=editor&open_in_editor=java11/README.md">
<img alt="Open in Cloud Shell" src ="http://gstatic.com/cloudssh/images/open-btn.png"></a>

This is a repository that contains Java code samples for Google App Engine
standard Java 11 environment.


## Prerequisites


**App Engine Java 11 private Alpha** Please, become part of the App Engine [Alpha program](https://docs.google.com/forms/d/e/1FAIpQLSf5uE5eknJjFEmcVBI6sMitBU0QQ1LX_J7VrA_OTQabo6EEEw/viewform) for Java11 and wait for approval.

[**Read the User Guide in this repository.**](UserGuide.md)

### Download Maven

These samples use the [Apache Maven][maven] build system. Before getting
started, be sure to [download][maven-download] and [install][maven-install] it.
When you use Maven as described here, it will automatically download the needed
client libraries.

[maven]: https://maven.apache.org
[maven-download]: https://maven.apache.org/download.cgi
[maven-install]: https://maven.apache.org/install.html

### Create a Project in the Google Cloud Platform Console

If you haven't already created a project, create one now. Projects enable you to
manage all Google Cloud Platform resources for your app, including deployment,
access control, billing, and services.

1. Open the [Cloud Platform Console][cloud-console].
1. In the drop-down menu at the top, select **Create a project**.
1. Give your project a name.
1. Make a note of the project ID, which might be different from the project
   name. The project ID is used in commands and in configurations.

[cloud-console]: https://console.cloud.google.com/

### Google Cloud Shell, Open JDK 11 setup:

To switch to an Open JDK 11 in a Cloud shell session, you can use:

```
   sudo update-alternatives --config java
   # And select the /usr/lib/jvm/zulu-11-amd64/bin/java version.
   # Also, set the JAVA_HOME variable for Maven to pick the correct JDK:
   export JAVA_HOME=/usr/lib/jvm/zulu-11-amd64
```

## List of App Engine Standard Java11 Alpha Runtime Samples
  
 - [smallest-sample](smallest-sample)
 - [smallest-fatjar](smallest-fatjar)
 - [gaeinfo](gaeinfo)
 - [helidon-quickstart-mp](helidon-quickstart-mp)
 - [sparkjava](sparkjava)
 - [Big Table](bigtable)
 - [cloudsql](cloudsql)
 - [PostGreSQL](cloud-sqlpostgres)
 - [springboot](springboot)
 - [spanner](spanner)
 - [bigquery](bigquery)



### appengine-simple-jetty-main

This is not a sample per se, but a shared artifact as a Jar Main Class that can start the Jetty Web Server, with a given war file as an argument.
You will see some samples, creating a warfile.war which is used as an argument in the App Engine app.yaml entrypoint field.
This is basically a Jetty Embedded server, supporting JSPs, loading a given war file as a "/" webcontext and listening to port 8080 as a Main class.

- [appengine-simple-jetty-main](appengine-simple-jetty-main)

