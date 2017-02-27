package com.techmonad.es

import com.techmonad.json.JsonHelper
import com.techmonad.logger.Logging
import org.elasticsearch.action.bulk.BulkRequestBuilder
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders._


trait ESService extends JsonHelper with Logging {
  this: ESManager =>

  val DOC_ID = "id"
  val DOC_TYPE = "type"

  def ingest(doc: Map[String, Any]) = ingest(List(doc))

  def ingest(docs: List[Map[String, Any]]): Boolean = {
    info("ingesting docs.... " + docs.length)
    val bulkRequest = getBulkRequestBuilder(docs)
    bulkRequest.execute().actionGet().hasFailures
  }

  def searchByQuery(params: Map[String, Any]): Array[String] = {
    val query = getQuery(params)
    info("Search query " + query.toString)
    val response = client.prepareSearch(indexName).setQuery(query).execute().actionGet()
    response.getHits.getHits.map(_.source)
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
        val docId = doc(DOC_ID).toString
        val `type` = doc(DOC_TYPE).toString
        bulkRequest.add(client.prepareIndex().setIndex(indexName).setType(`type`)
          .setId(docId).setSource(write(doc)))
    }
    bulkRequest
  }

}

