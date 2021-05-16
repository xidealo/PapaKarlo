package com.bunbeauty.domain.repository.address

import com.bunbeauty.data.api.IApiRepository
import com.bunbeauty.data.dao.UserAddressDao
import com.bunbeauty.data.mapper.AddressMapper
import com.bunbeauty.data.model.address.UserAddress
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAddressRepository @Inject constructor(
    private val userAddressDao: UserAddressDao,
    private val apiRepository: IApiRepository,
    private val addressMapper: AddressMapper
) : UserAddressRepo {

    override suspend fun insert(token: String, userAddress: UserAddress): UserAddress {
        userAddress.uuid =
            apiRepository.insert(addressMapper.from(userAddress), userAddress.userId ?: "")
        userAddress.id = insert(userAddress)
        return userAddress
    }

    override suspend fun insert(userAddress: UserAddress) = userAddressDao.insert(userAddress)

    override fun getUserAddressByUserId(userId: String): Flow<List<UserAddress>> {
        return userAddressDao.getUserAddressByUserId(userId)
    }
}