package com.bunbeauty.data.mapper

import com.bunbeauty.domain.mapper.IStreetMapper
import com.bunbeauty.domain.mapper.ICafeMapper
import com.bunbeauty.domain.model.entity.address.DistrictEntity
import com.bunbeauty.domain.model.entity.cafe.CafeEntity
import com.bunbeauty.domain.model.entity.cafe.CafeWithDistricts
import com.bunbeauty.domain.model.entity.cafe.DistrictWithStreets
import com.bunbeauty.domain.model.ktor.CafeServer
import com.bunbeauty.domain.model.ui.Cafe
import com.bunbeauty.domain.model.ui.address.CafeAddress
import javax.inject.Inject

class CafeMapper @Inject constructor(
    private val streetMapper: IStreetMapper
) : ICafeMapper {

    override fun toUIModel(cafeEntity: CafeEntity): Cafe {
        return Cafe(
            uuid = cafeEntity.uuid,

            fromTime = cafeEntity.fromTime,
            toTime = cafeEntity.toTime,
            phone = cafeEntity.phone,
            cafeAddress = toCafeAddress(cafeEntity),

            latitude = cafeEntity.latitude,
            longitude = cafeEntity.longitude,

            visible = cafeEntity.visible,
        )
    }

  /*  override fun toEntityModel(cafeServer: CafeServer): CafeWithDistricts {
        return CafeWithDistricts(
           *//* cafeEntity = CafeEntity(
                uuid = cafeFirebase.uuid,
                fromTime = cafeFirebase.cafeEntity.fromTime,
                toTime = cafeFirebase.cafeEntity.toTime,
                phone = cafeFirebase.cafeEntity.phone,
                city = cafeFirebase.address.city,
                street = cafeFirebase.address.street.name,
                house = cafeFirebase.address.house,
                comment = cafeFirebase.address.comment,
                latitude = cafeFirebase.cafeEntity.coordinate.latitude,
                longitude = cafeFirebase.cafeEntity.coordinate.longitude,
                visible = cafeFirebase.cafeEntity.visible,
            ),
            districtWithStreetsList = cafeFirebase.districts.map { districtFirebase ->
                DistrictWithStreets(
                    district = DistrictEntity(
                        uuid = districtFirebase.districtEntity.id,
                        name = districtFirebase.districtEntity.name,
                        cafeUuid = cafeFirebase.uuid,
                    ),
                    streets = districtFirebase.streets.map { streetFirebase ->
                        streetMapper.toEntityModel(
                            streetFirebase,
                            districtFirebase.districtEntity.id
                        )
                    }
                )
            }*//*
        )
    }*/

    override fun toCafeAddress(cafeEntity: CafeEntity): CafeAddress {
        return CafeAddress(
            city = cafeEntity.city,
            street = cafeEntity.street,
            house = cafeEntity.house,
            comment = cafeEntity.comment,
            cafeUuid = cafeEntity.uuid,
        )
    }
}