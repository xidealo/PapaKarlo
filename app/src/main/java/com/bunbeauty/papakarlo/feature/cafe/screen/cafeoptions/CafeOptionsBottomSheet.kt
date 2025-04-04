package com.bunbeauty.papakarlo.feature.cafe.screen.cafeoptions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.ui.element.CircularProgressBar
import com.bunbeauty.papakarlo.common.ui.element.card.NavigationIconCard
import com.bunbeauty.papakarlo.common.ui.screen.bottomsheet.FoodDeliveryBottomSheet
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.BottomSheetComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.cafe.model.CafeOptions
import com.bunbeauty.shared.Constants.COORDINATES_DIVIDER
import com.bunbeauty.shared.Constants.MAPS_LINK
import com.bunbeauty.shared.Constants.PHONE_LINK
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class CafeOptionsBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_compose) {

    override val viewModel: CafeOptionsViewModel by stateViewModel(state = {
        arguments ?: bundleOf()
    })
    override val viewBinding by viewBinding(BottomSheetComposeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.root.setContentWithTheme {
            val cafeOptions by viewModel.cafeOptions.collectAsStateWithLifecycle()
            CafeOptionsScreen(cafeOptions)
        }
    }

    @Composable
    private fun CafeOptionsScreen(cafeOptions: CafeOptions?) {
        FoodDeliveryBottomSheet(
            title = cafeOptions?.title ?: "",
            content = {
                if (cafeOptions == null) {
                    CircularProgressBar(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(CenterHorizontally)
                    )
                } else {
                    CafeOptionsSuccessScreen(cafeOptions)
                }
            }
        )
    }

    @Composable
    private fun CafeOptionsSuccessScreen(cafeOptions: CafeOptions) {
        NavigationIconCard(
            iconId = R.drawable.ic_call,
            iconDescriptionStringId = R.string.description_cafe_options_call,
            label = cafeOptions.callToCafe,
            elevated = false
        ) {
            val uri = (PHONE_LINK + cafeOptions.phone).toUri()
            goByUri(uri, Intent.ACTION_DIAL)
        }
        NavigationIconCard(
            modifier = Modifier.padding(top = 8.dp),
            iconId = R.drawable.ic_address,
            iconDescriptionStringId = R.string.description_cafe_options_map,
            label = cafeOptions.showOnMap,
            elevated = false
        ) {
            val uri =
                (MAPS_LINK + cafeOptions.latitude + COORDINATES_DIVIDER + cafeOptions.longitude).toUri()
            goByUri(uri, Intent.ACTION_VIEW)
        }
    }

    private fun goByUri(uri: Uri, action: String) {
        val intent = Intent(action, uri)
        startActivity(intent)
    }

    @Preview
    @Composable
    private fun CafeOptionsSuccessScreenPreview() {
        FoodDeliveryTheme {
            CafeOptionsScreen(
                CafeOptions(
                    title = "улица Чапаева, д 22А",
                    showOnMap = "На карте: улица Чапаева, д 22А",
                    callToCafe = "Позвонить: +7 (900) 900-90-90",
                    phone = "",
                    latitude = 0.0,
                    longitude = 0.0
                )
            )
        }
    }

    @Preview
    @Composable
    private fun CafeOptionsLoadingScreenPreview() {
        FoodDeliveryTheme {
            CafeOptionsScreen(null)
        }
    }
}
