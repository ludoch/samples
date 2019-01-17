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


## Deploy the application
In the directory containing the Main.java and the app.yaml file, just deploy the application with the Cloud SDK command line:

```
gcloud app deploy --project=<yourprojectid>
```

## See the application page
Navigate to http://yourprojectid.appspot.com URL, you ahould see the following page:

```
Hello World from Google App Engine Java 11.
```

## Entire source code: (just 2 files)

Here is the Main.java:

```
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
public class Main {
  public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
    server.createContext("/", (var t) -> {
      byte[] response = "Hello World from Google App Engine Java 11.".getBytes();
      t.sendResponseHeaders(200, response.length);
      try (OutputStream os = t.getResponseBody()) {
        os.write(response);
      }
    });
    server.setExecutor(null);
    server.start();
  }
}
```

Here is the app.yaml:

```
runtime: java11
entrypoint: java Main.java
```

