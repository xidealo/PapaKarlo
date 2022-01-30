package com.bunbeauty.papakarlo.feature.cafe.cafe_options

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.COORDINATES_DIVIDER
import com.bunbeauty.common.Constants.MAPS_LINK
import com.bunbeauty.common.Constants.PHONE_LINK
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseBottomSheet
import com.bunbeauty.papakarlo.common.delegates.argument
import com.bunbeauty.papakarlo.databinding.BottomSheetCafeOptionsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import kotlinx.coroutines.flow.onEach

class CafeOptionsBottomSheet : BaseBottomSheet(R.layout.bottom_sheet_cafe_options) {

    override val viewModel: CafeOptionsViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(BottomSheetCafeOptionsBinding::bind)

    private val cafeUuid: String by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCafe(cafeUuid)
        viewBinding.run {
            viewModel.cafeOptions.onEach { cafeOptions ->
                cafeOptions ?: return@onEach

                bottomSheetCafeOptionsNcCall.cardText = cafeOptions.callToCafe
                bottomSheetCafeOptionsNcShowMap.cardText = cafeOptions.showOnMap
                bottomSheetCafeOptionsNcCall.setOnClickListener {
                    val uri = Uri.parse(PHONE_LINK + cafeOptions.phone)
                    goByUri(uri, Intent.ACTION_DIAL)
                }
                bottomSheetCafeOptionsNcShowMap.setOnClickListener {
                    val uri =
                        Uri.parse(MAPS_LINK + cafeOptions.latitude + COORDINATES_DIVIDER + cafeOptions.longitude)
                    goByUri(uri, Intent.ACTION_VIEW)
                }
            }.startedLaunch()
        }
    }

    private fun goByUri(uri: Uri, action: String) {
        val intent = Intent(action, uri)
        startActivity(intent)
    }
}