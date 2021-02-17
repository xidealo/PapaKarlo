package com.bunbeauty.domain.interactor.street

import com.bunbeauty.domain.model.Street
import kotlinx.coroutines.flow.Flow

interface IStreetInteractor {

    fun observeStreetList(): Flow<List<Street>>
    fun observeStreetNameList(): Flow<List<String>>
}