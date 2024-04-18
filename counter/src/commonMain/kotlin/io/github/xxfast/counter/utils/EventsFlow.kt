package io.github.xxfast.counter.utils

import kotlinx.coroutines.flow.MutableSharedFlow

typealias EventsFlow<T> = MutableSharedFlow<T>

private const val EVENT_BUFFER = 5

fun <T> EventsFlow() = MutableSharedFlow<T>(replay = EVENT_BUFFER)
