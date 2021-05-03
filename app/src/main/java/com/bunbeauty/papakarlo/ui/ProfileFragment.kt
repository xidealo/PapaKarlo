package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bunbeauty.common.State
import com.bunbeauty.common.extensions.gone
import com.bunbeauty.common.extensions.toggleVisibility
import com.bunbeauty.common.extensions.visible
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentProfileBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.ProfileViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import kotlinx.coroutines.flow.onEach

class ProfileFragment : BarsFragment<FragmentProfileBinding>() {

    override var layoutId = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels { modelFactory }
    override val isBottomBarVisible = true

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getUser()
        viewModel.userState.onEach { state ->
            when (state) {
                is State.Loading -> {
                    viewDataBinding.fragmentProfilePbLoading.visible()
                }
                is State.Success -> {
                    if (state.data == null) {
                        viewDataBinding.fragmentProfileGroupHasProfile.toggleVisibility(false)
                        viewDataBinding.fragmentProfileGroupNoProfile.toggleVisibility(true)
                    } else {
                        viewDataBinding.fragmentProfileGroupHasProfile.toggleVisibility(true)
                        viewDataBinding.fragmentProfileGroupNoProfile.toggleVisibility(false)
                        viewDataBinding.fragmentProfileTvPhone.text = state.data?.phone
                        viewDataBinding.fragmentProfileTvEmail.text = state.data?.email
                        viewDataBinding.fragmentProfileTvBonusesValue.text = state.data?.bonus.toString()
                    }
                    viewDataBinding.fragmentProfilePbLoading.gone()
                }
                else ->{}
            }
        }.launchWhenStarted(lifecycleScope)

   /*     viewModel.getAddress().onEach { resource ->
            when (resource) {
                is State.Success -> {
                    if (resource.data == null) {
                        viewDataBinding.fragmentProfileGroupHasAddress.toggleVisibility(false)
                        viewDataBinding.fragmentProfileGroupNoAddress.toggleVisibility(true)
                    } else {
                        viewDataBinding.fragmentProfileGroupHasAddress.toggleVisibility(true)
                        viewDataBinding.fragmentProfileGroupNoAddress.toggleVisibility(false)
                    }
                }
                else -> {
                }
            }
        }.launchWhenStarted(lifecycleScope)*/

        viewDataBinding.fragmentProfileBtnOrderListPick.setOnClickListener {
            viewModel.onOrderListClicked()
        }
        viewDataBinding.fragmentProfileBtnAddressPick.setOnClickListener {
            viewModel.onAddressClicked()
        }
        viewDataBinding.fragmentProfileBtnCreateAddress.setOnClickListener {
            viewModel.onCreateAddressClicked()
        }
        viewDataBinding.fragmentProfileBtnLogin.setOnClickListener {
            viewModel.goToLogin()
        }
        viewDataBinding.fragmentProfileBtnLogout.setOnClickListener {
            viewModel.logout()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}