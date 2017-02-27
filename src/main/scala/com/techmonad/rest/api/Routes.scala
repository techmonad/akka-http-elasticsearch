package com.techmonad.rest.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.techmonad.es.ESService
import com.techmonad.json.JsonHelper

trait Routes extends JsonHelper {
  this: ESService =>

  val routes: Route = {
    path("catalogue" / "add") {
      post {
        entity(as[String]) { json =>
          val record = parse(json).extract[Map[String, Any]]
          val response = if (ingest(record)) {
            "Record added to catalogue successfully"
          } else {
            "Invalid record "
          }
          complete(response)
        }
      }
    }
  }

}

