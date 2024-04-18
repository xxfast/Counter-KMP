package io.github.xxfast.counter.utils

import androidx.compose.runtime.Composable
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class ViewModel<State : Any>(
  val domain: @Composable () -> State
) : CoroutineScope {
  override val coroutineContext: CoroutineContext = Dispatchers.Main

  fun collect(state: (State) -> Unit) {
    launch {
      launchMolecule(RecompositionMode.Immediate) { domain() }
        .collect(state)
    }
  }
}
