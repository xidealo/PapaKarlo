package com.example.domain_api.repo

import com.example.domain_api.model.server.CafeServer
import com.example.domain_api.model.server.DeliveryServer
import com.example.domain_api.model.server.MenuProductServer

interface ApiRepo {

    suspend fun getCafeServerList(): List<CafeServer>
    suspend fun getCafeServerByCityList(city: String): List<CafeServer>
    suspend fun getMenuProductList(): List<MenuProductServer>
    suspend fun getDelivery(): DeliveryServer
}