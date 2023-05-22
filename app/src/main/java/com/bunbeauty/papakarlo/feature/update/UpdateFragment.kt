package com.bunbeauty.papakarlo.feature.update

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.button.MainButton
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.bold
import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateFragment : BaseFragment(R.layout.layout_compose) {

    override val viewModel: EmptyViewModel by viewModel()
    override val viewBinding by viewBinding(LayoutComposeBinding::bind)

    //private val googlePlayLink = GetSocialNetworkLinksUseCase().invoke().googlePlayLink

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.setContentWithTheme {
            UpdateScreen()
        }
    }

    @Composable
    private fun UpdateScreen() {
        FoodDeliveryScaffold(
            title = stringResource(R.string.title_update_new_app_version),
            actionButton = {
                MainButton(
                    modifier = Modifier.padding(horizontal = FoodDeliveryTheme.dimensions.mediumSpace),
                    textStringId = R.string.action_update_update
                ) {
                    val uri = Uri.parse("googlePlayLink")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            }
        ) {
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
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun UpdateScreenPreview() {
        FoodDeliveryTheme {
            UpdateScreen()
        }
    }
}
