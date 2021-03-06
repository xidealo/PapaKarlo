package com.bunbeauty.papakarlo.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants
import com.bunbeauty.common.State
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentProfileBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.gone
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.visible
import com.bunbeauty.papakarlo.presentation.profile.ProfileViewModel
import com.bunbeauty.papakarlo.ui.base.TopbarCartFragment
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
        viewModel.userState.onEach { state ->
            when (state) {
                is State.Loading -> {
                    viewDataBinding.fragmentProfilePbLoading.visible()
                    viewDataBinding.fragmentProfileGroupHasProfile.gone()
                    viewDataBinding.fragmentProfileGroupNoProfile.gone()
                }
                is State.Success -> {
                    if (state.data == null) {
                        viewDataBinding.fragmentProfileGroupHasProfile.gone()
                        viewDataBinding.fragmentProfileGroupNoProfile.visible()
                    } else {
                        viewDataBinding.fragmentProfileGroupHasProfile.visible()
                        viewDataBinding.fragmentProfileGroupNoProfile.gone()
                        /* viewDataBinding.fragmentProfileTvBonusesValue.text =
                             viewModel.getBonusesString(
                                 state.data?.bonusList!!
                             )*/
                    }
                    viewDataBinding.fragmentProfilePbLoading.gone()
                }
                else -> Unit
            }
        }.startedLaunch(viewLifecycleOwner)
        viewModel.hasAddressState.onEach { state ->
            when (state) {
                is State.Success -> {
                    if (state.data) {
                        with(viewDataBinding) {
                            fragmentProfileMcvAddress.setOnClickListener {
                                viewModel.onAddressClicked()
                            }
                            fragmentProfileTvAddress.text =
                                resourcesProvider.getString(R.string.title_profile_your_address)
                            fragmentProfileIvCreateAddress.gone()
                            fragmentProfileIvSelectAddress.visible()
                        }
                    } else {
                        with(viewDataBinding) {
                            fragmentProfileMcvAddress.setOnClickListener {
                                viewModel.onCreateAddressClicked()
                            }
                            fragmentProfileTvAddress.text =
                                resourcesProvider.getString(R.string.title_profile_create_address)
                            fragmentProfileIvCreateAddress.visible()
                            fragmentProfileIvSelectAddress.gone()
                        }
                    }
                }
                else -> { }
            }
        }.startedLaunch(viewLifecycleOwner)
        viewModel.lastOrderState.onEach { state ->
            when (state) {
                is State.Success -> {
                    with(viewDataBinding) {
                        with(fragmentProfileILastOrder) {
                            elementOrderTvCode.text = state.data.code
                            elementOrderTvDeferred.text = state.data.deferredTime
                            elementOrderTvTime.text = state.data.time
                            elementOrderChipStatus.text = state.data.orderStatus
                            elementOrderChipStatus.setChipBackgroundColorResource(state.data.orderColor)
                            elementOrderMvcMain.setOnClickListener {
                                viewModel.goToOrder(state.data)
                            }
                        }
                    }
                }
                is State.Empty -> {
                    viewDataBinding.fragmentProfileILastOrder.elementOrderMvcMain.gone()
                }
                else -> Unit
            }
        }.startedLaunch(viewLifecycleOwner)

        setOnClickListeners()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setOnClickListeners() {
        with(viewDataBinding) {
            fragmentProfileMcvOrders.setOnClickListener {
                viewModel.onOrderListClicked()
            }
            fragmentProfileBtnLogin.setOnClickListener {
                viewModel.goToLogin()
            }
            fragmentProfileMcvSettings.setOnClickListener {
                viewModel.goToSettings()
            }
            fragmentProfileMcvVk.setOnClickListener {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(Constants.VK_LINK))
                startActivity(browserIntent)
            }
            fragmentProfileMcvInstagram.setOnClickListener {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(Constants.INSTAGRAM_LINK)
                )
                startActivity(browserIntent)
            }
            fragmentProfileMcvPayment.setOnClickListener {
                viewModel.router.navigate(ProfileFragmentDirections.toPaymentBottomSheet())
            }
            fragmentProfileMcvGooglePlay.setOnClickListener {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(Constants.GOOGLE_PLAY_LINK)
                )
                startActivity(browserIntent)
            }
        }
    }
}