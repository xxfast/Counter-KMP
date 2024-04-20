package io.github.xxfast.counter.android.screens.facts

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import app.cash.molecule.RecompositionMode.ContextClock
import app.cash.molecule.launchMolecule
import io.github.xxfast.counter.screens.facts.FactEvents
import io.github.xxfast.counter.screens.facts.FactEvents.Refresh
import io.github.xxfast.counter.screens.facts.FactsState
import io.github.xxfast.counter.screens.facts.FactsState.Failed
import io.github.xxfast.counter.screens.facts.FactsState.Loading
import io.github.xxfast.counter.screens.facts.FactsState.Success
import io.github.xxfast.counter.screens.facts.FactsDomain
import io.github.xxfast.counter.screens.facts.FactsWebService
import io.github.xxfast.counter.utils.EventsFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun FactsScreen() {
  val coroutineScope: CoroutineScope = rememberCoroutineScope()
  val service: FactsWebService = remember { FactsWebService() }
  val events: EventsFlow<FactEvents> = remember { EventsFlow() }

  val stateFlow: StateFlow<FactsState> = remember {
    coroutineScope.launchMolecule(ContextClock) { FactsDomain(events, service) }
  }

  val state: FactsState by stateFlow.collectAsState()

  FactsView(
    state = state,
    onRefresh = { coroutineScope.launch { events.emit(Refresh) } }
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactsView(
  state: FactsState,
  onRefresh: () -> Unit,
) {
  Card(onClick = onRefresh) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      contentAlignment = Alignment.Center
    ) {
      AnimatedContent(targetState = state, label = "content") {
        when (it) {
          Loading -> CircularProgressIndicator()
          Failed -> Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = null,
            modifier = Modifier
              .size(48.dp)
          )

          is Success -> Text(
            text = it.fact,
            style = MaterialTheme.typography.titleLarge
          )
        }
      }
    }
  }
}

@Preview
@Composable
fun FactsLoadingPreview() {
  FactsView(state = Loading, onRefresh = {})
}

@Preview
@Composable
fun FactsFailedPreview() {
  FactsView(state = Failed, onRefresh = {})
}

@Preview
@Composable
fun FactsSuccessPreview() {
  FactsView(
    state = Success("42 is the answer to the Ultimate Question of Life, the Universe, and Everything."),
    onRefresh = {}
  )
}
