package com.bunbeauty.papakarlo.ui.addresses

import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetAddressesBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog
import com.bunbeauty.papakarlo.view_model.AddressesViewModel


class AddressesBottomSheet : BaseBottomSheetDialog<BottomSheetAddressesBinding, AddressesViewModel>() {

    override var layoutId = R.layout.bottom_sheet_addresses
    override var viewModelVariable = BR.viewModel

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.root
    }

}