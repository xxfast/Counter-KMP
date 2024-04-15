package io.github.xxfast.counter.screens.timer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

data class TimerState(
  val minutes: Int = 1,
  val seconds: Int = 0,
)

@Composable
fun TimerDomain(seed: TimerState = TimerState()): TimerState {
  var state: TimerState by remember { mutableStateOf(seed) }

  LaunchedEffect(Unit) {
    while (true) {
      delay(1.seconds)

      state = when {
        state.minutes > 0 -> state.copy(
          minutes = state.minutes - 1,
          seconds = 59
        )

        state.seconds > 0 -> state.copy(
          seconds = state.seconds - 1
        )

        else -> break
      }
    }
  }

  return state
}
