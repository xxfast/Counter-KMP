package io.github.xxfast.counter.screens.tally

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.github.xxfast.counter.tally.TallyDomain
import io.github.xxfast.counter.tally.TallyEvents
import io.github.xxfast.counter.tally.TallyEvents.Increase
import io.github.xxfast.counter.tally.TallyState
import io.github.xxfast.counter.utils.EventsFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TallyTest {
  private val events: EventsFlow<TallyEvents> = EventsFlow()

  @Test
  fun `Test increment`() = runTest {
    moleculeFlow(RecompositionMode.Immediate) { TallyDomain(events) }
      .test {
        assertEquals(
          expected = TallyState("0000"),
          actual = awaitItem()
        )

        events.emit(Increase)

        assertEquals(
          expected = TallyState("0001"),
          actual = awaitItem()
        )
      }
  }

  @Test
  fun `Test ones rollover`() = runTest {
    moleculeFlow(RecompositionMode.Immediate) { TallyDomain(events) }
      .onEach { println(it) }
      .test {
        assertEquals(
          expected = TallyState("0000"),
          actual = awaitItem()
        )

        repeat(9) {
          events.emit(Increase)
          awaitItem()
        }

        events.emit(Increase)
        assertEquals(
          expected = TallyState("0010"),
          actual = awaitItem()
        )
      }
  }

  @Test
  fun `Test tens rollover`() = runTest {
    moleculeFlow(RecompositionMode.Immediate) { TallyDomain(events) }
      .onEach { println(it) }
      .test {
        assertEquals(
          expected = TallyState("0000"),
          actual = awaitItem()
        )

        repeat(99) {
          events.emit(Increase)
          awaitItem()
        }

        events.emit(Increase)
        assertEquals(
          expected = TallyState("0100"),
          actual = awaitItem()
        )
      }
  }

  @Test
  fun `Test hundreds rollover`() = runTest {
    moleculeFlow(RecompositionMode.Immediate) { TallyDomain(events) }
      .onEach { println(it) }
      .test {
        assertEquals(
          expected = TallyState("0000"),
          actual = awaitItem()
        )

        repeat(999) {
          events.emit(Increase)
          awaitItem()
        }

        events.emit(Increase)
        assertEquals(
          expected = TallyState("1000"),
          actual = awaitItem()
        )
      }
  }

  @Test
  fun `Test thousands rollover`() = runTest {
    moleculeFlow(RecompositionMode.Immediate) { TallyDomain(events) }
      .onEach { println(it) }
      .test {
        assertEquals(
          expected = TallyState("0000"),
          actual = awaitItem()
        )

        repeat(9999) {
          events.emit(Increase)
          awaitItem()
        }

        events.emit(Increase)
        assertEquals(
          expected = TallyState("0000"),
          actual = awaitItem()
        )
      }
  }
}
