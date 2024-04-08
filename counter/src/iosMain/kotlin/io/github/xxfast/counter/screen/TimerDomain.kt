package io.github.xxfast.counter.screen

import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import io.github.xxfast.counter.screens.timer.TimerDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TimerDomain : CoroutineScope {
  override val coroutineContext: CoroutineContext = Dispatchers.Main

  fun onState(collector: (Int) -> Unit) {
    launch {
      launchMolecule(RecompositionMode.Immediate) { TimerDomain() }
        .collect(collector)
    }
  }
}
