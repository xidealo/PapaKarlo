package com.bunbeauty.shared.domain.interactor.street

import com.bunbeauty.shared.domain.model.Street

interface IStreetInteractor {
    suspend fun getStreetList(): List<Street>?
}