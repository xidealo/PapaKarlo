package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.extensions.toggleVisibility
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetAddressesBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.adapter.AddressesAdapter
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog
import com.bunbeauty.papakarlo.presentation.AddressesViewModel
import com.bunbeauty.papakarlo.ui.AddressesBottomSheetArgs.fromBundle
import javax.inject.Inject

class AddressesBottomSheet : BaseBottomSheetDialog<BottomSheetAddressesBinding>() {

    override var layoutId = R.layout.bottom_sheet_addresses
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
            viewModel.saveSelectedAddress(address)
        }
        viewModel.isDelivery = isDelivery
        /*viewModel.addressesLiveData.observe(viewLifecycleOwner) {
            addressesAdapter.setItemList(it)
        }*/

        if (isDelivery) {
            viewDataBinding.bottomSheetAddressTvAddress.text =
                requireContext().resources.getString(R.string.title_bottom_sheet_addresses_your_address)
        } else {
            viewDataBinding.bottomSheetAddressTvAddress.text =
                requireContext().resources.getString(R.string.title_bottom_sheet_addresses_cafe_address)
        }

        viewDataBinding.bottomSheetAddressBtnCreateAddress.toggleVisibility(isDelivery)
        viewDataBinding.fragmentCreationOrderIvCreateAddress.toggleVisibility(isDelivery)
        viewDataBinding.bottomSheetAddressRvResult.adapter = addressesAdapter
        viewDataBinding.bottomSheetAddressBtnCreateAddress.setOnClickListener {
            viewModel.createAddressClick()
        }
    }

}