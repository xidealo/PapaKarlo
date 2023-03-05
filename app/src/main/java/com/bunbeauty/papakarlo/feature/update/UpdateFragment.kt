package com.bunbeauty.papakarlo.feature.update

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.ui.element.MainButton
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.databinding.FragmentUpdateBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.shared.Constants.PLAY_MARKET_LINK
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateFragment : BaseFragment(R.layout.fragment_update) {

    override val viewModel: EmptyViewModel by viewModel()
    override val viewBinding by viewBinding(FragmentUpdateBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentUpdateCvMain.compose {
            UpdateScreen()
        }
    }

    @Composable
    private fun UpdateScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.background)
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.google_play),
                    contentDescription = stringResource(R.string.description_consumer_cart_empty)
                )
                Text(
                    modifier = Modifier
                        .padding(top = FoodDeliveryTheme.dimensions.mediumSpace),
                    text = stringResource(R.string.msg_update_new_app_version),
                    style = FoodDeliveryTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
            MainButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                textStringId = R.string.action_update_update
            ) {
                val uri = Uri.parse(PLAY_MARKET_LINK)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }

    @Preview
    @Composable
    private fun UpdateScreenPreview() {
        UpdateScreen()
    }
}
