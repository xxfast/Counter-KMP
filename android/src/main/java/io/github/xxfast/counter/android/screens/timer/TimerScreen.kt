package io.github.xxfast.counter.android.screens.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
  Card {
    Box(modifier = Modifier.fillMaxWidth()) {
      Text(
        text = "${state.minutes}m ${state.seconds}s",
        style = MaterialTheme.typography.displayMedium,
        modifier = Modifier.padding(16.dp)
      )
    }
  }
}

@Preview
@Composable
fun TimerPreview() {
  TimerView(
    state = TimerState(9, 42),
  )
}
