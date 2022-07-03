package com.bunbeauty.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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