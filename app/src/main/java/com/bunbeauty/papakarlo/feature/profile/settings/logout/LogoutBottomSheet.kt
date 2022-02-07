package com.bunbeauty.papakarlo.feature.profile.settings.logout

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.databinding.BottomSheetLogoutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogoutBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_logout) {

    override val viewModel: LogoutViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetLogoutBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetLogoutBtnLogout.setOnClickListener {
            viewModel.logout()
        }
        viewBinding.bottomSheetLogoutCvCancel.setOnClickListener {
            viewModel.goBack()
        }
    }
}