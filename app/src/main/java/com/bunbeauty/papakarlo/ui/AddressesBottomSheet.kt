package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetAddressesBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.address.AddressesViewModel
import com.bunbeauty.papakarlo.ui.adapter.AddressAdapter
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheet
import com.bunbeauty.papakarlo.ui.custom.MarginItemDecoration
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AddressesBottomSheet : BaseBottomSheet<BottomSheetAddressesBinding>() {

    @Inject
    lateinit var addressAdapter: AddressAdapter

    @Inject
    lateinit var marginItemDecoration: MarginItemDecoration

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    override val viewModel: AddressesViewModel by viewModels { viewModelFactory }

    private val isDelivery: Boolean by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewDataBinding.run {
            bottomSheetAddressIvCreateAddress.toggleVisibility(isDelivery)
            if (isDelivery) {
                viewModel.userAddressList.onEach { userAddressList ->
                    addressAdapter.submitList(userAddressList)
                }.startedLaunch()
            } else {
                viewModel.cafeAddressList.onEach { userAddressList ->
                    addressAdapter.submitList(userAddressList)
                }.startedLaunch()
            }

            if (isDelivery) {
                bottomSheetAddressTvTitle.text =
                    resourcesProvider.getString(R.string.title_bottom_sheet_addresses_your_address)
                bottomSheetAddressIvCreateAddress.setOnClickListener {
                    viewModel.onCreateAddressClicked()
                }
            } else {
                bottomSheetAddressTvTitle.text =
                    resourcesProvider.getString(R.string.title_bottom_sheet_addresses_cafe_address)
            }
            addressAdapter.setOnItemClickListener { address ->
                // viewModel.saveSelectedAddress(address.uuid, isDelivery)
            }
            bottomSheetAddressRvResult.adapter = addressAdapter
            bottomSheetAddressRvResult.addItemDecoration(marginItemDecoration)
        }
    }

    override fun onDestroyView() {
        viewDataBinding.bottomSheetAddressRvResult.adapter = null

        super.onDestroyView()
    }
}