package com.bunbeauty.papakarlo.feature.cafe.cafe_options

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.COORDINATES_DIVIDER
import com.bunbeauty.common.Constants.MAPS_LINK
import com.bunbeauty.common.Constants.PHONE_LINK
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.compose.card.NavigationIconCard
import com.bunbeauty.papakarlo.compose.element.CircularProgressBar
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.compose.theme.bottomSheetShape
import com.bunbeauty.papakarlo.databinding.BottomSheetCafeOptionsBinding
import com.bunbeauty.papakarlo.extensions.compose
import org.koin.androidx.viewmodel.ext.android.viewModel

class CafeOptionsBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_cafe_options) {

    override val viewModel: CafeOptionsViewModel by viewModel()
    override val viewBinding by viewBinding(BottomSheetCafeOptionsBinding::bind)

    private val cafeUuid: String by argument()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCafe(cafeUuid)
        viewBinding.bottomSheetCafeOptionsCvMain.compose {
            val cafeOptions by viewModel.cafeOptions.collectAsState()
            CafeOptionsScreen(cafeOptions)
        }
    }

    @Composable
    private fun CafeOptionsScreen(cafeOptions: CafeOptions?) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(bottomSheetShape)
                .background(FoodDeliveryTheme.colors.surface)
                .padding(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            if (cafeOptions == null) {
                CircularProgressBar(modifier = Modifier.align(CenterHorizontally))
            } else {
                CafeOptionsSuccessScreen(cafeOptions)
            }
        }
    }

    @Composable
    private fun CafeOptionsSuccessScreen(cafeOptions: CafeOptions) {
        NavigationIconCard(
            iconId = R.drawable.ic_phone,
            iconDescription = R.string.description_cafe_options_call,
            label = cafeOptions.callToCafe,
            hasShadow = false
        ) {
            val uri = Uri.parse(PHONE_LINK + cafeOptions.phone)
            goByUri(uri, Intent.ACTION_DIAL)
        }
        NavigationIconCard(
            modifier = Modifier.padding(top = FoodDeliveryTheme.dimensions.smallSpace),
            iconId = R.drawable.ic_address,
            iconDescription = R.string.description_cafe_options_map,
            label = cafeOptions.showOnMap,
            hasShadow = false
        ) {
            val uri =
                Uri.parse(MAPS_LINK + cafeOptions.latitude + COORDINATES_DIVIDER + cafeOptions.longitude)
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
        CafeOptionsScreen(
            CafeOptions(
                showOnMap = "На карте: улица Чапаева, д 22А",
                callToCafe = "Позвонить: +7 (900) 900-90-90",
                phone = "",
                latitude = 0.0,
                longitude = 0.0,
            )
        )
    }

    @Preview
    @Composable
    private fun CafeOptionsLoadingScreenPreview() {
        CafeOptionsScreen(null)
    }
}