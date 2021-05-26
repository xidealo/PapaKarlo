package com.bunbeauty.papakarlo.presentation.cafe

import com.bunbeauty.data.mapper.adapter.CafeAdapterMapper
import com.bunbeauty.domain.model.adapter.CafeAdapterModel
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.CartProductRepo
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.papakarlo.presentation.base.TopbarCartViewModel
import com.bunbeauty.papakarlo.ui.cafe_list.CafeListFragmentDirections.toCafeOptionsBottomSheet
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CafeListViewModel @Inject constructor(
    private val cafeRepo: CafeRepo,
    private val cafeAdapterMapper: CafeAdapterMapper,
    cartProductRepo: CartProductRepo,
    stringUtil: IStringHelper,
    productHelper: IProductHelper,
) : TopbarCartViewModel(cartProductRepo, stringUtil, productHelper) {

    val cafeListFlow by lazy {
        cafeRepo.cafeEntityListFlow.map { cafeList -> cafeList.map { cafeAdapterMapper.from(it) } }
    }

    fun onCafeCardClick(cafeAdapterModel: CafeAdapterModel) {
        router.navigate(toCafeOptionsBottomSheet(cafeAdapterModel))
    }
}