package com.bunbeauty.shared.domain

import com.squareup.sqldelight.db.Closeable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

inline fun <IM, OM> Flow<List<IM>>.mapListFlow(crossinline block: suspend ((IM) -> OM)): Flow<List<OM>> {
    return map { inputModelList ->
        inputModelList.map { inputModel ->
            block(inputModel)
        }
    }
}

inline fun <IM, OM> Flow<IM?>.mapFlow(crossinline block: suspend ((IM) -> OM)): Flow<OM?> {
    return map { inputModel ->
        inputModel?.let {
            block(inputModel)
        }
    }
}

fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)
fun <T> MutableStateFlow<T>.asCommonStateFlow(): CommonStateFlow<T> = CommonStateFlow(this.asStateFlow())

class CommonFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        val job = Job()

        onEach {
            block(it)
        }.launchIn(CoroutineScope(Dispatchers.Main + job))

        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}

class CommonStateFlow<T>(private val origin: StateFlow<T>) : StateFlow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        val job = Job()

        onEach {
            block(it)
        }.launchIn(CoroutineScope(Dispatchers.Main + job))

        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}