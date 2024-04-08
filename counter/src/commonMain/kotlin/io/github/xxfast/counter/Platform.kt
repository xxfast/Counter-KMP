package io.github.xxfast.counter

interface Platform {
  val name: String
}

expect fun getPlatform(): Platform
