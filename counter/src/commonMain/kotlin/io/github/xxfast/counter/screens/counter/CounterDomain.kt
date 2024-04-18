package io.github.xxfast.counter.screens.counter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.xxfast.counter.screens.counter.CounterEvent.Decrease
import io.github.xxfast.counter.screens.counter.CounterEvent.Increase
import io.github.xxfast.counter.utils.EventsFlow

data class CounterState(val count: Int)

sealed interface CounterEvent {
  data object Increase: CounterEvent
  data object Decrease: CounterEvent
}

@Composable
fun CounterDomain(
  events: EventsFlow<CounterEvent> = EventsFlow()
): CounterState {
  var count: Int by remember { mutableStateOf(0) }

  LaunchedEffect(Unit){
    events.collect { event ->
      when(event){
        Decrease -> count--
        Increase -> count++
      }
    }
  }

  return CounterState(count = count)
}
