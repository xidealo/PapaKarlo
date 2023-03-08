package com.bunbeauty.papakarlo.feature.profile.screen.logout

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.element.SecondaryButton
import com.bunbeauty.papakarlo.common.ui.element.Title
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bottomSheetShape
import com.bunbeauty.papakarlo.databinding.BottomSheetLogoutBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogoutBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_logout) {

    override val viewModel: LogoutViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetLogoutBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.bottomSheetLogoutCvMain.setContentWithTheme {
            LogoutScreen()
        }
    }

    @Composable
    private fun LogoutScreen() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(bottomSheetShape)
                .background(FoodDeliveryTheme.colors.surface)
        ) {
            Title(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                textStringId = R.string.title_logout
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace)
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                MainButton(
                    textStringId = R.string.action_logout,
                    hasShadow = false
                ) {
                    viewModel.logout()
                }
                SecondaryButton(
                    modifier = Modifier.padding(
                        top = FoodDeliveryTheme.dimensions.smallSpace,
                        bottom = FoodDeliveryTheme.dimensions.mediumSpace
                    ),
                    textStringId = R.string.action_logout_cancel,
                    hasShadow = false
                ) {
                    viewModel.goBack()
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun LogoutScreenPreview() {
        FoodDeliveryTheme {
            LogoutScreen()
        }
    }
}
