package com.bunbeauty.data.database.dao

import androidx.room.*
import com.bunbeauty.data.BaseDao
import com.bunbeauty.data.database.entity.user.ProfileEntity
import com.bunbeauty.data.database.entity.user.UserAddressEntity
import com.bunbeauty.data.database.entity.user.UserEmailUpdate
import com.bunbeauty.data.database.entity.user.UserEntity
import com.bunbeauty.data.database.entity.user.order.OrderEntity
import com.bunbeauty.data.database.entity.user.order.OrderProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao : BaseDao<UserEntity> {

    // OBSERVE

    @Query("SELECT * FROM UserEntity WHERE uuid = :uuid")
    abstract fun observeProfileByUuid(uuid: String): Flow<ProfileEntity?>

    @Query("SELECT * FROM UserEntity WHERE uuid = :uuid")
    abstract fun observeUserByUuid(uuid: String): Flow<UserEntity?>

    // GET

    @Query("SELECT * FROM UserEntity WHERE uuid = :uuid")
    abstract suspend fun getUserByUuid(uuid: String): ProfileEntity?

    // INSERT
    @Transaction
    open suspend fun insertProfile(profileEntity: ProfileEntity) {
        insert(profileEntity.user)
        insertUserAddressList(profileEntity.userAddressList)
        profileEntity.orderList.forEach { order ->
            insertOrder(order.order)
            insertOrderProductList(order.orderProductList)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertOrder(order: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract suspend fun insertUserAddressList(userAddressList: List<UserAddressEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract suspend fun insertOrderProductList(userAddressList: List<OrderProductEntity>)

    // UPDATE
    @Update(entity = UserEntity::class)
    abstract suspend fun update(userEmailUpdate: UserEmailUpdate)
}