package com.bunbeauty.data_firebase.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.bunbeauty.data.BaseDao
import com.example.domain_firebase.model.entity.address.DistrictEntity
import com.example.domain_firebase.model.entity.address.StreetEntity
import com.example.domain_firebase.model.entity.cafe.CafeEntity
import com.example.domain_firebase.model.entity.cafe.CafeWithDistricts
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CafeDao : BaseDao<CafeEntity> {

    // INSERT

    @Transaction
    open suspend fun refreshCafeList(cafeWithDistrictsList: List<CafeWithDistricts>) {
        deleteAllCafes()
        deleteAllDistricts()
        deleteAllStreets()

        cafeWithDistrictsList.forEach { cafeWithDistricts ->
            insert(cafeWithDistricts.cafeEntity)
            cafeWithDistricts.districtWithStreetsList.forEach { districtWithStreets ->
                insertDistrict(districtWithStreets.district)
                insertAllStreets(districtWithStreets.streets)
            }
        }
    }

    @Insert(onConflict = REPLACE)
    abstract suspend fun insertAllStreets(streetList: List<StreetEntity>)

    @Insert(onConflict = REPLACE)
    abstract suspend fun insertDistrict(district: DistrictEntity)

    // OBSERVE

    @Query("SELECT * FROM CafeEntity")
    abstract fun observeCafeList(): Flow<List<CafeEntity>>

    @Query("SELECT * FROM CafeEntity WHERE uuid = :uuid")
    abstract fun observeCafeByUuid(uuid: String): Flow<CafeEntity>

    // GET

    @Query("SELECT * FROM CafeEntity WHERE uuid = :uuid")
    abstract suspend fun getCafeByUuid(uuid: String): CafeEntity

    @Query("SELECT CafeEntity.* FROM CafeEntity " +
            "JOIN DistrictEntity ON CafeEntity.uuid = DistrictEntity.cafeUuid " +
            "JOIN StreetEntity ON DistrictEntity.uuid = StreetEntity.districtUuid " +
            "WHERE StreetEntity.uuid = :streetUuid")
    abstract suspend fun getCafeByStreetUuid(streetUuid: String): CafeEntity

    // DELETE

    @Query("DELETE FROM CafeEntity")
    abstract suspend fun deleteAllCafes()

    @Query("DELETE FROM DistrictEntity")
    abstract suspend fun deleteAllDistricts()

    @Query("DELETE FROM StreetEntity")
    abstract suspend fun deleteAllStreets()
}