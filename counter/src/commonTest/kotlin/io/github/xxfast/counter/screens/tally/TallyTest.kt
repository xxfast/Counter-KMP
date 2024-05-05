package io.github.xxfast.counter.screens.tally

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.github.xxfast.counter.screens.tally.TallyDomain
import io.github.xxfast.counter.screens.tally.TallyEvents
import io.github.xxfast.counter.screens.tally.TallyEvents.Increase
import io.github.xxfast.counter.screens.tally.TallyState
import io.github.xxfast.counter.utils.EventsFlow
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
          expected = TallyState(0, 0, 0, 0),
          actual = awaitItem()
        )

        events.emit(Increase)

        assertEquals(
          expected = TallyState(0, 0, 0, 1),
          actual = awaitItem()
        )
      }
  }

  @Test
  fun `Test ones rollover`() = runTest {
    moleculeFlow(RecompositionMode.Immediate) { TallyDomain(events) }
      .test {
        assertEquals(
          expected = TallyState(0, 0, 0, 0),
          actual = awaitItem()
        )

        repeat(9) {
          events.emit(Increase)
          awaitItem()
        }

        events.emit(Increase)
        assertEquals(
          expected = TallyState(0, 0, 1, 0),
          actual = awaitItem()
        )
      }
  }

  @Test
  fun `Test tens rollover`() = runTest {
    moleculeFlow(RecompositionMode.Immediate) { TallyDomain(events) }
      .test {
        assertEquals(
          expected = TallyState(0, 0, 0, 0),
          actual = awaitItem()
        )

        repeat(99) {
          events.emit(Increase)
          awaitItem()
        }

        events.emit(Increase)
        assertEquals(
          expected = TallyState(0, 1, 0, 0),
          actual = awaitItem()
        )
      }
  }

  @Test
  fun `Test hundreds rollover`() = runTest {
    moleculeFlow(RecompositionMode.Immediate) { TallyDomain(events) }
      .test {
        assertEquals(
          expected = TallyState(0, 0, 0, 0),
          actual = awaitItem()
        )

        repeat(999) {
          events.emit(Increase)
          awaitItem()
        }

        events.emit(Increase)
        assertEquals(
          expected = TallyState(1, 0, 0, 0),
          actual = awaitItem()
        )
      }
  }

  @Test
  fun `Test thousands rollover`() = runTest {
    moleculeFlow(RecompositionMode.Immediate) { TallyDomain(events) }
      .test {
        assertEquals(
          expected = TallyState(0, 0, 0, 0),
          actual = awaitItem()
        )

        repeat(9999) {
          events.emit(Increase)
          awaitItem()
        }

        events.emit(Increase)
        assertEquals(
          expected = TallyState(0, 0, 0, 0),
          actual = awaitItem()
        )
      }
  }
}
