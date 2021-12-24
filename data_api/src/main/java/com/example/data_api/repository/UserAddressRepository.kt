package com.example.data_api.repository

import com.bunbeauty.common.Logger.USER_ADDRESS_TAG
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.AuthRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import com.example.data_api.dao.UserAddressDao
import com.example.data_api.handleNullableResult
import com.example.data_api.mapFlow
import com.example.data_api.mapListFlow
import com.example.domain_api.mapper.IUserAddressMapper
import com.example.domain_api.model.entity.user.SelectedUserAddressUuidEntity
import com.example.domain_api.model.server.UserAddressPostServer
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class UserAddressRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val authRepo: AuthRepo,
    private val userAddressDao: UserAddressDao,
    private val userAddressMapper: IUserAddressMapper
) : UserAddressRepo {

    override suspend fun saveUserAddress(userAddress: UserAddress): UserAddress {
        val userAddressWithUuid = userAddress.copy(uuid = UUID.randomUUID().toString())
        if (userAddressWithUuid.userUuid == null) {
            val userAddressEntity = userAddressMapper.toEntityModel(userAddressWithUuid)
            userAddressDao.insert(userAddressEntity)
        } else {
            val userAddressServer = userAddressMapper.toPostServerModel(userAddressWithUuid)
            postUserAddress(userAddressServer)
        }

        return userAddressWithUuid
    }

    override suspend fun saveSelectedUserAddress(userAddressUuid: String) {
        val userUuid = authRepo.firebaseUserUuid
        val selectedCityUuid = dataStoreRepo.getSelectedCityUuid()

        if (userUuid != null && selectedCityUuid != null) {
            val selectedUserAddressUuid = SelectedUserAddressUuidEntity(
                userUuid = userUuid,
                cityUuid = selectedCityUuid,
                addressUuid = userAddressUuid
            )
            userAddressDao.insertSelectedUserAddressUuid(selectedUserAddressUuid)
        }
    }

    override suspend fun getUserAddressByUuid(userAddressUuid: String): UserAddress? {
        return userAddressDao.getUserAddressByUuid(userAddressUuid)?.let { userAddressEntity ->
            userAddressMapper.toModel(userAddressEntity)
        }
    }

    override suspend fun getUserAddressList(): List<UserAddress> {
        val userUuid = authRepo.firebaseUserUuid ?: ""
        val selectedCityUuid = dataStoreRepo.getSelectedCityUuid() ?: ""
        return userAddressDao.getUserAddressListByUserAndCityUuid(userUuid, selectedCityUuid)
            .map(userAddressMapper::toModel)
    }

    override suspend fun assignToUser(userUuid: String) {
        val unassignedUserAddressList = userAddressDao.getUnassignedUserAddressList()
        unassignedUserAddressList.forEach { userAddressEntity ->
            val assignedUserAddress = userAddressEntity.copy(userUuid = userUuid)
            val userAddressServer = userAddressMapper.toPostServerModel(assignedUserAddress)
            postUserAddress(userAddressServer)
        }
    }

    suspend fun postUserAddress(userAddress: UserAddressPostServer) {
        apiRepo.postUserAddress(userAddress).handleNullableResult(USER_ADDRESS_TAG) { postedUserAddress ->
            if (postedUserAddress != null) {
                val userAddressEntity = userAddressMapper.toEntityModel(postedUserAddress)
                userAddressDao.insert(userAddressEntity)
            }
        }
    }

    override suspend fun observeSelectedUserAddress(): Flow<UserAddress?> {
        val userUuid = authRepo.firebaseUserUuid ?: ""
        val selectedCityUuid = dataStoreRepo.getSelectedCityUuid() ?: ""

        return userAddressDao.observeSelectedUserAddressByUserAndCityUuid(
            userUuid,
            selectedCityUuid
        ).flatMapLatest { selectedUserAddress ->
            userAddressDao.observeFirstUserAddressByUserAndCityUuid(userUuid, selectedCityUuid)
                .map { firstUserAddress ->
                    (selectedUserAddress ?: firstUserAddress)?.let { userAddress ->
                        userAddressMapper.toModel(userAddress)
                    }
                }
        }
    }

    override fun observeUserAddressByUuid(userAddressUuid: String): Flow<UserAddress?> {
        return userAddressDao.observeUserAddressByUuid(userAddressUuid)
            .mapFlow(userAddressMapper::toModel)
    }

    override fun observeUserAddressList(): Flow<List<UserAddress>> {
        val userUuid = authRepo.firebaseUserUuid ?: ""
        return dataStoreRepo.selectedCityUuid.flatMapLatest { cityUuid ->
            userAddressDao.observeUserAddressListByUserAndCityUuid(userUuid, cityUuid ?: "")
        }.mapListFlow(userAddressMapper::toModel)
    }

    override fun observeUnassignedUserAddressList(): Flow<List<UserAddress>> {
        return userAddressDao.observeUnassignedUserAddressList()
            .mapListFlow(userAddressMapper::toModel)
    }
}