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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TimerScreen() {
  val coroutineScope: CoroutineScope = rememberCoroutineScope()

  val stateFlow: StateFlow<Int> = remember {
    coroutineScope.launchMolecule(RecompositionMode.ContextClock) { TimerDomain() }
  }

  val seconds: Int by stateFlow.collectAsState()

  TimerView(seconds = seconds)
}

@Composable
fun TimerView(seconds: Int) {
  Text(
    text = "${seconds}s",
    style = MaterialTheme.typography.displayLarge
  )
}
