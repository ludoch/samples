<a href="https://console.cloud.google.com/cloudshell/open?git_repo=https://github.com/ludoch/samples&page=editor&open_in_editor=java11/bigquery/README.md">
<img alt="Open in Cloud Shell" src ="http://gstatic.com/cloudssh/images/open-btn.png"></a>

# Google Cloud API Showcase: Cloud BigQuery & StackDriver Monitoring in App Engine Standard Environment for Java 11

This API Showcase demonstrates how to run an AppEngine standard environment application with dependencies on both 
[Google BigQuery][bigquery] and [StackDriver Monitoring][stackdriver].

[bigquery]: https://cloud.google.com/bigquery/docs
[stackdriver]: https://cloud.google.com/monitoring/docs

The home page of this application provides a form to initiate a query of public data, in this case StackOverflow
questions tagged with `google-bigquery`.

The home page also provides a summary view of the metrics that have been logged in the past 30 days.

## Clone the sample app

Copy the sample apps to your local machine, and cd to the `samples/java11/bigquery` directory:

```
git clone https://github.com/ludoch/samples
cd java11/bigquery
```

## Setup

- Make sure [`gcloud`](https://cloud.google.com/sdk/docs/) is installed and initialized:

```
   gcloud init
```

- If you are using the Google Cloud Shell, change the default JDK to Java11:

```
   sudo update-alternatives --config java
   # And select the usr/lib/jvm/java-11-openjdk-amd64/bin/java version.
   # Also, set the JAVA_HOME variable for Maven to pick the correct JDK:
   export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
```

- If this is the first time you are creating an App Engine project
```
   gcloud app create
```
- For local development, [set up][set-up] authentication
- Enable [BigQuery][bigquery-api] and [Monitoring][monitoring-api] APIs
- If you have not already enabled your project for StackDriver, do so by following [these instructions][stackdriver-setup].
  
[set-up]: https://cloud.google.com/docs/authentication/getting-started
[bigquery-api]: https://console.cloud.google.com/launcher/details/google/bigquery-json.googleapis.com
[monitoring-api]: https://console.cloud.google.com/launcher/details/google/monitoring.googleapis.com
[stackdriver-setup]: https://cloud.google.com/monitoring/accounts/tiers#not-enabled


Note: The first time the app is run (or after any metrics definitions have been deleted) it may take up to 5 minutes for
the MetricDescriptors to sync with StackDriver before any results are shown. If you do not see results, please wait a
few moments and try again.

## Deploy

- Deploy to AppEngine standard environment using the following Maven command.

```
   mvn appengine:deploy -Dapp.deploy.projectId=<your-project-id>
```
- Direct your browser to `https://bigquery-dot-<your-project-id>.appspot.com`.
- View more in-depth metrics data on the [StackDriver Monitoring Dashboard][dashboard]

[dashboard]: https://pantheon.corp.google.com/monitoring

