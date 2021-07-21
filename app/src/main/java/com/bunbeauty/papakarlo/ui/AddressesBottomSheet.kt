package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetAddressesBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.presentation.address.AddressesViewModel
import com.bunbeauty.papakarlo.ui.AddressesBottomSheetArgs.fromBundle
import com.bunbeauty.papakarlo.ui.adapter.AddressesAdapter
import com.bunbeauty.papakarlo.ui.adapter.diff_util.MyDiffCallback
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AddressesBottomSheet : BaseBottomSheetDialog<BottomSheetAddressesBinding>() {

    override val viewModel: AddressesViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var addressesAdapter: AddressesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isDelivery = fromBundle(requireArguments()).isDelivery
        addressesAdapter.onItemClickListener = { address ->
            viewModel.saveSelectedAddress(address.uuid, isDelivery)
        }

        viewModel.addressListState.onEach { state ->
            when (state) {
                is State.Loading -> {
                }
                is State.Success -> {
                    addressesAdapter.setItemList(
                        state.data,
                        MyDiffCallback(state.data, addressesAdapter.itemList)
                    )
                }
                else -> Unit
            }
        }.startedLaunch(viewLifecycleOwner)

        viewModel.getAddresses(isDelivery)

        if (isDelivery) {
            viewDataBinding.bottomSheetAddressTvAddress.text =
                requireContext().resources.getString(R.string.title_bottom_sheet_addresses_your_address)
            viewDataBinding.bottomSheetAddressMcvTitle.setOnClickListener {
                viewModel.onCreateAddressClicked()
            }
            viewDataBinding.bottomSheetAddressIvCreateAddress.visible()

        } else {
            viewDataBinding.bottomSheetAddressTvAddress.text =
                requireContext().resources.getString(R.string.title_bottom_sheet_addresses_cafe_address)
            viewDataBinding.bottomSheetAddressIvCreateAddress.gone()
        }
        viewDataBinding.bottomSheetAddressRvResult.adapter = addressesAdapter
    }
}