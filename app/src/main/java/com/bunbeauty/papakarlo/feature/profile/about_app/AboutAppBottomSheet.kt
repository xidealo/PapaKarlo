package com.bunbeauty.papakarlo.feature.profile.about_app

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.databinding.BottomSheetAboutAppBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent

class AboutAppBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_about_app) {

    override val viewModel: EmptyViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(BottomSheetAboutAppBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetAboutAppNcVersion.cardText =
            resourcesProvider.getString(R.string.msg_about_app_version) + BuildConfig.VERSION_NAME
    }
}