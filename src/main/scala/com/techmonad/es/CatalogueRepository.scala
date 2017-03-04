package com.techmonad.es

import com.techmonad.json.JsonHelper
import com.techmonad.logger.Logging
import org.elasticsearch.action.bulk.BulkRequestBuilder
import org.elasticsearch.client.Client
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders._


trait CatalogueRepository extends JsonHelper with Logging {

  val client: Client

  val indexName: String

  def ingest(doc: Map[String, Any]): Boolean = ingest(List(doc))

  def ingest(docs: List[Map[String, Any]]): Boolean = {
    info("ingesting docs.... " + docs.length)
    val bulkRequest = getBulkRequestBuilder(docs)
    !bulkRequest.execute().actionGet().hasFailures
  }

  def searchByQuery(params: Map[String, Any]): String = {
    val query = getQuery(params)
    info("Search query: " + query.toString)
    val response = client.prepareSearch(indexName).setQuery(query).execute().actionGet()
    write(response.getHits.getHits.map(doc => parse(doc.sourceAsString())))
  }

  private def getQuery(params: Map[String, Any]) = {
    val queryBuilder: BoolQueryBuilder = boolQuery()
    params foreach { case (key, value) => queryBuilder.must(termQuery(key, value)) }
    queryBuilder
  }

  private def getBulkRequestBuilder(docs: List[Map[String, Any]]): BulkRequestBuilder = {
    val bulkRequest = client.prepareBulk()
    docs foreach {
      doc =>
        val docId = doc("id").toString
        val `type` = doc("type").toString
        bulkRequest.add(client.prepareIndex().setIndex(indexName).setType(`type`)
          .setId(docId).setSource(write(doc - `type`)))
    }
    bulkRequest
  }


}

