package com.techmonad.main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.techmonad.es.{CatalogueRepository, ESConfig}
import com.techmonad.rest.api.CatalogueRoutes
import com.techmonad.util.Configuration


object HttpService extends App {

  implicit val system: ActorSystem = ActorSystem()

  implicit val materializer = ActorMaterializer()

  val catalogueRoutes = new CatalogueRoutes with CatalogueRepository with ESConfig

  val restPort = Configuration.config.getInt("rest.port")

  Http().bindAndHandle(catalogueRoutes.routes, "0.0.0.0", restPort)

}
