package com.bunbeauty.papakarlo.ui.addresses

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetAddressesBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.setViewVisibility
import com.bunbeauty.papakarlo.ui.adapter.AddressesAdapter
import com.bunbeauty.papakarlo.ui.addresses.AddressesBottomSheetArgs.fromBundle
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog
import com.bunbeauty.papakarlo.view_model.AddressesViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class AddressesBottomSheet :
    BaseBottomSheetDialog<BottomSheetAddressesBinding, AddressesViewModel>(), AddressesNavigator {

    override var layoutId = R.layout.bottom_sheet_addresses
    override var viewModelVariable = BR.viewModel

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var addressesAdapter: AddressesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = WeakReference(this)
        
        val isDelivery = fromBundle(requireArguments()).isDelivery
        viewDataBinding.bottomSheetAddressBtnCreateAddress.setViewVisibility(isDelivery)
        viewDataBinding.fragmentCreationOrderIvCreateAddress.setViewVisibility(isDelivery)
        viewModel.isDelivery = isDelivery

        addressesAdapter.addressesViewModel = viewModel
        viewDataBinding.bottomSheetAddressRvResult.adapter = addressesAdapter

        viewModel.addressesLiveData.observe(viewLifecycleOwner) {
            addressesAdapter.setItemList(it)
        }
    }

    override fun goToBack() {
        findNavController().navigateUp()
    }

    override fun goToCreationAddress() {
        findNavController().navigate(AddressesBottomSheetDirections.actionAddressesBottomSheetToCreationAddressFragment())
    }
}