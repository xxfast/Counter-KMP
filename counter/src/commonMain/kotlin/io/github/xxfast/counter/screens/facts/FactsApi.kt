package io.github.xxfast.counter.screens.facts

import io.github.xxfast.counter.utils.client
import io.github.xxfast.counter.utils.get
import io.ktor.client.HttpClient

class FactsWebService(private val httpClient: HttpClient = client) {
  suspend fun getRandomFact(number: Int): Result<String> =
    httpClient.get("http://numbersapi.com/$number")
}
