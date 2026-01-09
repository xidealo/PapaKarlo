package com.bunbeauty.shared.domain.interactor.city

import com.bunbeauty.core.model.city.City
import com.bunbeauty.core.model.city.SelectableCity
import com.bunbeauty.shared.domain.CommonFlow

interface ICityInteractor {
    suspend fun getCityList(): List<City>?

    suspend fun checkIsCitySelected(): Boolean

    suspend fun saveSelectedCity(city: City)

    fun observeCityList(): CommonFlow<List<SelectableCity>>
}
