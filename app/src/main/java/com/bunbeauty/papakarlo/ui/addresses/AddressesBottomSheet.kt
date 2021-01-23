package com.bunbeauty.papakarlo.ui.addresses

import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetAddressesBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.adapter.AddressesAdapter
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog
import com.bunbeauty.papakarlo.view_model.AddressesViewModel
import javax.inject.Inject

class AddressesBottomSheet :
    BaseBottomSheetDialog<BottomSheetAddressesBinding, AddressesViewModel>() {

    override var layoutId = R.layout.bottom_sheet_addresses
    override var viewModelVariable = BR.viewModel

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var addressesAdapter: AddressesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.bottomSheetAddressRvResult.adapter = addressesAdapter
        val isDelivery = AddressesBottomSheetArgs.fromBundle(requireArguments()).isDelivery

        viewModel.getAddressesLiveData(isDelivery).observe(viewLifecycleOwner) {
            addressesAdapter.setItemList(it)
        }
    }

}