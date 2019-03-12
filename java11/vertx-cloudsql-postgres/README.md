# Vertx Postgres SQL sample for Google App Engine Java11

This sample demonstrates how to use [PostgreSql](https://cloud.google.com/sql/) on Google App Engine standard Java 11
using Eclipse Vert.x and the reactive PostgreSQL client

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
```

## Deploying

```bash
 mvn clean package appengine:deploy -Dapp.deploy.projectId=<your-project-id>
```

## See the application page

Navigate to http://yourprojectid.appspot.com URL.

## The application

The application is written to use Eclipse Vert.x to demonstrate the use of [Vert.x Web](https://vertx.io/docs/vertx-web/java/) as web server
and the [Reactive PostgreSQL client](https://github.com/reactiverse/reactive-pg-client) to access a PostgreSQL instance managed by Google Cloud SQL.

Vert.x is a fully non-blocking toolkit and some parts of the application use callbacks.

The PostgreSQL client is configured to connect to the instance using unix domain sockets configured with the `app.yaml` file.

The [main](src/main/java/com/google/appengine/vertxcloudsqlpostgres/Main.java) class creates the Vert.x instance deploys the `Application` class:

```
Vertx vertx = Vertx.vertx();
vertx.deployVerticle(new Application());
```

When the [application](src/main/java/com/google/appengine/vertxcloudsqlpostgres/Application.java) starts

- it creates a Reactive PostgreSQL Client for querying the Cloud SQL instance
- it creates a Vert.x Web router when it starts and initializes it to serve all the routes with the `handleDefault` method
- it starts an HTTP server on the port `8080`

```
PgPoolOptions options = new PgPoolOptions()
    .setUser(DB_USER)
    .setPassword(DB_PASSWORD)
    .setDatabase(DB_NAME)
    .setPort(DB_PORT)
    .setHost(DB_UNIX_DOMAIN_SOCKET);

pgClient = PgClient.pool(vertx, options);

Router router = Router.router(vertx);

router.route().handler(this::handleDefault);

vertx.createHttpServer()
    .requestHandler(router)
    .listen(8080, ar -> startFuture.handle(ar.mapEmpty()));
```

HTTP requests are served by the `handleDefault` method. This method uses the `PgClient` to query the PostgreSQL instance
and return a list of all the tables found in the database.

## Cleaning up

* [Delete your Instance](https://cloud.google.com/sql/docs/postgres/delete-instance)
