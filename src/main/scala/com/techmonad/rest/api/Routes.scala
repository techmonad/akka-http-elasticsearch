package com.techmonad.rest.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.techmonad.es.CatalogueRepository
import com.techmonad.json.JsonHelper

trait Routes extends JsonHelper {
  this: CatalogueRepository =>

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
    } ~
      path("catalogue" / "search") {
        post {
          entity(as[String]) { queryParamsJson =>
            val queryParams = parse(queryParamsJson).extract[Map[String, Any]]
            complete(searchByQuery(queryParams))
          }

        }
      }
  }

}

