package com.bunbeauty.papakarlo.feature.profile.screen.feedback

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.shared.Constants.INSTAGRAM_LINK
import com.bunbeauty.shared.Constants.PLAY_MARKET_LINK
import com.bunbeauty.shared.Constants.VK_LINK
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCard
import com.bunbeauty.papakarlo.common.ui.element.Title
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.BottomSheetFeedbackBinding
import com.bunbeauty.papakarlo.extensions.compose
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedbackBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_feedback) {

    override val viewModel: EmptyViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetFeedbackBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetFeedbackCvMain.compose {
            FeedbackScreen()
        }
    }

    private fun goByLink(link: String) {
        val uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    @Composable
    private fun FeedbackScreen() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Title(textStringId = R.string.title_feedback)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = FoodDeliveryTheme.dimensions.mediumSpace
                    )
            ) {
                NavigationIconCard(
                    iconId = R.drawable.ic_vk,
                    iconDescription = R.string.description_feedback_vk,
                    labelStringId = R.string.action_feedback_vk,
                    hasShadow = false
                ) {
                    goByLink(VK_LINK)
                }
                NavigationIconCard(
                    modifier = Modifier.padding(
                        top = FoodDeliveryTheme.dimensions.smallSpace,
                    ),
                    iconId = R.drawable.ic_instagram,
                    iconDescription = R.string.description_feedback_instagram,
                    labelStringId = R.string.action_feedback_instagram,
                    hasShadow = false
                ) {
                    goByLink(INSTAGRAM_LINK)
                }
                NavigationIconCard(
                    modifier = Modifier.padding(
                        top = FoodDeliveryTheme.dimensions.smallSpace,
                        bottom = FoodDeliveryTheme.dimensions.mediumSpace,
                    ),
                    iconId = R.drawable.ic_play_market,
                    iconDescription = R.string.description_feedback_play_market,
                    labelStringId = R.string.action_feedback_play_market,
                    hasShadow = false
                ) {
                    goByLink(PLAY_MARKET_LINK)
                }
            }
        }
    }

    @Preview
    @Composable
    private fun FeedbackScreenPreview() {
        FeedbackScreen()
    }
}