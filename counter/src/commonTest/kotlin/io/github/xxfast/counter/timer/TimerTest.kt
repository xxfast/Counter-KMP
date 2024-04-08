package io.github.xxfast.counter.timer

import app.cash.molecule.RecompositionMode
import app.cash.molecule.moleculeFlow
import app.cash.turbine.test
import io.github.xxfast.counter.screens.timer.TimerDomain
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TimerTest {
  @Test
  fun `Test timer stops at zero`() = runTest {
    moleculeFlow(RecompositionMode.Immediate) { TimerDomain(60) }
      .test {
        for(expect in 60 downTo 0){
          val actual: Int = awaitItem()
          assertEquals(expect, actual)
        }
      }
  }
}
