package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.companyUuid
import com.bunbeauty.shared.data.dao.order.IOrderDao
import com.bunbeauty.shared.data.dao.user.IUserDao
import com.bunbeauty.shared.data.dao.user_address.IUserAddressDao
import com.bunbeauty.shared.data.mapper.order.IOrderMapper
import com.bunbeauty.shared.data.mapper.profile.IProfileMapper
import com.bunbeauty.shared.data.mapper.user.IUserMapper
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.network.model.login.LoginPostServer
import com.bunbeauty.shared.data.network.model.profile.get.ProfileServer
import com.bunbeauty.shared.domain.mapFlow
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
    private val dataStoreRepo: DataStoreRepo
) : DatabaseCacheRepository(), UserRepo {

    override val tag: String = "USER_TAG"
    var cachedUserUuid: String? = null

    override suspend fun login(userUuid: String, userPhone: String): String? {
        val loginPost = LoginPostServer(
            firebaseUuid = userUuid,
            phoneNumber = userPhone,
            companyUuid = companyUuid
        )
        return networkConnector.postLogin(loginPost).getNullableResult { authResponseServer ->
            authResponseServer.token
        }
    }

    override fun observeUserByUuid(userUuid: String): Flow<User?> {
        return userDao.observeUserByUuid(userUuid).mapFlow(userMapper::toUser)
    }

    override suspend fun getProfileByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String,
        token: String
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

    override suspend fun updateUserEmail(token: String, userUuid: String, email: String): User? {
        val patchUserServer = userMapper.toPatchServerModel(email)
        return networkConnector.patchProfileEmail(token, userUuid, patchUserServer)
            .getNullableResult { profile ->
                userDao.updateUserEmailByUuid(userUuid, email)
                userMapper.toUser(profile)
            }
    }

    override suspend fun clearUserCache() {
        dataStoreRepo.clearToken()
        dataStoreRepo.clearUserUuid()
    }

    suspend fun saveProfileLocally(profile: ProfileServer?) {
        if (profile != null) {
            dataStoreRepo.saveUserUuid(profile.uuid)
            userDao.insertUser(profileMapper.toUserEntity(profile))
            userAddressDao.insertUserAddressList(profileMapper.toUserAddressEntityList(profile))
            profile.orders.flatMap { orderServer ->
                orderServer.oderProductList.map { orderProductServer ->
                    orderMapper.toOrderWithProductEntity(orderServer, orderProductServer)
                }
            }.let { orderWithProductEntityList ->
                orderDao.insertOrderWithProductList(orderWithProductEntityList)
            }
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