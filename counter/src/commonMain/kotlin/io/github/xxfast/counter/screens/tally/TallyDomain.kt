package io.github.xxfast.counter.screens.tally

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.xxfast.counter.screens.tally.TallyEvents.Increase
import io.github.xxfast.counter.screens.tally.TallyEvents.Reset
import io.github.xxfast.counter.utils.EventsFlow

data class TallyState(
  val thousands: Int,
  val hundreds: Int,
  val tens: Int,
  val ones: Int,
)

sealed interface TallyEvents {
  data object Increase : TallyEvents
  data object Reset : TallyEvents
}

@Composable
fun TallyDomain(events: EventsFlow<TallyEvents> = EventsFlow()): TallyState {
  var count: Int by remember { mutableStateOf(0) }

  LaunchedEffect(Unit) {
    events.collect { event ->
      when (event) {
        Increase -> count++
        Reset -> count = 0
      }
    }
  }

  return TallyState(
    thousands = (count % 10000) / 1000,
    hundreds = (count % 1000) / 100,
    tens = (count % 100) / 10,
    ones = (count % 10) / 1,
  )
}
