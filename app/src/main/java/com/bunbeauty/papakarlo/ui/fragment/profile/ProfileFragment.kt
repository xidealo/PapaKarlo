package com.bunbeauty.papakarlo.ui.fragment.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentProfileBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.profile.ProfileViewModel
import com.bunbeauty.papakarlo.presentation.state.State
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val viewModel: ProfileViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLastOrder()
        setupAddresses()
        viewDataBinding.run {
            viewModel.profileState.onEach { state ->
                fragmentProfilePbLoading.toggleVisibility(state is State.Loading)
                fragmentProfileGroupHasProfile.toggleVisibility(state is State.Success)
                fragmentProfileGroupNoProfile.toggleVisibility(state is State.Empty)
            }.startedLaunch()
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
            fragmentProfileBtnLogin.setOnClickListener {
                viewModel.onLoginClicked()
            }
        }
    }

    private fun setupLastOrder() {
        viewModel.lastOrder.onEach { orderItem ->
            viewDataBinding.fragmentProfileItemLastOrder.run {
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
        }.startedLaunch()
    }

    private fun setupAddresses() {
        viewModel.hasAddresses.onEach { hasAddresses ->
            viewDataBinding.fragmentProfileNcAddresses.run {
                if (hasAddresses) {
                    cardText = resourcesProvider.getString(R.string.action_profile_your_addresses)
                    icon = resourcesProvider.getDrawable(R.drawable.ic_address)
                } else {
                    cardText = resourcesProvider.getString(R.string.action_profile_create_address)
                    icon = resourcesProvider.getDrawable(R.drawable.ic_add)
                }
            }
        }.startedLaunch()
    }
}