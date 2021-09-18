package com.example.domain_api.mapper

import com.bunbeauty.domain.model.Order
import com.example.domain_api.model.entity.user.OrderEntity
import com.example.domain_api.model.server.OrderServer

interface IOrderMapper {

    fun toEntityModel(order: OrderServer): OrderEntity
    fun toModel(order: OrderEntity): Order
}