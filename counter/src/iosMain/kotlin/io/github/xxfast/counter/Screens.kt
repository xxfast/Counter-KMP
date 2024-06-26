package io.github.xxfast.counter

import io.github.xxfast.counter.screens.facts.FactEvents
import io.github.xxfast.counter.screens.facts.FactEvents.Refresh
import io.github.xxfast.counter.screens.facts.FactsState
import io.github.xxfast.counter.screens.facts.FactsDomain
import io.github.xxfast.counter.screens.facts.FactsWebService
import io.github.xxfast.counter.screens.timer.TimerDomain
import io.github.xxfast.counter.screens.timer.TimerState
import io.github.xxfast.counter.screens.tally.TallyDomain
import io.github.xxfast.counter.screens.tally.TallyEvents
import io.github.xxfast.counter.screens.tally.TallyEvents.Increase
import io.github.xxfast.counter.screens.tally.TallyEvents.Reset
import io.github.xxfast.counter.screens.tally.TallyState
import io.github.xxfast.counter.utils.EventsFlow
import io.github.xxfast.counter.utils.ViewModel
import kotlinx.coroutines.launch

class TimerDomain : ViewModel<TimerState>({ TimerDomain() })

class TallyDomain(
  private val events: EventsFlow<TallyEvents>
) : ViewModel<TallyState>({ TallyDomain(events = events) }) {
  constructor() : this(EventsFlow())
  fun onIncrease() = launch { events.emit(Increase) }
  fun onReset() = launch { events.emit(Reset) }
}

class FactsDomain(
  private val events: EventsFlow<FactEvents>
) : ViewModel<FactsState>({ FactsDomain(events = events, service = FactsWebService()) }) {
  constructor() : this(EventsFlow())
  fun onRefresh() = launch { events.emit(Refresh) }
}
