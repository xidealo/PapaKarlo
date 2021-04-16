package com.bunbeauty.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.bunbeauty.data.model.Street
import com.bunbeauty.data.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface StreetDao : BaseDao<Street> {
    @Query("SELECT * FROM Street")
    fun getStreets(): Flow<List<Street>>

    @Transaction
    @Query("DELETE FROM Street")
    fun deleteAll()
}