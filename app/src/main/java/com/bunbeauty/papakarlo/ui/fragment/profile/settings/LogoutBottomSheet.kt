package com.bunbeauty.papakarlo.ui.fragment.profile.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.databinding.BottomSheetLogoutBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.profile.settings.LogoutViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheet

class LogoutBottomSheet : BaseBottomSheet<BottomSheetLogoutBinding>() {

    override val viewModel: LogoutViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.bottomSheetLogoutBtnLogout.setOnClickListener {
            viewModel.logout()
        }
        viewDataBinding.bottomSheetLogoutCvCancel.setOnClickListener {
            viewModel.goBack()
        }
    }
}