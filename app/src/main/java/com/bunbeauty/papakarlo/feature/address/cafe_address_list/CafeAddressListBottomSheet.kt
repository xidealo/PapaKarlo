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
import com.bunbeauty.papakarlo.feature.address.AddressAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CafeAddressListBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_cafe_address_list) {

    val addressAdapter: AddressAdapter by inject()

    val marginItemVerticalDecoration: MarginItemVerticalDecoration by inject()

    override val viewModel: CafeAddressListViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetCafeAddressListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cafeAddressList.startedLaunch { cafeAddressList ->
            addressAdapter.submitList(cafeAddressList)
        }
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