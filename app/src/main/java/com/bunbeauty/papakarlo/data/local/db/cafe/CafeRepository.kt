package com.bunbeauty.papakarlo.data.local.db.cafe

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.api.firebase.IApiRepository
import com.bunbeauty.papakarlo.data.local.db.address.AddressRepo
import com.bunbeauty.papakarlo.data.model.cafe.Cafe
import kotlinx.coroutines.Dispatchers.IO
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
                for (cafe in cafeList) {
                    val cafeEntity = cafeDao.getCafeEntityById(cafe.cafeEntity.id)
                    if (cafeEntity == null) {
                        val addressId = addressRepo.insert(cafe.address)
                        cafe.cafeEntity.addressId = addressId
                        cafeDao.insert(cafe.cafeEntity)
                    } else {
                        cafe.address.id = cafeEntity.addressId!!
                        addressRepo.update(cafe.address)
                        cafe.cafeEntity.id = cafeEntity.id
                        cafe.cafeEntity.addressId = cafeEntity.addressId!!
                        cafeDao.update(cafe.cafeEntity)
                    }
                }
            }
        }
    }

    override fun getCafeById(cafeId: String): LiveData<Cafe> {
        return cafeDao.getCafeById(cafeId)
    }
}