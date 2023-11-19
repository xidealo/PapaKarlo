package com.bunbeauty.shared.extension

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.bunbeauty.shared.domain.CommonStateFlow
import com.bunbeauty.shared.domain.asCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
fun <DATA, UI> MutableStateFlow<DATA>.mapToStateFlow(
    scope: CoroutineScope,
    block: (DATA) -> UI
): CommonStateFlow<UI> = mapLatest { data ->
    block(data)
}.stateIn(
    scope = scope,
    started = SharingStarted.Eagerly,
    initialValue = block(value)
).asCommonStateFlow()

fun <T : Any> Flow<Query<T>>.mapToOneOrNull(): Flow<T?> {
    return mapToOneOrNull(Dispatchers.IO)
}

fun <T : Any> Flow<Query<T>>.mapToList(): Flow<List<T>> {
    return mapToList(Dispatchers.IO)
}