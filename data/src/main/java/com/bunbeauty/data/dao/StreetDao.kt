package com.bunbeauty.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.bunbeauty.domain.model.entity.address.StreetEntity
import com.bunbeauty.domain.model.ui.Street
import kotlinx.coroutines.flow.Flow

@Dao
interface StreetDao : BaseDao<StreetEntity> {

    @Query("SELECT * FROM StreetEntity")
    suspend fun getStreetList(): List<StreetEntity>
}