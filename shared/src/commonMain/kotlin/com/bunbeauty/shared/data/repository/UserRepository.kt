package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.companyUuid
import com.bunbeauty.shared.data.dao.order.IOrderDao
import com.bunbeauty.shared.data.dao.order_addition.IOrderAdditionDao
import com.bunbeauty.shared.data.dao.order_product.IOrderProductDao
import com.bunbeauty.shared.data.dao.user.IUserDao
import com.bunbeauty.shared.data.dao.user_address.IUserAddressDao
import com.bunbeauty.shared.data.mapper.order.IOrderMapper
import com.bunbeauty.shared.data.mapper.order_addition.mapOrderAdditionServerToOrderAdditionEntity
import com.bunbeauty.shared.data.mapper.order_product.mapOrderProductServerToOrderProductEntity
import com.bunbeauty.shared.data.mapper.profile.IProfileMapper
import com.bunbeauty.shared.data.mapper.user.IUserMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.login.LoginPostServer
import com.bunbeauty.shared.data.network.model.order.get.OrderProductServer
import com.bunbeauty.shared.data.network.model.profile.get.ProfileServer
import com.bunbeauty.shared.data.network.model.profile.patch.PatchUserServer
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.model.AuthResponse
import com.bunbeauty.shared.domain.model.profile.Profile
import com.bunbeauty.shared.domain.model.profile.User
import com.bunbeauty.shared.domain.repo.UserRepo
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val networkConnector: NetworkConnector,
    private val profileMapper: IProfileMapper,
    private val orderMapper: IOrderMapper,
    private val userMapper: IUserMapper,
    private val userDao: IUserDao,
    private val userAddressDao: IUserAddressDao,
    private val orderDao: IOrderDao,
    private val dataStoreRepo: DataStoreRepo,
    private val orderProductDao: IOrderProductDao,
    private val orderAdditionDao: IOrderAdditionDao,
) : DatabaseCacheRepository(), UserRepo {

    override val tag: String = "USER_TAG"
    var cachedUserUuid: String? = null

    override suspend fun login(userUuid: String, userPhone: String): AuthResponse? {
        val loginPost = LoginPostServer(
            firebaseUuid = userUuid,
            phoneNumber = userPhone,
            companyUuid = companyUuid
        )
        return networkConnector.postLogin(loginPost).getNullableResult { authResponseServer ->
            AuthResponse(
                token = authResponseServer.token,
                userUuid = authResponseServer.userUuid,
            )
        }
    }

    override fun observeUserByUuid(userUuid: String): Flow<User?> {
        return userDao.observeUserByUuid(userUuid).mapFlow(userMapper::toUser)
    }

    override suspend fun getProfileByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String,
        token: String,
    ): Profile.Authorized? {
        val profile = getCacheOrData(
            isCacheValid = {
                cachedUserUuid == userUuid
            },
            onLocalRequest = {
                getProfileLocally(userUuid, cityUuid)
            },
            onApiRequest = {
                networkConnector.getProfile(token)
            },
            onSaveLocally = ::saveProfileLocally,
            serverToDomainModel = profileMapper::toProfile
        )
        cachedUserUuid = profile?.userUuid

        return profile
    }

    override suspend fun clearUserCache() {
        dataStoreRepo.clearUserData()
    }

    override suspend fun disableUser(token: String) {
        networkConnector.patchSettings(token, PatchUserServer(isActive = false))
    }

    private suspend fun saveProfileLocally(profile: ProfileServer?) {
        if (profile != null) {
            dataStoreRepo.saveUserUuid(profile.uuid)
            userDao.insertUser(profileMapper.toUserEntity(profile))
            userAddressDao.insertUserAddressList(profileMapper.toUserAddressEntityList(profile))
            profile.orders.forEach { orderServer ->
                orderDao.insertOrder(
                    orderMapper.toOrderEntity(orderServer)
                )
                orderServer.oderProductList.forEach { orderProductServer ->
                    orderProductDao.insert(
                        orderProductServer.mapOrderProductServerToOrderProductEntity(
                            orderServer.uuid
                        )
                    )
                    insertOrderAdditions(orderProductServer)
                }
            }
        }
    }

    private fun insertOrderAdditions(
        orderProductServer: OrderProductServer,
    ) {
        orderProductServer.additions.map { orderAdditionServer ->
            orderAdditionDao.insert(
                orderAdditionServer.mapOrderAdditionServerToOrderAdditionEntity(
                    orderProductServer.uuid
                )
            )
        }
    }

    suspend fun getProfileLocally(userUuid: String, cityUuid: String): Profile.Authorized? {
        return userDao.getUserByUuid(userUuid)?.let { userEntity ->
            val userAddressCount =
                userAddressDao.getUserAddressCountByUserAndCityUuid(userUuid, cityUuid)
            val lastOrderEntity = orderDao.getLastOrderByUserUuid(userUuid)
            profileMapper.toProfile(
                userUuid = userEntity.uuid,
                userAddressCount = userAddressCount,
                lastOrderEntity = lastOrderEntity
            )
        }
    }

}