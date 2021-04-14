package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentProfileBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.ProfileViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProfileFragment : BarsFragment<FragmentProfileBinding>() {

    override var layoutId = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels { modelFactory }
    override val isBottomBarVisible = true
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var iStringHelper: IStringHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewDataBinding.fragmentProfileTvPhone.text = viewModel.phoneNumber
        viewDataBinding.fragmentProfileEtEmail.setText(viewModel.email)


        viewModel.addressStateFlow.onEach { address ->
            viewDataBinding.fragmentProfileBtnAddressPick.text =
                iStringHelper.toString(address)
        }.launchWhenStarted(lifecycleScope)

        super.onViewCreated(view, savedInstanceState)
    }

}