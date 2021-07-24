package com.bunbeauty.papakarlo.ui.fragment.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.INSTAGRAM_LINK
import com.bunbeauty.common.Constants.PLAY_MARKET_LINK
import com.bunbeauty.common.Constants.VK_LINK
import com.bunbeauty.papakarlo.databinding.BottomSheetFeedbackBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.EmptyViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog

class FeedbackBottomSheet : BaseBottomSheetDialog<BottomSheetFeedbackBinding>() {

    override val viewModel: EmptyViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
            bottomSheetFeedbackNcVk.setOnClickListener {
                goByLink(VK_LINK)
            }
            bottomSheetFeedbackNcInstagram.setOnClickListener {
                goByLink(INSTAGRAM_LINK)
            }
            bottomSheetFeedbackNcPlayMarket.setOnClickListener {
                goByLink(PLAY_MARKET_LINK)
            }
        }
    }

    private fun goByLink(link: String) {
        val uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}