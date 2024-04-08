package io.github.xxfast.counter.screens.timer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun TimerDomain(time: Int = 60): Int {
  var seconds: Int by remember { mutableStateOf(time) }

  LaunchedEffect(Unit){
    while (true) {
      delay(1.seconds)
      if (seconds > 0) seconds--
    }
  }

  return seconds
}
