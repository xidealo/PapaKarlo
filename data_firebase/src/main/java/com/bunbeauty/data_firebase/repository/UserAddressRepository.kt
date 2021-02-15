package com.bunbeauty.data_firebase.repository

import com.bunbeauty.data_firebase.dao.UserAddressDao
import com.bunbeauty.domain.model.address.CreatedUserAddress
import com.bunbeauty.domain.model.address.UserAddress
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.UserAddressRepo
import com.example.domain_firebase.mapper.IUserAddressMapper
import com.example.domain_firebase.repo.FirebaseRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserAddressRepository @Inject constructor(
    private val userAddressDao: UserAddressDao,
    private val firebaseRepo: FirebaseRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val userAddressMapper: IUserAddressMapper,
) : UserAddressRepo {

    override suspend fun saveUserAddress(
        token: String,
        createdUserAddress: CreatedUserAddress
    ): UserAddress {
        return Any() as UserAddress
//        val userUuid = createdUserAddress.userUuid
//        if (userUuid == null) {
//            val userAddressEntity = userAddressMapper.toEntityModel(createdUserAddress)
//            userAddressDao.insert(userAddressEntity)
//        } else {
//            val userAddressFirebase = userAddressMapper.toFirebaseModel(createdUserAddress)
//            val firebaseUuid = withContext(IO) {
//                firebaseRepo.postUserAddress(userAddressFirebase, userUuid)
//            }
//            val userAddressWithUuid = createdUserAddress.copy(uuid = firebaseUuid)
//            val userAddressEntity = userAddressMapper.toEntityModel(userAddressWithUuid)
//            userAddressDao.insert(userAddressEntity)
//        }
//
//        return createdUserAddress
    }

    override suspend fun saveSelectedUserAddress(userAddressUuid: String) {

    }

    override suspend fun getUserAddressByUuid(userAddressUuid: String): UserAddress? {
        return null
    }

    override suspend fun observeSelectedUserAddress(): Flow<UserAddress?> {
        return flow {  }
    }

    override suspend fun getUserAddressList(): List<UserAddress> {
        return emptyList()
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

    override fun observeUserAddressListByUserUuidAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<List<UserAddress>> {
        return flow { }
    }

    override fun observeUnassignedUserAddressList(): Flow<List<UserAddress>> {
        return userAddressDao.observeUnassignedList()
            .flowOn(IO)
            .map { userAddressList ->
                userAddressList.map(userAddressMapper::toUIModel)
            }.flowOn(Default)
    }
}