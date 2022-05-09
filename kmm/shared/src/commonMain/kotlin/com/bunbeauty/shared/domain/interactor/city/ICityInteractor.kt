package com.bunbeauty.shared.domain.interactor.city

import com.bunbeauty.shared.domain.model.City
import kotlinx.coroutines.flow.Flow

interface ICityInteractor {

    suspend fun getCityList(): List<City>?
    suspend fun checkIsCitySelected(): Boolean
    suspend fun saveSelectedCity(city: City)
    fun observeCityList(): Flow<List<City>>
    fun observeSelectedCity(): Flow<City?>
}