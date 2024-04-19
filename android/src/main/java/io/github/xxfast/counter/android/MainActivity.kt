package io.github.xxfast.counter.android

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.xxfast.counter.android.screens.facts.FactsScreen
import io.github.xxfast.counter.android.screens.tally.TallyScreen
import io.github.xxfast.counter.android.screens.timer.TimerScreen

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge(SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT))

    setContent {
      MaterialTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          Scaffold(
            topBar = {
              LargeTopAppBar(
                title = { Text(text = "Counter-KMP") }
              )
            }
          ) { scaffoldPadding ->
            LazyColumn(
              verticalArrangement = Arrangement.spacedBy(16.dp),
              contentPadding = PaddingValues(16.dp),
              modifier = Modifier
                .padding(scaffoldPadding)
            ) {
              item { TimerScreen() }
              item { TallyScreen() }
              item { FactsScreen() }
            }
          }
        }
      }
    }
  }
}
