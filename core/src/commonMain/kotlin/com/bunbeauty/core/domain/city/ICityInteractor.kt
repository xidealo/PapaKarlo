package com.bunbeauty.core.domain.city

import com.bunbeauty.core.model.city.City
import com.bunbeauty.core.model.city.SelectableCity
import kotlinx.coroutines.flow.Flow

interface ICityInteractor {
    suspend fun getCityList(): List<City>?

    suspend fun checkIsCitySelected(): Boolean

    suspend fun saveSelectedCity(city: City)

    fun observeCityList(): Flow<List<SelectableCity>>
}
