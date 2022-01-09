package com.bunbeauty.papakarlo.ui.fragment.cafe

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.COORDINATES_DIVIDER
import com.bunbeauty.common.Constants.MAPS_LINK
import com.bunbeauty.common.Constants.PHONE_LINK
import com.bunbeauty.papakarlo.databinding.BottomSheetCafeOptionsBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.cafe.CafeOptionsViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheet
import kotlinx.coroutines.flow.onEach

class CafeOptionsBottomSheet : BaseBottomSheet<BottomSheetCafeOptionsBinding>() {

    override val viewModel: CafeOptionsViewModel by viewModels { viewModelFactory }

    private val cafeUuid: String by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCafe(cafeUuid)
        viewDataBinding.run {
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