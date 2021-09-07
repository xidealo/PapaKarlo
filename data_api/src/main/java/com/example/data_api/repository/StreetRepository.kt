package com.example.data_api.repository

import com.bunbeauty.domain.model.Street
import com.bunbeauty.domain.repo.StreetRepo
import javax.inject.Inject

class StreetRepository @Inject constructor(): StreetRepo {

    override suspend fun getStreets(): List<Street> {
        //TODO("Not yet implemented")

        return Any() as List<Street>
    }
}