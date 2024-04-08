package io.github.xxfast.counter

class Greeting {
  private val platform: Platform = getPlatform()

  fun greet(): String {
    return "Hello, ${platform.name}!"
  }
}
