package com.bunbeauty.papakarlo.data.local.db.cafe

import android.util.Log
import com.bunbeauty.papakarlo.data.api.firebase.IApiRepository
import com.bunbeauty.papakarlo.data.local.db.address.AddressRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CafeRepository @Inject constructor(
    private val apiRepository: IApiRepository,
    private val cafeDao: CafeDao,
    private val addressRepo: AddressRepo,
) : CafeRepo {

    override val cafeEntityListLiveData = cafeDao.getCafeList()

    override suspend fun refreshCafeList() {
        withContext(IO) {
            apiRepository.getCafeList().collect { cafeList ->
                val cafeEntityList = cafeList.map { cafe ->
                    Log.d("test", "cafe " + cafe)
                    val addressId = addressRepo.insert(cafe.address)
                    cafe.cafeEntity.addressId = addressId
                    cafe.cafeEntity
                }
                cafeDao.insertAll(cafeEntityList)
            }
        }
    }
}