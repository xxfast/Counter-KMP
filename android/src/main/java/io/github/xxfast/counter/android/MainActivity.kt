package io.github.xxfast.counter.android

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            LazyVerticalStaggeredGrid(
              columns = StaggeredGridCells.Adaptive(180.dp),
              verticalItemSpacing = 16.dp,
              horizontalArrangement = Arrangement.spacedBy(16.dp),
              contentPadding = PaddingValues(16.dp),
              modifier = Modifier
                .padding(scaffoldPadding)
            ) {
              item { TimerScreen() }
              item { TallyScreen() }
            }
          }
        }
      }
    }
  }
}
