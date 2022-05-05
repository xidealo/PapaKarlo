package com.bunbeauty.domain.interactor.street

import com.bunbeauty.domain.model.Street
import kotlinx.coroutines.flow.Flow

interface IStreetInteractor {

    suspend fun getStreetList(): List<Street>?
}