#App Engine Standard Java11 User Guide (Alpha)
ludo at google.com
Last updated: 2019-01-24

**Disclaimer**: The App Engine Java11 runtime is Alpha Early Access, not suitable for production applications. We appreciate your collaboration as a Cloud Early Access Program Trusted Tester in reporting your feedback.

Google form to apply for [Alpha](https://docs.google.com/forms/d/1gcKdMq8Xa6eDunXBDEmF20yp2BdJcFiAkbKG7FhYEVk/). Make sure you sign the Cloud [TTA document](http://cloud-tta.appspot.com) to get whitelisted.

## Introduction
We are releasing an Alpha version of the App Engine standard Java runtime that is based on OpenJDK 11. This alpha release allows you to start testing to the Java11 runtime and help identify any issues prior to Beta and GA. This is in addition to the current Java8 GAE runtime. The new Java11 runtime is not backward compatible with the existing Java8 runtime, and does not support the Java8 App Engine Java APIs anymore. The new Java11 runtime configuration is now done with an app.yaml file instead of an appengine-web.xml. It supports arbitrary Java applications as long as they contain a Main class that can be start with the java command line. The applications must listen to port 8080 in order to be served.

Please report any issues on [https://issuetracker.google.com/savedsearches/559750](https://issuetracker.google.com/savedsearches/559750) (add Java11 in the title to help the triage).

## Prerequisites
Before you start developing, download the latest version of the Google Cloud SDK or update your Cloud SDK to the current version (231 or later):
gcloud components update
	To deploy using maven, you will need to add the App Engine Maven Plugin (version 2.0.0.rc05 or above) to your pom.xml:
	
```	
<plugin>
  <groupId>com.google.cloud.tools</groupId>
  <artifactId>appengine-maven-plugin</artifactId>
  <version>2.0.0-rc5</version>
</plugin>
```
And use: 

	mvn appengine:deploy command.
	
Other options for deploying include using the gcloud app deploy command or the App Engine Gradle plugin (version 2.0.0-rc5 or above).
Follow the instructions for your application framework to configure the build of an executable JAR. This executable JAR must run via java -jar app.jar. For example, refer to the Spring Boot documentation.
Feel free to upgrade your pom.xml file to use OpenJDK 11 features if needed, via:

```
 <properties>
   <maven.compiler.target>11</maven.compiler.target>
   <maven.compiler.source>11</maven.compiler.source>
 </properties>
```

## Java11 runtime code samples
This is a work in progress, but you can follow the changes in the samples Github repo at: https://github.com/ludoch/samples. It contains the following samples:

* [helidon-quickstart-mp](https://github.com/ludoch/samples/java11/helidon-quickstart-mp): A simple Java EE microprofile demo.
* [Smallest-sample](https://github.com/ludoch/samples/java11/smallest-sample): The smallest application possible without Maven, Gradle or even a local OpenJDK installation
* [Smallest-fatjar](https://github.com/ludoch/samples/java11/smallest-fatjar) : A very small "fat" (self-contained) jar using the embedded OpenJDK Web Server[a][b][c].
* [Cloud Sql](https://github.com/ludoch/samples/java11cCloudsql): A Jetty9 based Web Application accessing Google Cloud SQL
* [Cloud Sql-postgres](https://github.com/ludoch/samples/java11/cloudsql-postgres): A Jetty9 based Web Application accessing Google Cloud PostGreSQL
* [Micronaut-hello-java11](https://github.com/ludoch/samples/java11/micronaut-hello-java11): A Micronaut micro web service fat jar application
* [Spanner](https://github.com/ludoch/samples/java11/spanner): A Jetty9 based Web Application accessing Google Cloud Spanner
* [Bigquery](https://github.com/ludoch/samples/java11/bigquery): A Jetty9 based Web Application accessing Google Cloud BigQuery
* [Sparkjava](https://github.com/ludoch/samples/java11/sparkjava): A SparkJava framework fat jar application
* [Bigtable](https://github.com/ludoch/samples/java11/bigtable): A fat-jar application accessing Google Cloud BigTable
* [Gaeinfo](https://github.com/ludoch/samples/java11/gaeinfo): A Jetty9 based Web Application displaying App Engine data and application environment
* [Springboot](https://github.com/ludoch/samples/java11/springboot): An exploded fat-jar SpringBoot application. (The pom.xml explodes the fat jar as we currently have a 32MB constraint on a given deployed artifact.)


## Organizing your files
Your development file hierarchy should look like this (Maven project):

```
MyDir/
 pom.xml
 [index.yaml]
 [cron.yaml]
 [dispatch.yaml]
 src/main/
   appengine/
     app.yaml
   java/
     com.example.mycode/
       MyCode.java
```
       
## 	app.yaml
An app.yaml file is required. Define a file that looks like this:
runtime: java11
instance_class: F2
	By specifying runtime: java11, the Java11 runtime is automatically selected when you deploy a JAR (*.jar) file (or a collection of jars and resources).
You can find other app.yaml settings in Using app.yaml.
Contrary to the Java8 Standard runtime, you cannot configure the Java11 runtime with an appengine-web.xml.
Note: Your app must either respond to health checks or disable them in your app.yaml configuration. For more information, see lifecycle events.

## Optional files
These configuration files are optional:
* index.yaml
* cron.yaml
* dispatch.yaml
Place these files at the top level of MyDir. If you use any of these files, you must deploy them separately with the gcloud app deploy command.

## Default entry point
The default entry point for the Java11 runtime is automatically calculated when you deploy a single jar myjar.jar as:

```
runtime: java11
instance_class: F2
entrypoint: java -agentpath:/opt/cdbg/cdbg_java_agent.so=--log_dir=/var/log -jar myjar.jar
```

As you can see, the Google Cloud Debugger is automatically configured.
When you deploy more than a single jar file, there is no way we can guess which entry point you want to use, and the deployment process will emit an error. You will have to provide your own entrypoint in the app.yaml. The “java” command is in the PATH, the deployed artifacts are added in the “/srv” directory, which is the current directory of the process started in the entry point, so you do not need to use absolute paths to access your JARs and your deployed resources.

## Auto-update of Minor and Patch versions

App Engine automatically applies security patches to the Ubuntu system image and system libraries. Additionally, App Engine automatically upgrades applications to newer patch releases of the OpenJDK 11 JDK, the Google Cloud Java Debugger (under the directory `/opt/cdbg`), and the Google Cloud Java Profiler (in `/opt/cprof`). 

For example, your application might be deployed at OpenJDK 11.0 and later will be automatically upgraded to OpenJDK11.0.1.  

We will not auto-upgrade to a newer major release. For example, we will not auto-upgrade your application to the OpenJDK12 Runtime.

## Environment variables
The following environment variables are set by the runtime in the container executing the application:

|Environment variable | Description |
| --- | --- |
| `GAE_APPLICATION` | The ID of your App Engine application. |
| `GAE_DEPLOYMENT_ID` | The ID of the current deployment. |
| `GAE_ENV` | The App Engine environment. Set to “standard”. |
| `GAE_INSTANCE` | The ID of the instance on which your service is currently running. |
| `GAE_MEMORY_MB` |The amount of memory available to the application process, in MB. |
| `GAE_RUNTIME` | The runtime specified in your app.yaml file. The value is “java1” for the Java11 runtime. |
| `GAE_SERVICE` |The service name specified in your app.yaml file. If no service name is specified, it is set to default. |
| `GAE_VERSION` | The current version label of your service. |
| `GOOGLE_CLOUD_PROJECT` | The GCP project ID associated with your application. |
| `PATH` | /usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin |
| `PORT` | The port that receives HTTP requests. |
	

You can define additional environment variables in your app.yaml file, but the above values cannot be overridden. You can see all the headers, environment variables, system properties, metadata server content in the Gaeinfo sample deployed for now under https://gaeinfo-dot-openjdk11.appspot.com/ (this may break anytime).

## Filesystem
The runtime includes a full Ubuntu filesystem with the Open JDK 11 installed in the default location. Both Ubuntu and the OpenJDK11 are automatically updated by the App Engine PAAS product, so you do not need to worry about pathing minor releases or apply security fixes in Ubuntu and OpenJDK 11.
The filesystem is read-only except for the location `/tmp`, which is a virtual disk storing data in your App Engine instance's RAM.
Applications can write to the RW `/var/log` directory which content is automatically streamed to the Google StackDriver stack, and all log files content can be seen in the Admin Console log page for the given project.

## Logs

HTTP request logs are sent to Stackdriver Logging under the request_log log.

All `STDOUT` and `STDERR` output is sent to Stackdriver Logging. You will find these logs under stdout and stderr logs.

Additionally, you may wish to send logs to [Cloud Logging](https://cloud.google.com/logging/docs/reference/libraries#client-libraries-install-java) directly.

## Request headers

The Java11 Runtime environment supports the same set of incoming HTTP headers as GAE Standard, including but not limited to the following:

| Header | Example value |
| --- | --- |
| `X-AppEngine-CityLatLong` | 77.322998,-192.032182 |
| `X-AppEngine-City` | Saint Cloud |
| `X-AppEngine-Region` | ca |
| `X-AppEngine-Country` | US |
| `X-AppEngine-Https` | off |
| `X-AppEngine-User-IP` | 2604:306:3429:520:501f:4a71:9d2c:be5f |
| `X-Cloud-Trace-Context` | 18ff88cd7f38ff2bf9b79443... |

You can see all the headers, environment variables, system properties, metadata server content in the [Gaeinfo](https://github.com/ludoch/samples/java11/gaeinfo) sample which is [live under this site](https://gaeinfo-dot-openjdk11.appspot.com/).

## Sockets

You may connect to most outbound TCP/UDP sockets and perform regular socket communications. Unlike the existing GAE Standard Java8 runtime, there is no Urlfetch API. To access other web servers, connect to their port 80 (or 443) as you normally would.

Some ports may not be accessed:

* 22
* 25 / 465
* 10000
* 10001
* 10400 - 10500

## Cloud SQL

You may connect to Cloud SQL using standard JDBC driver. However, the Java11 Runtime environment also provides a fast-path Unix socket technique for accessing Cloud SQL instances with automatic authorization using the GAE service account. For this, you need to use the Google Socket Factory library located at [https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory](https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory)

The JDBC connection URL for PostGreSQL is:

```
jdbc:postgresql://google/<DATABASE_NAME>?useSSL=false&socketFactoryArg=<INSTANCE_CONNECTION_NAME>&socketFactory=com.google.cloud.sql.postgres.SocketFactory&user=<POSTGRESQL_USER_NAME>&password=<POSTGRESQL_USER_PASSWORD>
```
	        
For MySQL, it is:

```
jdbc:mysql://google/<DATABASE_NAME>?cloudSqlInstance=<INSTANCE_CONNECTION_NAME>&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=<MYSQL_USER_NAME>&password=<MYSQL_USER_PASSWORD>&useSSL=false
```

To access Cloud SQL instances, you can connect to Unix sockets as described in the [Cloudsql](https://github.com/ludoch/samples/java11cCloudsql) sample.

## Metadata Server
Each instance of your application can use the App Engine metadata server to query information about the instance and your project.
Note: Custom metadata is not supported in the Java11 runtime standard environment.
You can access the metadata server through the following endpoints:

    http://metadata
    http://metadata.google.internal

The following table lists the endpoints where you can make HTTP requests for specific metadata:

| Metadata Endpoint | Description|
| --- | --- |
| /computeMetadata/v1/project/numeric-project-id | The project number assigned to your project. |
| /computeMetadata/v1/project/project-id | The project ID assigned to your project. |
| /computeMetadata/v1/instance/zone | The zone the instance is running in. |
| /computeMetadata/v1/instance/service-accounts/default/aliases
| /computeMetadata/v1/instance/service-accounts/default/email | The default service account email assigned to your project. |
| /computeMetadata/v1/instance/service-accounts/default/ | Lists all the default service accounts for your project. |
| /computeMetadata/v1/instance/service-accounts/default/scopes | Lists all the supported scopes for the default service accounts. |
| /computeMetadata/v1/instance/service-accounts/default/token | Returns the auth token that can be used to authenticate your application to other Google Cloud APIs. |
	
For example, to retrieve your project ID, send a request to 

    http://metadata.google.internal/computeMetadata/v1/project/project-id.
    
You can see all the headers, environment variables, system properties, metadata server content in the Gaeinfo sample, deployed for now under the [GaeInfo sample](https://gaeinfo-dot-openjdk11.appspot.com/) (this may break anytime).

## HTTPS requests
Use HTTPS requests to access to your App Engine app securely. Depending on how your app is configured, you have the following options:

###appspot.com domains

Simply use the https URL prefix to send HTTPS request to the default service of your GCP project, for example:

      https://[MY_PROJECT_ID].appspot.com
To target specific resources in your App Engine app, you use the -dot- syntax to separate each resource you want to target, for example:

     https://[VERSION_ID]-dot-[SERVICE_ID]-dot-[MY_PROJECT_ID].appspot.com
     
Tip: You convert an HTTP URL to HTTPS by simply replacing the periods between each resource with -dot-, for example:

     http://[SERVICE_ID].[MY_PROJECT_ID].appspot.com
     https://[SERVICE_ID]-dot-[MY_PROJECT_ID].appspot.com
     
For more information about HTTPS URLs and targeting resources, see How Requests are Routed.

### Custom domains
To send HTTPS requests with your custom domain, you can use the managed SSL certificates that are provisioned by App Engine. For more information, see Securing Custom Domains with SSL.

### App handlers
To force HTTPS for your app's handlers, you can specify the secure: always element for each handler in your app.yaml, for example:
handlers:

```
- url: /.*
 script: true
 secure: always
 redirect_http_response_code: 301
```
 
Using secure: always redirects all HTTP traffic to an HTTPS URL with the same path, see the app.yaml configuration reference for more information.
	
## Identity and access management
You can set access control using Identity and Access Management (IAM) roles at the GCP project level. Assign a role to a GCP project member or service account to determine the level of access to your GCP project and its resources. For details see, Access Control.

## App Engine firewall
The App Engine firewall enables you to control access to your App Engine app through a set of rules that can either allow or deny requests from the specified ranges of IP addresses. You are not billed for traffic or bandwidth that is blocked by the firewall. Create a firewall to:

### Allow only traffic from within a specific network
Ensure that only a certain range of IP addresses from specific networks can access your app. For example, create rules to allow only the range of IP addresses from within your company's private network during your app's testing phase. You can then create and modify your firewall rules to control the scope of access throughout your release process, allowing only certain organizations, either within your company or externally, to access your app as it makes its way to public availability.

### Allow only traffic from a specific service
Ensure that all the traffic to your App Engine app is first proxied through a specific service. For example, if you use a third-party Web Application Firewall (WAF) to proxy requests directed at your app, you can create firewall rules to deny all requests except those that are forwarded from your WAF.

### Block abusive IP addresses
While Google Cloud Platform has many mechanisms in place to prevent attacks, you can use the App Engine firewall to block traffic to your app from IP addresses that present malicious intent or shield your app from denial of service attacks and similar forms of abuse. You can blacklist IP addresses or subnetworks, so that requests routed from those addresses and subnetworks are denied before it reaches your App Engine app.

For details about creating rules and configuring your firewall, see Controlling App Access with Firewalls.

## Security scanner
The Google Cloud Security Scanner discovers vulnerabilities by crawling your App Engine app, following all that links within the scope of your starting URLs, and attempting to exercise as many user inputs and event handlers as possible.
In order to use the security scanner, you must be an owner of the GCP project. For more information on assigning roles, see Granting Project Access.
You can run security scans from the Google Cloud Platform Console to identify security vulnerabilities in your App Engine app. For details about running the Security Scanner, see the Security Scanner Quickstart.

## Users API
The Users API is not supported. You can use Cloud IAP to restrict access to your app and Firebase Authentication for user management.

## Datastore/Objectify
Access to Cloud Datastore is via the Google.Cloud Datastore Java APIs.

We encourage Alpha testers to try the latest [Objectify (v6)](https://github.com/objectify/objectify/wiki/UpgradeVersion5ToVersion6) Datastore library that now relies on the Cloud Datastore APIs, as the App Engine Datastore APIs are not available in the Java 11 runtime. Make sure you send your feedback to the mailing list as well as to the Objectify Open Source project.
You can use this artifact:

```
<dependency>
  <groupId>com.googlecode.objectify</groupId>
  <artifactId>objectify</artifactId>
  <version>6.0.2</version>
</dependency>
```

For SpringBoot users, we recommend testing the latest [GCP SpringBoot](https://spring.io/projects/spring-cloud-gcp) integration


## Task Queue API
The App Engine Task Queue is available via the Cloud Tasks API. 


## Blobstore API / Images API
Access to the GAE Blobstore API and GAE Images API is not provided.
We recommend using Google Cloud Storage APIs.


## Mail API
Access to the GAE Mail API is not provided.


## Search API
Access to the GAE Search API is not provided.

## Google Cloud Client Library for Java

- [Google Cloud Platform Documentation](https://cloud.google.com/docs/)
- [Client Java Library Documentation](https://googleapis.github.io/google-cloud-java/google-cloud-clients/apidocs/index.html)

This library supports the following Google Cloud Platform services with clients at a GA quality level:

-  [BigQuery](google-cloud-clients/google-cloud-bigquery) (GA)
-  [Cloud Datastore](google-cloud-clients/google-cloud-datastore) (GA)
-  [Cloud Natural Language](google-cloud-clients/google-cloud-language) (GA)
-  [Cloud Pub/Sub](google-cloud-clients/google-cloud-pubsub) (GA)
-  [Cloud Spanner](google-cloud-clients/google-cloud-spanner) (GA)
-  [Cloud Storage](google-cloud-clients/google-cloud-storage) (GA)
-  [Cloud Translation](google-cloud-clients/google-cloud-translate) (GA)
-  [Cloud Vision](google-cloud-clients/google-cloud-vision) (GA)
-  [Stackdriver Logging](google-cloud-clients/google-cloud-logging) (GA)
-  [Stackdriver Monitoring](google-cloud-clients/google-cloud-monitoring) (GA)

This library supports the following Google Cloud Platform services with clients at a Beta quality level:

-  [BigQuery Data Transfer](google-cloud-clients/google-cloud-bigquerydatatransfer) (Beta)
-  [Cloud Asset](google-cloud-clients/google-cloud-asset) (Beta)
-  [Cloud AutoML](google-cloud-clients/google-cloud-automl) (Beta)
-  [Cloud Container Analysis](google-cloud-clients/google-cloud-containeranalysis) (Beta)
-  [Cloud Data Loss Prevention](google-cloud-clients/google-cloud-dlp) (Beta)
-  [Cloud Firestore](google-cloud-clients/google-cloud-firestore) (Beta)
-  [Cloud IoT Core](google-cloud-clients/google-cloud-iot) (Beta)
-  [Cloud KMS](google-cloud-clients/google-cloud-kms) (Beta)
-  [Cloud Speech](google-cloud-clients/google-cloud-speech) (Beta)
-  [Cloud Text-to-Speech](google-cloud-clients/google-cloud-texttospeech) (Beta)
-  [Cloud Video Intelligence](google-cloud-clients/google-cloud-video-intelligence) (Beta)
-  [Kubernetes Engine](google-cloud-clients/google-cloud-container) (Beta)
-  [Stackdriver Error Reporting](google-cloud-clients/google-cloud-errorreporting) (Beta)
-  [Stackdriver Trace](google-cloud-clients/google-cloud-trace) (Beta)

This library supports the following Google Cloud Platform services with clients at an Alpha quality level:

-  [Cloud Bigtable](google-cloud-clients/google-cloud-bigtable) (Alpha)
-  [Cloud Compute](google-cloud-clients/google-cloud-compute) (Alpha)
-  [Cloud Dataproc](google-cloud-clients/google-cloud-dataproc) (Alpha)
-  [Cloud DNS](google-cloud-clients/google-cloud-dns) (Alpha)
-  [Cloud OS Login](google-cloud-clients/google-cloud-os-login) (Alpha)
-  [Cloud Memorystore for Redis](google-cloud-clients/google-cloud-redis) (Alpha)
-  [Cloud Resource Manager](google-cloud-clients/google-cloud-resourcemanager) (Alpha)
-  [Cloud Security Scanner](google-cloud-clients/google-cloud-websecurityscanner) (Alpha)
-  [Dialogflow](google-cloud-clients/google-cloud-dialogflow) (Alpha)

## SpringBoot Cloud GCP Integration
We encourage Java11 Alpha testers to try the [SpringBoot Cloud GCP](https://spring.io/projects/spring-cloud-gcp) integration, a collaboration between Google and the SpringBoot developers. 
A recent [blog post](https://cloud.google.com/blog/products/application-development/announcing-spring-cloud-gcp-1-1-deepening-ties-pivotals-spring-framework) was announced.
Due to the current 32MB deployment single artifact limitation of the Cloud SDK, you will need to explode the fat jar produced by the build system, as explained in this [SpringBoot](https://github.com/ludoch/samples/tree/master/java11/springboot) sample.
We are aiming at removing this 32MB limitation for our Beta release.

## Local Testing
You can build your app using:

    mvn package
And if this is a fat-jar application type, run it using the java command line:

    java -jar target/myjar.jar
   
Or, follow the instructions for you application framework on how to run the app locally.
When you are testing in your local environment, you might prefer to use emulated Google cloud services, rather than remote Google cloud services. There are emulators for Cloud Datastore, Cloud PubSub, and Cloud Bigtable. Use the gcloud command to start them before you run your app:

    gcloud beta emulators datastore start
    gcloud beta emulators pubsub start
    gcloud beta emulators bigtable start
    
## Deploying your app
After completing the configurations, you can use the Google Cloud SDK to deploy this directory containing the app.yaml file and the JAR using:

    gcloud app deploy app.yaml
If you are using any of the optional configuration files (index.yaml, cron.yaml, and dispatch.yaml) be sure to deploy them separately with the gcloud command. For example:

    gcloud app deploy cron.yaml
Important: If your web framework requires you to specify an IP address, do not set the loopback IP (127.0.0.1 or localhost). This will block your app from receiving requests from outside its container. Use 0.0.0.0 as IP instead.
	
## Maven
Use Maven to deploy your app:

    mvn appengine:deploy
    
## 	Gradle
Use Gradle to deploy your app:

    gradle appengineDeploy

## Known Java11 runtime limitations

* The sandbox still applies certain system level restrictions: the only place where users can write files are the /tmp and /var/log/ directories. Files in /tmp will consume the memory allocated to your instance. 
* WebSocket is not supported.
* The file system where your application code is running on top of the JVM is not specified. You may want to use the existing System properties or System env to access the values known by the JVM, but do not rely on hardcoded file paths in your application.
* F1 instance class does not work yet (but should work by the end of Q1). For now, use F2 or F4.
* Cloud SQL V1 is not supported. Please use Cloud SQL V2.
* There is currently a 32MB limit for the size of deployed artifacts (this includes Fat JAR that may need to be unpacked before a deployment via the Cloud SDK.

## Known Java11 runtime bugs
* You can view all public App Engine issues at:
   * [https://issuetracker.google.com/savedsearches/559750](https://issuetracker.google.com/savedsearches/559750)
* You can file a new  App Engine issue at (make sure you add Java11 in the title to help the triage):
   * [https://issuetracker.google.com/issues/new?component=187191&template=0](https://issuetracker.google.com/issues/new?component=187191&template=0)
  
## Support constraints
* The Java11 Alpha GAE runtime is unsupported software. 
* The GAE Java runtime team will work with Alpha customers to identify and resolve non-working features but such resolutions are not guaranteed to be timely.
* App Engine Free tier, quotas and billing items apply to the Java11 runtime as well. 
* Customer Support response may be delayed or unavailable. Please send any questions to [https://groups.google.com/forum/#!forum/java11-gae-dogfood](https://groups.google.com/forum/#!forum/java11-gae-dogfood). This will reach the Java team on App Engine Standard who would be able to resolve or redirect your questions.
