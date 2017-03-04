package com.techmonad.es

import java.io.File

import com.techmonad.logger.Logging
import org.apache.commons.io.FileUtils
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest
import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.node.NodeBuilder


trait ESTestHelper extends Logging {

  val indexName = "catalogue-test-index"

  private val esDataDir = "es-data"

 lazy private val settings = Settings.settingsBuilder()
    .put("cluster.name", "elasticsearch-test")
    .put("discovery.zen.ping.multicast.enabled", false)
    .put("http.enabled", false)
    .put("client", true)
    .put("path.home", esDataDir)
    .put("local", true)
    .put("client.transport.sniff", false)


  lazy val node = NodeBuilder.nodeBuilder().settings(settings).node()

  lazy val client: Client = node.client()


  def init() = {
    val scalaBook ="""{"id":2, "type":"book", "author":"Martin Odersky", "title": "Programming in Scala"}"""
    val akkaBook ="""{"id":3, "type":"book", "author":"Derek Wyatt" , "title": "Akka Concurrency"}"""
    val builder = client.prepareBulk()
    builder.add(client.prepareIndex().setIndex(indexName).setId("2").setType("book").setSource(scalaBook))
    builder.add(client.prepareIndex().setIndex(indexName).setId("3").setType("book").setSource(akkaBook))
    val result = builder.execute().actionGet().hasFailures
    info("inserted record into ES, result -> " + result)
    refresh()
  }

  def refresh() = {
    client.admin().indices().refresh(new RefreshRequest(indexName)).get
  }

  def close() = {
    info("Closing ES node....")
    node.close()
    info("Deleting es data directory....")
    FileUtils.deleteDirectory(new File(esDataDir));
  }
}
