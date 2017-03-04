package com.techmonad.es

import org.scalatest.{BeforeAndAfterAll, WordSpec}


class CatalogueRepositoryTest extends WordSpec with CatalogueRepository with ESTestHelper with BeforeAndAfterAll{


  "Catalogue Repository " should {

    "add record to book catalogue" in {
      val result = ingest(Map("id" -> 1, "type" -> "book", "author" -> "Chetan bhagat", "title" -> "The 3 Mistakes of My Life"))
      assert(result)
    }

    "search by query " in {
      val query = Map("author" -> "martin")
      val result = searchByQuery(query)
      assert(result === """[{"id":2,"type":"book","author":"Martin Odersky","title":"Programming in Scala"}]""")
    }

  }

  override  def beforeAll(): Unit = {
    init()
  }


  override  def afterAll(): Unit = {
    close()
  }

}
