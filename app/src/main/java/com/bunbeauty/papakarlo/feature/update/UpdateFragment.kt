package com.bunbeauty.papakarlo.feature.update

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.shared.domain.model.link.Link
import com.bunbeauty.shared.domain.model.link.LinkType
import com.bunbeauty.shared.presentation.update.UpdateUiState
import com.bunbeauty.shared.presentation.update.UpdateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateFragment : BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    private val viewModel: UpdateViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        launchOnLifecycle {
            viewModel.updateGooglePlayLink()
        }

        viewBinding.root.setContentWithTheme {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            UpdateScreen(
                uiState = uiState,
                onRepeatClick = viewModel::updateGooglePlayLink
            )
        }
    }

    @Composable
    private fun UpdateScreen(
        uiState: UpdateUiState,
        onRepeatClick: () -> Unit,
    ) {
        FoodDeliveryScaffold(
            title = stringResource(R.string.title_update_new_app_version),
            actionButton = {
                if (uiState is UpdateUiState.Success) {
                    MainButton(
                        modifier = Modifier.padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                        textStringId = R.string.action_update_update
                    ) {
                        val uri = Uri.parse(uiState.googlePayLink.value)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                }
            }
        ) {
            when (uiState) {
                UpdateUiState.Loading -> LoadingScreen()
                UpdateUiState.Error -> ErrorScreen(
                    mainTextId = R.string.error_common_data_loading,
                    onClick = onRepeatClick
                )
                is UpdateUiState.Success -> UpdateScreenSuccess()
            }
        }
    }

    @Composable
    private fun UpdateScreenSuccess() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(FoodDeliveryTheme.colors.statusColors.warning),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(64.dp),
                    painter = painterResource(R.drawable.ic_google_play),
                    tint = FoodDeliveryTheme.colors.statusColors.onStatus,
                    contentDescription = stringResource(R.string.description_google_play)
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                text = stringResource(id = R.string.msg_update_new_title_app_version),
                style = FoodDeliveryTheme.typography.titleMedium.bold,
                color = FoodDeliveryTheme.colors.mainColors.onBackground,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                text = stringResource(id = R.string.msg_update_new_app_version),
                style = FoodDeliveryTheme.typography.bodyLarge,
                color = FoodDeliveryTheme.colors.mainColors.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun UpdateScreenSuccessPreview() {
        FoodDeliveryTheme {
            UpdateScreen(
                uiState = UpdateUiState.Success(
                    Link(
                        uuid = "1",
                        type = LinkType.GOOGLE_PLAY,
                        value = "https://play.google.com/store/apps/details?id=1",
                    )
                ),
                onRepeatClick = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun UpdateScreenErrorPreview() {
        FoodDeliveryTheme {
            UpdateScreen(
                uiState = UpdateUiState.Error,
                onRepeatClick = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun UpdateScreenLoadingPreview() {
        FoodDeliveryTheme {
            UpdateScreen(
                uiState = UpdateUiState.Loading,
                onRepeatClick = {}
            )
        }
    }
}
