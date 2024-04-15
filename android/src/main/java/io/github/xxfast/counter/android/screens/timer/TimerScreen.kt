package io.github.xxfast.counter.android.screens.timer

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import io.github.xxfast.counter.screens.timer.TimerDomain
import io.github.xxfast.counter.screens.timer.TimerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TimerScreen() {
  val coroutineScope: CoroutineScope = rememberCoroutineScope()

  val stateFlow: StateFlow<TimerState> = remember {
    coroutineScope.launchMolecule(RecompositionMode.ContextClock) { TimerDomain() }
  }

  val state: TimerState by stateFlow.collectAsState()

  TimerView(state = state)
}

@Composable
fun TimerView(state: TimerState) {
  Text(
    text = "${state.minutes}m ${state.seconds}s",
    style = MaterialTheme.typography.displayLarge
  )
}
