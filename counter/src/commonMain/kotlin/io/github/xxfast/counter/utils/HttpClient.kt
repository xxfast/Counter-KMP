package io.github.xxfast.counter.utils

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders

val client: HttpClient = HttpClient {
  install(Logging) {
    this.logger = object: Logger {
      override fun log(message: String) {
        println(message)
      }
    }

    level = LogLevel.ALL
    sanitizeHeader { header -> header == HttpHeaders.Authorization }
  }

  expectSuccess = true
}

suspend inline fun <reified T> HttpClient.get(
  url: String,
  block: HttpRequestBuilder.() -> Unit = {}
): Result<T> = request {
  get(url) { block() }
}

suspend inline fun <reified T> request(
  requester: () -> HttpResponse
): Result<T> = try {
  val httpResponse: HttpResponse = requester()
  val body: T = httpResponse.body()
  Result.success(body)
} catch (responseException: ResponseException) {
  Result.failure(responseException)
} catch (exception: Throwable) {
  Result.failure(exception)
}
