package com.bunbeauty.shared.extension

import com.bunbeauty.shared.domain.CommonStateFlow
import com.bunbeauty.shared.domain.asCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
fun <DATA, UI> MutableStateFlow<DATA>.mapToStateFlow(
    scope: CoroutineScope,
    block: (DATA) -> UI,
): CommonStateFlow<UI> =
    mapLatest { data ->
        block(data)
    }.stateIn(
        scope = scope,
        started = SharingStarted.Eagerly,
        initialValue = block(value),
    ).asCommonStateFlow()
