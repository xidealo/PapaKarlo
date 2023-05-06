package com.bunbeauty.shared.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
fun <DATA, UI> MutableStateFlow<DATA>.mapToStateFlow(
    scope: CoroutineScope,
    block: (DATA) -> UI
): StateFlow<UI> = mapLatest { data ->
    block(data)
}.stateIn(
    scope = scope,
    started = SharingStarted.Eagerly,
    initialValue = block(value)
)