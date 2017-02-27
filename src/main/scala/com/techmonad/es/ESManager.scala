package com.techmonad.es

import org.elasticsearch.client.Client


trait ESManager {

  val client: Client

  val indexName: String

}
