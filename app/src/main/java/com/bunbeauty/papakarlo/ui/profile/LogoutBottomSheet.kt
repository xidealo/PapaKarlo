package com.bunbeauty.papakarlo.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetLogoutBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.profile.LogoutViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog

class LogoutBottomSheet : BaseBottomSheetDialog<BottomSheetLogoutBinding>() {

    override var layoutId = R.layout.bottom_sheet_logout
    override val viewModel: LogoutViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.fragmentLogoutBtnCancel.setOnClickListener {
            viewModel.cancel()
        }
        viewDataBinding.fragmentLogoutBtnLogout.setOnClickListener {
            viewModel.logout()
        }
    }
}