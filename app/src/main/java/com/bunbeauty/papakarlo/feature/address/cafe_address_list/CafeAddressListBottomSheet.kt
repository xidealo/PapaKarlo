package com.bunbeauty.papakarlo.feature.address.cafe_address_list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.CAFE_ADDRESS_REQUEST_KEY
import com.bunbeauty.common.Constants.RESULT_CAFE_ADDRESS_KEY
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.decorator.MarginItemVerticalDecoration
import com.bunbeauty.papakarlo.databinding.BottomSheetCafeAddressListBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.feature.address.AddressAdapter
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CafeAddressListBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_cafe_address_list) {

    @Inject
    lateinit var addressAdapter: AddressAdapter

    @Inject
    lateinit var marginItemVerticalDecoration: MarginItemVerticalDecoration

    override val viewModel: CafeAddressListViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(BottomSheetCafeAddressListBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cafeAddressList.onEach { cafeAddressList ->
            addressAdapter.submitList(cafeAddressList)
        }.startedLaunch()
        addressAdapter.setOnItemClickListener { address ->
            setFragmentResult(
                CAFE_ADDRESS_REQUEST_KEY,
                bundleOf(RESULT_CAFE_ADDRESS_KEY to address.uuid)
            )
            viewModel.goBack()
        }
        viewBinding.bottomSheetCafeAddressesRvResult.adapter = addressAdapter
        viewBinding.bottomSheetCafeAddressesRvResult.addItemDecoration(
            marginItemVerticalDecoration
        )
    }
}