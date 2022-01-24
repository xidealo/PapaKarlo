package com.example.domain_firebase.mapper

import com.bunbeauty.domain.model.cafe.Cafe
import com.example.domain_firebase.model.entity.cafe.CafeEntity
import com.example.domain_firebase.model.entity.cafe.CafeWithDistricts
import com.example.domain_firebase.model.firebase.cafe.CafeFirebase

interface ICafeMapper {

    fun toUIModel(cafeEntity: CafeEntity): Cafe
    fun toEntityModel(cafeFirebase: CafeFirebase): CafeWithDistricts
    fun toCafeAddress(cafeEntity: CafeEntity): String
}