package com.bunbeauty.papakarlo.ui.fragment.address

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.RESULT_USER_ADDRESS_KEY
import com.bunbeauty.common.Constants.USER_ADDRESS_REQUEST_KEY
import com.bunbeauty.papakarlo.databinding.BottomSheetUserAddressesBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.address.UserAddressesViewModel
import com.bunbeauty.papakarlo.ui.adapter.AddressAdapter
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheet
import com.bunbeauty.papakarlo.ui.custom.MarginItemDecoration
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class UserAddressesBottomSheet : BaseBottomSheet<BottomSheetUserAddressesBinding>() {

    @Inject
    lateinit var addressAdapter: AddressAdapter

    @Inject
    lateinit var marginItemDecoration: MarginItemDecoration

    override val viewModel: UserAddressesViewModel by viewModels { viewModelFactory }

    private val isClickable: Boolean by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
            bottomSheetUserAddressesBtnCreateAddress.setOnClickListener {
                viewModel.onCreateAddressClicked()
            }
            viewModel.userAddressList.onEach { userAddressList ->
                addressAdapter.submitList(userAddressList)
            }.startedLaunch()
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
            bottomSheetUserAddressesRvResult.addItemDecoration(marginItemDecoration)
        }
    }

    override fun onDestroyView() {
        viewDataBinding.bottomSheetUserAddressesRvResult.adapter = null

        super.onDestroyView()
    }
}