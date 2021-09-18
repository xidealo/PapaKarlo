package com.bunbeauty.data_firebase.mapper

import com.bunbeauty.domain.model.Cafe
import com.example.domain_firebase.mapper.ICafeMapper
import com.example.domain_firebase.mapper.IStreetMapper
import com.example.domain_firebase.model.entity.address.DistrictEntity
import com.example.domain_firebase.model.entity.cafe.CafeEntity
import com.example.domain_firebase.model.entity.cafe.CafeWithDistricts
import com.example.domain_firebase.model.entity.cafe.DistrictWithStreets
import com.example.domain_firebase.model.firebase.cafe.CafeFirebase
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
            address = toCafeAddress(cafeEntity),

            latitude = cafeEntity.latitude,
            longitude = cafeEntity.longitude,

            cityUuid = cafeEntity.city
        )
    }

    override fun toEntityModel(cafeFirebase: CafeFirebase): CafeWithDistricts {
        return CafeWithDistricts(
            cafeEntity = CafeEntity(
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
            }
        )
    }

    override fun toCafeAddress(cafeEntity: CafeEntity): String {
        return "${cafeEntity.city} ${cafeEntity.street} ${cafeEntity.house} ${cafeEntity.comment} ${cafeEntity.uuid}"
    }
}