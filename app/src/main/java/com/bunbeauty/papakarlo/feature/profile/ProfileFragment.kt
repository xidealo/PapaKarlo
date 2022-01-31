package com.bunbeauty.papakarlo.feature.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.databinding.FragmentProfileBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override val viewModel: ProfileViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentProfileBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        setupLastOrder()
        setupAddresses()
        viewBinding.run {
            viewModel.profileState.startedLaunch { state ->
                fragmentProfilePbLoading.toggleVisibility(state is State.Loading)
                fragmentProfileGroupHasProfile.toggleVisibility(state is State.Success)
                fragmentProfileGroupNoProfile.toggleVisibility(state is State.Empty)
                fragmentProfileGroupInfo.toggleVisibility(state !is State.Loading)
            }
            fragmentProfileNcSettings.setOnClickListener {
                viewModel.onSettingsClicked()
            }
            fragmentProfileNcAddresses.setOnClickListener {
                viewModel.onAddressClicked()
            }
            fragmentProfileNcOrders.setOnClickListener {
                viewModel.onOrderListClicked()
            }
            fragmentProfileNcPayment.setOnClickListener {
                viewModel.onPaymentClicked()
            }
            fragmentProfileNcFeedback.setOnClickListener {
                viewModel.onFeedbackClicked()
            }
            fragmentProfileNcAboutApp.setOnClickListener {
                viewModel.onAboutAppClicked()
            }
            fragmentProfileBtnLogin.setOnClickListener {
                viewModel.onLoginClicked()
            }
        }
    }

    private fun setupLastOrder() {
        viewModel.lastOrder.startedLaunch { orderItem ->
            viewBinding.fragmentProfileItemLastOrder.run {
                root.toggleVisibility(orderItem != null)
                if (orderItem != null) {
                    elementOrderTvCode.text = orderItem.code
                    elementOrderChipStatus.text = orderItem.orderStatus
                    elementOrderChipStatus.setChipBackgroundColorResource(orderItem.orderColorResource)
                    elementOrderTvTime.text = orderItem.dateTime
                    elementOrderMvcMain.setOnClickListener {
                        viewModel.onLastOrderClicked()
                    }
                }
            }
        }
    }

    private fun setupAddresses() {
        viewModel.hasAddresses.startedLaunch { hasAddresses ->
            viewBinding.fragmentProfileNcAddresses.run {
                if (hasAddresses) {
                    cardText = resourcesProvider.getString(R.string.action_profile_your_addresses)
                    icon = resourcesProvider.getDrawable(R.drawable.ic_address)
                } else {
                    cardText = resourcesProvider.getString(R.string.action_profile_create_address)
                    icon = resourcesProvider.getDrawable(R.drawable.ic_add)
                }
            }
        }
    }
}