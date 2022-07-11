package com.bunbeauty.shared.domain

import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

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