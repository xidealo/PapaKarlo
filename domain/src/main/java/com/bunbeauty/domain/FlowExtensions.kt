package com.bunbeauty.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <MS, M> Flow<List<MS>>.mapListFlow(crossinline toModel: ((MS) -> M)): Flow<List<M>> {
    return map { userAddressList ->
        userAddressList.map { modelServer ->
            toModel(modelServer)
        }
    }
}

inline fun <MI, MO> Flow<MI?>.mapFlow(crossinline toModel: ((MI) -> MO)): Flow<MO?> {
    return map { modelServer ->
        modelServer?.let {
            toModel(modelServer)
        }
    }
}