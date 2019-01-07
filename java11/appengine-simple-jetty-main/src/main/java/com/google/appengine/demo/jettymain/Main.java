/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.appengine.demo.jettymain;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.Configuration.ClassList;

/**
 * Simple Jetty Main that can execute a WAR file (passed as an argument.
 */
public class Main {

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println("Usage: need a relative path to the war file to execute");
      System.exit(1);
    }
    System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StrErrLog");
    System.setProperty("org.eclipse.jetty.LEVEL", "INFO");
    // Create a basic Jetty server object that will listen on port 8080.  Note that if you set this to port 0
    // then a randomly available port will be assigned that you can either look in the logs for the port,
    // or programmatically obtain it for use in test cases.
    Server server = new Server(8080);
    // The WebAppContext is the entity that controls the environment in
    // which a web application lives and breathes. In this example the
    // context path is being set to "/" so it is suitable for serving
    // root context requests and then we see it setting the location of
    // the war. A whole host of other configurations are available,
    // ranging from configuring to support annotation scanning in the
    // webapp (through PlusConfiguration) to choosing where the webapp
    // will unpack itself.
    WebAppContext webapp = new WebAppContext();
    webapp.setContextPath("/");
    webapp.setWar(args[0]);
    ClassList classlist = ClassList.setServerDefault(server);

    // Enable Annotation Scanning
    classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
            "org.eclipse.jetty.annotations.AnnotationConfiguration");

    // A WebAppContext is a ContextHandler as well so it needs to be set to
    // the server so it is aware of where to send the appropriate requests.
    server.setHandler(webapp);

    // Start things up! By using the server.join() the server thread will join with the current thread.
    // See "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()" for more details.
    server.start();
    server.join();
  }
}
