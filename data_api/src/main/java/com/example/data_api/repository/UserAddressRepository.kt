package com.example.data_api.repository

import com.bunbeauty.common.Logger.USER_ADDRESS_TAG
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.UserAddressRepo
import com.example.data_api.dao.UserAddressDao
import com.example.data_api.handleResult
import com.example.data_api.mapFlow
import com.example.data_api.mapListFlow
import com.example.domain_api.mapper.IUserAddressMapper
import com.example.domain_api.model.server.UserAddressServer
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class UserAddressRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val userAddressDao: UserAddressDao,
    private val userAddressMapper: IUserAddressMapper
) : UserAddressRepo {

    override suspend fun saveUserAddress(userAddress: UserAddress): UserAddress {
        val userAddressWithUuid = userAddress.copy(uuid = UUID.randomUUID().toString())
        val userUuid = userAddressWithUuid.userUuid
        if (userUuid == null) {
            val userAddressEntity = userAddressMapper.toEntityModel(userAddressWithUuid)
            userAddressDao.insert(userAddressEntity)
        } else {
            val userAddressServer = userAddressMapper.toServerModel(userAddressWithUuid, userUuid)
            postUserAddress(userAddressServer)
        }

        return userAddressWithUuid
    }

    override suspend fun assignToUser(userUuid: String) {
        val unassignedUserAddressList = userAddressDao.getUnassignedUserAddressList()
        unassignedUserAddressList.forEach { userAddressEntity ->
            val userAddressServer = userAddressMapper.toServerModel(userAddressEntity, userUuid)
            postUserAddress(userAddressServer)
        }
    }

    suspend fun postUserAddress(userAddress: UserAddressServer) {
        apiRepo.postUserAddress(userAddress).handleResult(USER_ADDRESS_TAG) { postedUserAddress ->
            if (postedUserAddress != null) {
                val userAddressEntity = userAddressMapper.toEntityModel(postedUserAddress)
                userAddressDao.insert(userAddressEntity)
            }
        }
    }

    override fun observeUserAddressByUuid(userAddressUuid: String): Flow<UserAddress?> {
        return userAddressDao.observeUserAddressByUuid(userAddressUuid)
            .mapFlow(userAddressMapper::toModel)
    }

    override fun observeUserAddressListByUserUuid(userUuid: String): Flow<List<UserAddress>> {
        return userAddressDao.observeUserAddressListByUserUuid(userUuid)
            .mapListFlow(userAddressMapper::toModel)
    }

    override fun observeUnassignedUserAddressList(): Flow<List<UserAddress>> {
        return userAddressDao.observeUnassignedUserAddressList()
            .mapListFlow(userAddressMapper::toModel)
    }
}