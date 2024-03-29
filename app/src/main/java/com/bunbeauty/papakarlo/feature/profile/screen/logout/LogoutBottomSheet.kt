package com.bunbeauty.papakarlo.feature.profile.screen.logout

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentManager
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.ComposeBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.element.button.SecondaryButton
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LogoutBottomSheet : ComposeBottomSheet<Boolean>() {

    @Composable
    override fun Content() {
        LogoutScreen(
            onLogoutClick = {
                callback?.onResult(true)
            },
            onCancelClick = {
                callback?.onResult(false)
            }
        )
    }

    companion object {
        private const val TAG = "EmailBottomSheet"

        suspend fun show(
            fragmentManager: FragmentManager
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
}

@Composable
private fun LogoutScreen(
    onLogoutClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    FoodDeliveryBottomSheet(titleStringId = R.string.title_logout) {
        MainButton(
            textStringId = R.string.action_logout,
            elevated = false,
            onClick = onLogoutClick
        )
        SecondaryButton(
            modifier = Modifier.padding(top = 4.dp),
            textStringId = R.string.action_logout_cancel,
            elevated = false,
            onClick = onCancelClick
        )
    }
}

@Preview
@Composable
private fun LogoutScreenPreview() {
    FoodDeliveryTheme {
        LogoutScreen(
            onLogoutClick = {},
            onCancelClick = {}
        )
    }
}
