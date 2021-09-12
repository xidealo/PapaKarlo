package com.example.data_api.dao

import androidx.room.Dao
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.example.domain_api.model.entity.ProfileEntity

@Dao
interface ProfileDao : BaseDao<ProfileEntity> {

    @Query("SELECT * FROM ProfileEntity WHERE uuid = :uuid")
    fun observeProfileByUuid(uuid: String): ProfileEntity



}