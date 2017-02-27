package com.techmonad.util

import com.typesafe.config.{Config, ConfigFactory}

/**
  *
  * Load the config once and reuse every where.
  */
object Config {

  val config: Config = ConfigFactory.load()

}
