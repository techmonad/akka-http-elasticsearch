package com.techmonad.es

import org.elasticsearch.client.Client


trait ESConfig {

  val client: Client

  val indexName: String

}
