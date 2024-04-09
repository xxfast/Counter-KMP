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
fun TimerDomain(state: TimerState = TimerState()): TimerState {
  var minutes: Int by remember { mutableStateOf(state.minutes) }
  var seconds: Int by remember { mutableStateOf(state.seconds) }

  LaunchedEffect(Unit){
    while (true) {
      delay(1.seconds)
      when {
        seconds > 0 -> seconds--
        minutes > 0 -> {
          minutes--
          seconds = 59
        }
      }
    }
  }

  return TimerState(
    minutes = minutes,
    seconds = seconds,
  )
}
