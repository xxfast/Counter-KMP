package io.github.xxfast.counter.tally

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.xxfast.counter.tally.TallyEvents.Increase
import io.github.xxfast.counter.tally.TallyEvents.Reset
import io.github.xxfast.counter.utils.EventsFlow

data class TallyState(
  val display: String
)

sealed interface TallyEvents {
  data object Increase : TallyEvents
  data class Reset(val turns: Int) : TallyEvents
}

@Composable
fun TallyDomain(events: EventsFlow<TallyEvents> = EventsFlow()): TallyState {
  var digits: List<Int> by remember { mutableStateOf(listOf(0,0,0,0)) }

  LaunchedEffect(Unit) {
    events.collect { event ->
      when(event){
        Increase -> {
          var ones = digits[3]
          var tens = digits[2]
          var hundrands = digits[1]
          var thousands = digits[0]

          ones++
          if (ones > 9) {
            ones = 0
            tens++
          }

          if (tens > 9) {
            tens = 0
            hundrands++
          }

          if (hundrands > 9) {
            hundrands = 0
            thousands++
          }

          if (thousands > 9) {
            thousands = 0
            hundrands = 0
            tens = 0
            ones = 0
          }

          digits = listOf(thousands, hundrands, tens, ones)
        }

        is Reset -> TODO()
      }
    }
  }

  return TallyState(
    display = digits.joinToString("")
  )
}
