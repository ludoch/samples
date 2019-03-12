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
package com.google.appengine.vertxcloudsqlpostgres;

import io.reactiverse.pgclient.PgClient;
import io.reactiverse.pgclient.PgPoolOptions;
import io.reactiverse.pgclient.PgRowSet;
import io.reactiverse.pgclient.Row;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class Application extends AbstractVerticle {

  private static final String CLOUD_SQL_INSTANCE_NAME = System.getenv("DB_INSTANCE");
  private static final String DB_USER = System.getenv("DB_USER");
  private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
  private static final String DB_NAME = System.getenv("DB_DATABASE");
  private static final String DB_UNIX_DOMAIN_SOCKET = "/cloudsql/" + CLOUD_SQL_INSTANCE_NAME;
  private static final int DB_PORT = 5432;

  private PgClient pgClient;

  @Override
  public void start(Future<Void> startFuture) {

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
  }

  private void handleDefault(RoutingContext routingContext) {
    pgClient.query("select * from information_schema.tables", res -> {
      if (res.succeeded()) {
        PgRowSet rows = res.result();
        StringBuilder result = new StringBuilder("List of all tables in your Cloud SQL:");
        for (Row row : rows) {
          result
              .append("\r\n")
              .append(row.getString("table_schema"))
              .append(".")
              .append(row.getString("table_name"));
        }
        routingContext.response()
            .putHeader("content-type", "text/plain")
            .end(result.toString());
      } else {
        routingContext.fail(res.cause());
      }
    });
  }
}