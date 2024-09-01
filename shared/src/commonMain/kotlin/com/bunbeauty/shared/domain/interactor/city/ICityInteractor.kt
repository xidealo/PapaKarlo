package com.bunbeauty.shared.domain.interactor.city

import com.bunbeauty.shared.domain.model.city.City

interface ICityInteractor {

    suspend fun getCityList(): List<City>?
    suspend fun checkIsCitySelected(): Boolean
    suspend fun saveSelectedCity(city: City)
}
