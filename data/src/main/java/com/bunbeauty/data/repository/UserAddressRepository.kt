package com.bunbeauty.data.repository

import android.util.Log
import com.bunbeauty.data.dao.UserAddressDao
import com.bunbeauty.domain.mapper.IUserAddressMapper
import com.bunbeauty.domain.model.entity.address.UserAddressEntity
import com.bunbeauty.domain.model.ui.address.UserAddress
import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserAddressRepository @Inject constructor(
    private val userAddressDao: UserAddressDao,
    private val apiRepo: ApiRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressMapper: IUserAddressMapper,
) : UserAddressRepo {

    override suspend fun saveUserAddress(userAddress: UserAddress) {
        val userUuid = userAddress.userUuid
        if (userUuid == null) {
            val userAddressEntity = userAddressMapper.toEntityModel(userAddress)
            userAddressDao.insert(userAddressEntity)
        } else {
            val userAddressFirebase = userAddressMapper.toFirebaseModel(userAddress)
            val firebaseUuid = withContext(IO) {
                apiRepo.postUserAddress(userAddressFirebase, userUuid)
            }
            val userAddressWithUuid = userAddress.copy(uuid = firebaseUuid)
            val userAddressEntity = userAddressMapper.toEntityModel(userAddressWithUuid)
            userAddressDao.insert(userAddressEntity)
        }
    }

    override suspend fun saveUserAddresses(userAddressList: List<UserAddressEntity>) {
        userAddressDao.deleteAll()
        userAddressDao.insertAll(userAddressList)
    }

    override suspend fun assignToUser(userUuid: String) {
        withContext(IO) {
            val savedUserAddressUuid = dataStoreRepo.userAddressUuid.firstOrNull()
            val unassignedUserAddressList = userAddressDao.getUnassignedList()
            unassignedUserAddressList.forEach { userAddressWithStreet ->
                val userAddressFirebase = userAddressMapper.toFirebaseModel(userAddressWithStreet)
                val firebaseUuid = apiRepo.postUserAddress(userAddressFirebase, userUuid)
                val userAddressWithFirebaseUuid =
                    userAddressWithStreet.userAddress.copy(uuid = firebaseUuid)
                userAddressDao.delete(userAddressWithStreet.userAddress)
                userAddressDao.insert(userAddressWithFirebaseUuid)
                if (savedUserAddressUuid == userAddressWithStreet.userAddress.uuid) {
                    dataStoreRepo.saveUserAddressUuid(firebaseUuid)
                }
            }
        }
    }

    override fun observeUserAddressByUuid(uuid: String): Flow<UserAddress?> {
        return userAddressDao.observeByUuid(uuid)
            .flowOn(IO)
            .mapNotNull { userAddressWithStreet ->
                userAddressWithStreet?.let { address ->
                    userAddressMapper.toUIModel(address)
                }
            }.flowOn(Default)
    }

    override fun observeUserAddressListByUserUuid(userUuid: String): Flow<List<UserAddress>> {
        return userAddressDao.observeListByUserUuid(userUuid)
            .flowOn(IO)
            .map { userAddressList ->
                userAddressList.map(userAddressMapper::toUIModel)
            }.flowOn(Default)
    }

    override fun observeUnassignedUserAddressList(): Flow<List<UserAddress>> {
        return userAddressDao.observeUnassignedList()
            .flowOn(IO)
            .map { userAddressList ->
                userAddressList.map(userAddressMapper::toUIModel)
            }.flowOn(Default)
    }
}