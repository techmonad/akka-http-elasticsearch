package com.techmonad.es

import java.net.InetAddress

import com.techmonad.util.Configuration
import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress

import scala.collection.JavaConversions._

trait ESConfig {

  private val esConfig = Configuration.config.getConfig("es")
  private val nodes = esConfig.getStringList("nodes")
  private val port = esConfig.getInt("port")
  private val hosts = nodes.map { host => new InetSocketTransportAddress(InetAddress.getByName(host), port) }
  private val settings = Settings.settingsBuilder().build()

  lazy val client: Client = TransportClient.builder().settings(settings).build().addTransportAddresses(hosts: _*)

  val indexName: String = esConfig.getString("catalogue.index")

}

