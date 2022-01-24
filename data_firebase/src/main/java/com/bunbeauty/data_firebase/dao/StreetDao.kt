package com.bunbeauty.data_firebase.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.example.domain_firebase.model.entity.address.StreetEntity

@Dao
interface StreetDao : BaseDao<StreetEntity> {

    @Query("SELECT * FROM StreetEntity")
    suspend fun getStreetList(): List<StreetEntity>
}