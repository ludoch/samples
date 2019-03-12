# Vertx Hello World Google App Engine Java 11 Standard Application

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
Navigate to http://yourprojectid.appspot.com URL.

## The application

The application is written to use Eclipse Vert.x to demonstrate the use of [Vert.x Web](https://vertx.io/docs/vertx-web/java/) as web server
and [Vert.x Web client](https://vertx.io/docs/vertx-web-client/java/).

Vert.x is a fully non-blocking toolkit and some parts of the application use callbacks.

The [main](src/main/java/com/google/appengine/vertxhello/Main.java) class creates the Vert.x instance deploys the `Server` class:

```
Vertx vertx = Vertx.vertx();
vertx.deployVerticle(new Server());
```

## The application

When the [application](src/main/java/com/google/appengine/vertxhello/Application.java) starts

- it creates a Vert.x Web client for querying the Google metadata API for the project ID displayed in the response
- it creates a Vert.x Web router when it starts and initializes it to serve all the routes with the `handleDefault` method
- it starts an HTTP server on the port `8080`

```
webClient = WebClient.create(vertx);

Router router = Router.router(vertx);

router.route().handler(this::handleDefault);

vertx.createHttpServer()
    .requestHandler(router)
    .listen(8080, ar -> startFuture.handle(ar.mapEmpty()));
```

HTTP requests are served by the `handleDefault` method. This method uses the `WebClient` to query the Google metadata API
for the project ID and returns an _Hello World_ string that contains the project ID name.

## Testing

The application is tested with a simple JUnit [test](src/test/java/com/google/appengine/vertxhello/ApplicationTest.java).

The test starts two HTTP servers before running the actual test.

1. the server is started on port 8080
2. a mock Google metadata server is started on port 8081

The test uses the `WebClient` to query the HTTP server on port 8080 and check the response before completing the test.