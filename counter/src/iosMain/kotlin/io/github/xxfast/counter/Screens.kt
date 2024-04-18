package io.github.xxfast.counter

import io.github.xxfast.counter.screens.timer.TimerDomain
import io.github.xxfast.counter.screens.timer.TimerState
import io.github.xxfast.counter.tally.TallyDomain
import io.github.xxfast.counter.tally.TallyEvents
import io.github.xxfast.counter.tally.TallyEvents.Increase
import io.github.xxfast.counter.tally.TallyEvents.Reset
import io.github.xxfast.counter.tally.TallyState
import io.github.xxfast.counter.utils.EventsFlow
import io.github.xxfast.counter.utils.ViewModel
import kotlinx.coroutines.launch

class TimerDomain : ViewModel<TimerState>({ TimerDomain() })

class TallyDomain(
  private val events: EventsFlow<TallyEvents>
) : ViewModel<TallyState>({ TallyDomain(events) }) {
  constructor() : this(EventsFlow())
  fun onIncrease() = launch { events.emit(Increase) }
  fun onReset() = launch { events.emit(Reset) }
}
