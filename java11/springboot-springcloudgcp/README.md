# SpringBoot Google App Engine Java 11 Standard Application


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

Here is the [app.yaml](src/manin/appengine/app.yaml):

```
runtime: java11
instance_class: F4
# The entrypoint here is mandatory as the appengine-staging area contains an exploded
# fatjar, and not the fatjar itself, so we cannot detect what to run from the exploded
# area.
entrypoint: 'java -cp . org.springframework.boot.loader.JarLauncher'
```

This example has a workaround against the current Cloud SDK limitation of 32MB per deployed artifact. Instead of deploying the SpringBoot fat jar, in the [pom.xml](pom.xml) we explode this fat jar in the appengine-staging directory with:

```
      <plugin>
         <groupId>org.apache.maven.plugins</groupId>
         <artifactId>maven-dependency-plugin</artifactId>
         <version>3.1.1</version>
         <executions>
           <execution>
             <id>unpack</id>
             <phase>install</phase>
             <goals>
               <goal>unpack</goal>
             </goals>
             <configuration>
               <artifactItems>
                 <artifactItem>
                   <groupId>com.example.appengine</groupId>
                   <artifactId>springboot-j11</artifactId>
                   <version>0.0.1-SNAPSHOT</version>
                   <overWrite>true</overWrite>
                   <outputDirectory>${project.build.directory}/appengine-staging</outputDirectory>
                   <includes>**/*.class,**/*.xml,**/*.properties,**/*.jar,**/*.MF</includes>
                   <excludes>**/*test.class</excludes>
                 </artifactItem>
               </artifactItems>
               <overWriteReleases>false</overWriteReleases>
               <overWriteSnapshots>true</overWriteSnapshots>
             </configuration>
           </execution>
         </executions>
       </plugin>
```
 so that each item is smaller than 32MB. 
We then add the correct entrypoint to execute the exploded SpringBoot application jar.


