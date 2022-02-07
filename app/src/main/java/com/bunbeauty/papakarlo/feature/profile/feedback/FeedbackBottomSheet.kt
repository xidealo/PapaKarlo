package com.bunbeauty.papakarlo.feature.profile.feedback

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.INSTAGRAM_LINK
import com.bunbeauty.common.Constants.PLAY_MARKET_LINK
import com.bunbeauty.common.Constants.VK_LINK
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.databinding.BottomSheetFeedbackBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedbackBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_feedback) {

    override val viewModel: EmptyViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetFeedbackBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
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