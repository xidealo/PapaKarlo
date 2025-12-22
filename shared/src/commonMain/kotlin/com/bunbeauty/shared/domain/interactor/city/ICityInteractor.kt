package com.bunbeauty.shared.domain.interactor.city

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.model.city.City
import com.bunbeauty.shared.domain.model.city.SelectableCity

interface ICityInteractor {
    suspend fun getCityList(): List<City>?

    suspend fun checkIsCitySelected(): Boolean

    suspend fun saveSelectedCity(city: City)

    fun observeCityList(): CommonFlow<List<SelectableCity>>
}
