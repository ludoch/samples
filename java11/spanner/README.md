# Google Cloud Spanner Sample

<a href="https://console.cloud.google.com/cloudshell/open?git_repo=https://github.com/ludoch/samples&page=editor&open_in_editor=samples/java11/spanner/README.md">
<img alt="Open in Cloud Shell" src ="http://gstatic.com/cloudssh/images/open-btn.png"></a>

This sample demonstrates how to use [Google Cloud Spanner][spanner-docs]
from [Google App Engine standard Java11 environment][appengine-docs].

[spanner-docs]: https://cloud.google.com/spanner/docs/
[appengine-docs]: https://cloud.google.com/appengine/docs/java/


## Setup
- Install the [Google Cloud SDK](https://cloud.google.com/sdk/) and run:
```
   gcloud init
```

If you are using the Google Cloud Shell, change the default JDK to Java11:
```
   sudo update-alternatives --config java
   # And select the /usr/lib/jvm/zulu-11-amd64/bin/java version.
```

If this is your first time creating an App engine application:
```
   gcloud app create
```
- [Create a Spanner instance](https://cloud.google.com/spanner/docs/quickstart-console#create_an_instance).

- Update `SPANNER_INSTANCE` value in `[app.yaml](src/main/appengine/app.yaml).

## Endpoints
- `/spanner` : will run sample operations against the spanner instance in order. Individual tasks can be run
using the `task` query parameter. See [SpannerTasks](src/main/java/com/example/appengine/spanner/SpannerTasks.java)
for supported set of tasks.
Note : by default all the spanner example operations run in order, this operation may take a while to return.

## Deploying

    $ mvn clean appengine:deploy

To see the results of the deployed sample application, open
`https://spanner-dot-PROJECTID.appspot.com/spanner` in a web browser.
Note : by default all the spanner example operations run in order, this operation may take a while to show results.

