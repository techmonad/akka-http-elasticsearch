package com.techmonad.rest.api


import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.techmonad.es.{CatalogueRepository, ESTestHelper}
import org.scalatest.{Matchers, WordSpec}


class CatalogueRoutesTest extends WordSpec with Matchers with ScalatestRouteTest with CatalogueRoutes with MockedCatalogueRepository {

  "Catalogue service " should {
    "accept and add record to catalogue " in {
      val requestJson = """{"id":4,"type":"book","author":"Rúnar Bjarnason","title":"Functional programming in Scala"}"""
      Post("/catalogue/add", HttpEntity(ContentTypes.`application/json`, requestJson)) ~> routes ~> check {
        responseAs[String] shouldEqual "Record added to catalogue successfully"
      }
    }
    "search by query" in {
      val queryJson = """{"author":"martin"}"""
      Post("/catalogue/search", HttpEntity(ContentTypes.`application/json`, queryJson)) ~> routes ~> check {
        responseAs[String] shouldEqual """[{"id":2,"type":"book","author":"Martin Odersky","title":"Programming in Scala"}]"""
      }
    }


  }
}


trait MockedCatalogueRepository extends CatalogueRepository with ESTestHelper {
  override def ingest(docs: List[Map[String, Any]]): Boolean = true

  override def searchByQuery(params: Map[String, Any]): String =
    """[{"id":2,"type":"book","author":"Martin Odersky","title":"Programming in Scala"}]"""

  override def ingest(doc: Map[String, Any]): Boolean = true
}