package io.github.xxfast.counter.android.screens.tally

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import io.github.xxfast.counter.tally.TallyDomain
import io.github.xxfast.counter.tally.TallyEvents
import io.github.xxfast.counter.tally.TallyEvents.Increase
import io.github.xxfast.counter.tally.TallyEvents.Reset
import io.github.xxfast.counter.tally.TallyState
import io.github.xxfast.counter.utils.EventsFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun TallyScreen() {
  val coroutineScope: CoroutineScope = rememberCoroutineScope()
  val events: EventsFlow<TallyEvents> = remember { EventsFlow() }

  val stateFlow: StateFlow<TallyState> = remember {
    coroutineScope.launchMolecule(RecompositionMode.ContextClock) { TallyDomain(events) }
  }

  val state: TallyState by stateFlow.collectAsState()

  TallyView(
    state = state,
    onIncrease = { coroutineScope.launch { events.emit(Increase) } },
    onReset = { coroutineScope.launch { events.emit(Reset) } },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TallyView(
  state: TallyState,
  onIncrease: () -> Unit,
  onReset: () -> Unit,
) {
  val numbers: List<Int> = remember(state) {
    with(state) { listOf(thousands, hundreds, tens, ones) }
  }

  Card(onClick = onIncrease) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
      ProvideTextStyle(MaterialTheme.typography.displayMedium) {
        Row {
          numbers.forEach { number ->
            AnimatedContent(
              targetState = number,
              transitionSpec = {
                slideInVertically { -it } + fadeIn() + scaleIn() togetherWith
                    slideOutVertically { it } + fadeOut() + scaleOut()
              },
              label = "slide"
            ) {
              Text(text = "$it")
            }
          }
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onReset) {
          Icon(Icons.Rounded.Refresh, null)
        }
      }
    }
  }
}

@Preview
@Composable
fun TallyPreview() {
  TallyView(
    state = TallyState(3, 4, 3, 5),
    onIncrease = {},
    onReset = {}
  )
}
