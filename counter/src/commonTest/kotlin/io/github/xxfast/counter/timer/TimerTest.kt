package io.github.xxfast.counter.timer

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.github.xxfast.counter.screens.timer.TimerDomain
import io.github.xxfast.counter.screens.timer.TimerState
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TimerTest {
  @Test
  fun `Test minute rollover`() = runTest {
    moleculeFlow(RecompositionMode.Immediate) { TimerDomain() }
      .test {
        assertEquals(
          expected = TimerState(1, 0),
          actual = awaitItem()
        )

        assertEquals(
          expected = TimerState(0, 59),
          actual = awaitItem()
        )
      }
  }

  @Test
  fun `Test timer stops at zero`() = runTest {
    moleculeFlow(RecompositionMode.Immediate) { TimerDomain() }
      .test {
        repeat(60) { this.awaitItem() }
        assertEquals(
          expected = TimerState(0, 0),
          actual = awaitItem()
        )
      }
  }
}
