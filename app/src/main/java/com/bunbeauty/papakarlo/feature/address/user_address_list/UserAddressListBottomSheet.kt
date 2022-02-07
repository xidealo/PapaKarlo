package com.bunbeauty.papakarlo.feature.address.user_address_list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.RESULT_USER_ADDRESS_KEY
import com.bunbeauty.common.Constants.USER_ADDRESS_REQUEST_KEY
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.decorator.MarginItemVerticalDecoration
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.databinding.BottomSheetUserAddressListBinding
import com.bunbeauty.papakarlo.feature.address.AddressAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserAddressListBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_user_address_list) {

    val addressAdapter: AddressAdapter by inject()

    val marginItemVerticalDecoration: MarginItemVerticalDecoration by inject()

    override val viewModel: UserAddressListViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetUserAddressListBinding::bind)

    private val isClickable: Boolean by argument()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            bottomSheetUserAddressesBtnCreateAddress.setOnClickListener {
                viewModel.onCreateAddressClicked()
            }
            viewModel.userAddressList.startedLaunch { userAddressList ->
                addressAdapter.submitList(userAddressList)
            }
            if (isClickable) {
                addressAdapter.setOnItemClickListener { address ->
                    setFragmentResult(
                        USER_ADDRESS_REQUEST_KEY,
                        bundleOf(RESULT_USER_ADDRESS_KEY to address.uuid)
                    )
                    viewModel.goBack()
                }
            }
            bottomSheetUserAddressesRvResult.adapter = addressAdapter
            bottomSheetUserAddressesRvResult.addItemDecoration(marginItemVerticalDecoration)
        }
    }
}