package io.github.xxfast.counter.screens.facts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.xxfast.counter.screens.facts.FactEvents.Refresh
import io.github.xxfast.counter.screens.facts.FactsState.Loading
import io.github.xxfast.counter.screens.facts.FactsState.Success
import io.github.xxfast.counter.utils.EventsFlow
import kotlinx.coroutines.flow.collectLatest
import kotlin.random.Random

sealed class FactsState {
  data object Loading: FactsState()
  data object Failed: FactsState()
  data class Success(val fact: String): FactsState()
}

sealed interface FactEvents {
  data object Refresh: FactEvents
}

@Composable
fun FactsDomain(
  events: EventsFlow<FactEvents>,
  api: FactsApi
): FactsState {
  var state: FactsState by remember { mutableStateOf(Loading) }
  var refrehes: Int by remember { mutableStateOf(0) }

  LaunchedEffect(Unit){
    events.collectLatest { event ->
      when(event){
        Refresh -> refrehes++
      }
    }
  }

  LaunchedEffect(refrehes){
    state = Loading
    val result: Result<String> = api.getRandomFact(Random.nextInt(100))

    if (result.isFailure) {
      state = FactsState.Failed
      return@LaunchedEffect
    }

    state = Success(result.getOrThrow())
  }

  return state
}
