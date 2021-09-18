package com.bunbeauty.papakarlo.ui.fragment.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.State
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentProfileBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.profile.ProfileViewModel
import com.bunbeauty.papakarlo.ui.base.TopbarCartFragment
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


class ProfileFragment : TopbarCartFragment<FragmentProfileBinding>() {

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    override val isCartVisible = true
    override val isBottomBarVisible = true

    override val viewModel: ProfileViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
            viewModel.userState.onEach { state ->
                fragmentProfilePbLoading.toggleVisibility(state is State.Loading)
                fragmentProfileGroupHasProfile.toggleVisibility(state is State.Success)
                fragmentProfileGroupNoProfile.toggleVisibility(state is State.Empty)

                if (state is State.Success) {
                    val profile = state.data

                    if (profile.addressList.isEmpty()) {
                        fragmentProfileNcAddresses.cardText =
                            resourcesProvider.getString(R.string.action_profile_create_address)
                        fragmentProfileNcAddresses.icon =
                            resourcesProvider.getDrawable(R.drawable.ic_add)
                    } else {
                        fragmentProfileNcAddresses.cardText =
                            resourcesProvider.getString(R.string.action_profile_your_addresses)
                        fragmentProfileNcAddresses.icon =
                            resourcesProvider.getDrawable(R.drawable.ic_right_arrow)
                    }

                    fragmentProfileItemLastOrder.root
                        .toggleVisibility(profile.addressList.isNotEmpty())
                }
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
}