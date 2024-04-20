package io.github.xxfast.counter.screens.facts

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.github.xxfast.counter.utils.EventsFlow
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respondError
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

private const val TEST_FACT =
  "42 is the answer to the Ultimate Question of Life, the Universe, and Everything."

class FactsTest {
  private lateinit var mockResponder: MockRequestHandleScope.() -> HttpResponseData
  private val mockEngine: MockEngine = MockEngine { _ -> mockResponder() }
  private val mockClient: HttpClient = HttpClient(mockEngine) { expectSuccess = true }
  private val service = FactsWebService(mockClient)

  @Test
  fun `Test happy path`() = runTest {
    val events: EventsFlow<FactEvents> = EventsFlow()
    mockResponder = { respondOk(TEST_FACT) }

    moleculeFlow(RecompositionMode.Immediate) { FactsDomain(events, service) }
      .test {
        assertEquals(
          expected = FactsState.Loading,
          actual = awaitItem()
        )

        assertEquals(
          expected = FactsState.Success(TEST_FACT),
          actual = awaitItem()
        )
      }
  }

  @Test
  fun `Test sad path`() = runTest {
    val events: EventsFlow<FactEvents> = EventsFlow()
    mockResponder = { respondError(HttpStatusCode.NotFound) }

    moleculeFlow(RecompositionMode.Immediate) { FactsDomain(events, service) }
      .test {
        assertEquals(
          expected = FactsState.Loading,
          actual = awaitItem()
        )

        assertEquals(
          expected = FactsState.Failed,
          actual = awaitItem()
        )
      }
  }

  @Test
  fun `Test lonely path`() = runTest {
    val events: EventsFlow<FactEvents> = EventsFlow()
    mockResponder = { throw IOException("You're in the moon 🌕") }

    moleculeFlow(RecompositionMode.Immediate) { FactsDomain(events, service) }
      .test {
        assertEquals(
          expected = FactsState.Loading,
          actual = awaitItem()
        )

        assertEquals(
          expected = FactsState.Failed,
          actual = awaitItem()
        )
      }
  }
}
