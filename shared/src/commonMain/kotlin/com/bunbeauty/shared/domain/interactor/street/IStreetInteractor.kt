package com.bunbeauty.shared.domain.interactor.street

import com.bunbeauty.shared.domain.model.street.Street

interface IStreetInteractor {

    suspend fun getStreetList(): List<Street>?
}