# Google App Engine Standard Environment Samples for Java 11

<a href="https://console.cloud.google.com/cloudshell/open?git_repo=https://github.com/ludoch/samples&page=editor&open_in_editor=java11/README.md">
<img alt="Open in Cloud Shell" src ="http://gstatic.com/cloudssh/images/open-btn.png"></a>

This is a repository that contains Java code samples for Google App Engine
standard Java 11 environment.


## Prerequisites

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


## Samples

bigtable
cloudsql-postgres
springboot
appengine-simple-jetty-main
gaeinfo
spanner
bigquery
cloudsql
helidon-quickstart-mp
sparkjava


### appengine-simple-jetty-main

This is not a sample per se, but a shared artifact as a Jar Main Class that can start the Jetty Web Server, with a given war file as an argument.
You will see some samples, creating a warfile.war which is used as an argument in the App Engine app.yaml entrypoint field.
This is basically a Jetty Embedded server, supporting JSPs, loading a given war file as a "/" webcontext and listening to port 8080 as a Main class.

- [Code](appengine-simple-jetty-main)


### cloudsql-postgres

This sample demonstrates how to use the [Google Cloud Postgress][appid] with App Engine Java.

- [Documentation][https://cloud.google.com/sql/docs/postgres/]
- [Code](postgres/README.md)


### bigtable

- [BigTable Sample Applications][bigtable]

[bigtable]: https://cloud.google.com/bigtable



