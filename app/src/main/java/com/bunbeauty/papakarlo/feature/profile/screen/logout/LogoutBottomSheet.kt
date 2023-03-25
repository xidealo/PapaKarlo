package com.bunbeauty.papakarlo.feature.profile.screen.logout

import android.os.Bundle
import android.view.View
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
import com.bunbeauty.papakarlo.common.ui.screen.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LogoutBottomSheet : ComposeBottomSheet<Boolean>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setContentWithTheme {
            LogoutScreen(
                onLogoutClick = {
                    callback?.onResult(true)
                },
                onCancelClick = {
                    callback?.onResult(false)
                }
            )
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
}

@Composable
private fun LogoutScreen(
    onLogoutClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    FoodDeliveryBottomSheet(titleStringId = R.string.title_logout) {
        MainButton(
            textStringId = R.string.action_logout,
            hasShadow = false,
            onClick = onLogoutClick
        )
        SecondaryButton(
            modifier = Modifier.padding(top = 4.dp),
            textStringId = R.string.action_logout_cancel,
            hasShadow = false,
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
