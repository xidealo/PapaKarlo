package com.bunbeauty.papakarlo.data.local.db.street

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.papakarlo.data.local.db.BaseDao
import com.bunbeauty.papakarlo.data.model.Street
import kotlinx.coroutines.flow.Flow

@Dao
interface StreetDao : BaseDao<Street> {
    @Query("SELECT * FROM Street")
    fun getStreets(): Flow<List<Street>>
}