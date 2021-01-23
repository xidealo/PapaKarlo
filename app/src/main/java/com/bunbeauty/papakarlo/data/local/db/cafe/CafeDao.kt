package com.bunbeauty.papakarlo.data.local.db.cafe

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bunbeauty.papakarlo.data.local.db.BaseDao
import com.bunbeauty.papakarlo.data.model.cafe.Cafe
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity

@Dao
interface CafeDao : BaseDao<CafeEntity> {

    @Query("SELECT * FROM CafeEntity")
    fun getCafeList(): LiveData<List<CafeEntity>>
}