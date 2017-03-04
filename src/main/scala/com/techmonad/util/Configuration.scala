package com.techmonad.util

import com.typesafe.config.{Config, ConfigFactory}

/** *
  * Load once and reuse
  */
object Configuration {

  lazy val config: Config = ConfigFactory.load()
}
