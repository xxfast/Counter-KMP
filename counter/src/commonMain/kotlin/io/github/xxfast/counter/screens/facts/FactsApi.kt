package io.github.xxfast.counter.screens.facts

import io.github.xxfast.counter.utils.client
import io.github.xxfast.counter.utils.get
import io.ktor.client.HttpClient

interface FactsApi {
  suspend fun getRandomFact(number: Int): Result<String>
}

class FactsService(
  private val httpClient: HttpClient = client
): FactsApi {
  override suspend fun getRandomFact(number: Int): Result<String> =
    httpClient.get("http://numbersapi.com/$number")
}
