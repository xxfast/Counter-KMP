package io.github.xxfast.counter.screens.counter

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.github.xxfast.counter.screens.counter.CounterEvent.Decrease
import io.github.xxfast.counter.screens.counter.CounterEvent.Increase
import io.github.xxfast.counter.utils.EventsFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CounterTest {
  @Test
  fun `Test count increase`() = runTest {
    val events: EventsFlow<CounterEvent> = EventsFlow()

    moleculeFlow(RecompositionMode.Immediate) { CounterDomain(events) }
      .test {
        assertEquals(CounterState(0), awaitItem())
        events.emit(Increase)
        assertEquals(CounterState(1), awaitItem())
        events.emit(Increase)
        events.emit(Increase)
        assertEquals(CounterState(2), awaitItem())
        assertEquals(CounterState(3), awaitItem())
      }
  }

  @Test
  fun `Test count decrease`() = runTest {
    val events: EventsFlow<CounterEvent> = EventsFlow()

    moleculeFlow(RecompositionMode.Immediate) { CounterDomain(events) }
      .test {
        assertEquals(CounterState(0), awaitItem())
        events.emit(Decrease)
        assertEquals(CounterState(-1), awaitItem())
        events.emit(Decrease)
        events.emit(Decrease)
        assertEquals(CounterState(-2), awaitItem())
        assertEquals(CounterState(-3), awaitItem())
      }
  }
}
