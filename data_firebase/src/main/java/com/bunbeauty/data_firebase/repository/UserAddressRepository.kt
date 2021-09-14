package com.bunbeauty.data_firebase.repository

import com.bunbeauty.data_firebase.dao.UserAddressDao
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import com.example.domain_firebase.repo.FirebaseRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserAddressRepository @Inject constructor(
    private val userAddressDao: UserAddressDao,
    private val firebaseRepo: FirebaseRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressMapper: com.example.domain_firebase.mapper.IUserAddressMapper,
) : UserAddressRepo {

    override suspend fun saveUserAddress(userAddress: UserAddress): UserAddress {
        val userUuid = userAddress.userUuid
        if (userUuid == null) {
            val userAddressEntity = userAddressMapper.toEntityModel(userAddress)
            userAddressDao.insert(userAddressEntity)
        } else {
            val userAddressFirebase = userAddressMapper.toFirebaseModel(userAddress)
            val firebaseUuid = withContext(IO) {
                firebaseRepo.postUserAddress(userAddressFirebase, userUuid)
            }
            val userAddressWithUuid = userAddress.copy(uuid = firebaseUuid)
            val userAddressEntity = userAddressMapper.toEntityModel(userAddressWithUuid)
            userAddressDao.insert(userAddressEntity)
        }

        return userAddress
    }

    override suspend fun assignToUser(userUuid: String) {
        withContext(IO) {
            val savedUserAddressUuid = dataStoreRepo.userAddressUuid.firstOrNull()
            val unassignedUserAddressList = userAddressDao.getUnassignedList()
            unassignedUserAddressList.forEach { userAddressWithStreet ->
                val userAddressFirebase = userAddressMapper.toFirebaseModel(userAddressWithStreet)
                val firebaseUuid = firebaseRepo.postUserAddress(userAddressFirebase, userUuid)
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

    override fun observeUserAddressByUuid(userAddressUuid: String): Flow<UserAddress?> {
        return userAddressDao.observeByUuid(userAddressUuid)
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