package com.bunbeauty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bunbeauty.data.BaseDao
import com.bunbeauty.data.database.entity.user.SelectedUserAddressUuidEntity
import com.bunbeauty.data.database.entity.user.UserAddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAddressDao : BaseDao<UserAddressEntity> {

    // INSERT

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedUserAddressUuid(selectedUserAddressUuid: SelectedUserAddressUuidEntity)

    // OBSERVE

    @Query(
        "SELECT * FROM UserAddressEntity UA " +
                "JOIN SelectedUserAddressUuidEntity SUA ON UA.uuid == SUA.addressUuid " +
                "WHERE SUA.userUuid = :userUuid AND SUA.cityUuid = :cityUuid "
    )
    fun observeSelectedUserAddressByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<UserAddressEntity?>

    @Query(
        "SELECT * FROM UserAddressEntity " +
                "WHERE userUuid = :userUuid AND street_cityUuid = :cityUuid " +
                "ORDER BY uuid DESC " +
                "LIMIT 1"
    )
    fun observeFirstUserAddressByUserAndCityUuid(userUuid: String, cityUuid: String): Flow<UserAddressEntity?>

    @Query(
        "SELECT * FROM UserAddressEntity " +
                "WHERE userUuid = :userUuid AND street_cityUuid = :cityUuid"
    )
    fun observeUserAddressListByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<List<UserAddressEntity>>
}