package com.bunbeauty.papakarlo.feature.profile.screen.logout

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.DragHandle
import com.bunbeauty.papakarlo.common.ui.element.SecondaryButton
import com.bunbeauty.papakarlo.common.ui.element.Title
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LogoutBottomSheet : ComposeBottomSheet<Boolean>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setContentWithTheme {
            LogoutScreen()
        }
    }

    @Composable
    private fun LogoutScreen() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
                .background(FoodDeliveryTheme.colors.mainColors.surface)
        ) {
            DragHandle()

            Title(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                textStringId = R.string.title_logout
            )
            Column(
                modifier = Modifier
                    .padding(top = FoodDeliveryTheme.dimensions.mediumSpace)
            ) {
                MainButton(
                    textStringId = R.string.action_logout,
                    hasShadow = false
                ) {
                    callback?.onResult(true)
                }
                SecondaryButton(
                    modifier = Modifier.padding(
                        top = FoodDeliveryTheme.dimensions.smallSpace,
                        bottom = FoodDeliveryTheme.dimensions.mediumSpace
                    ),
                    textStringId = R.string.action_logout_cancel,
                    hasShadow = false
                ) {
                    callback?.onResult(false)
                }
            }
        }
    }

    companion object {
        private const val TAG = "EmailBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager,
        ) = suspendCoroutine { continuation ->
            LogoutBottomSheet().apply {
                callback = object : Callback<Boolean> {
                    override fun onResult(result: Boolean?) {
                        continuation.resume(result)
                        dismiss()
                    }
                }
                show(fragmentManager, TAG)
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
