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

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {

  public static void main(String[] args) throws IOException {
    var server = HttpServer.create(new InetSocketAddress(8080), 0);
    server.createContext("/", t -> {
      var response = "Hello World from Google App Engine Java 11.".getBytes();
      t.sendResponseHeaders(200, response.length);
      try (var os = t.getResponseBody()) {
        os.write(response);
      }
    });
    server.setExecutor(null);
    server.start();
  }
}

