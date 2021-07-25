package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.COORDINATES_DIVIDER
import com.bunbeauty.common.Constants.MAPS_LINK
import com.bunbeauty.common.Constants.PHONE_LINK
import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.papakarlo.databinding.BottomSheetCafeOptionsBinding
import com.bunbeauty.papakarlo.delegates.argument
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.cafe.CafeOptionsViewModel
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog

class CafeOptionsBottomSheet : BaseBottomSheetDialog<BottomSheetCafeOptionsBinding>() {

    override val viewModel: CafeOptionsViewModel by viewModels { modelFactory }

    private val cafe: Cafe by argument()

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cafeOption = viewModel.getCafeOptions(cafe)
        viewDataBinding.run {
            bottomSheetCafeOptionsNcCall.cardText = cafeOption.callToCafe
            bottomSheetCafeOptionsNcShowMap.cardText = cafeOption.showOnMap
            bottomSheetCafeOptionsNcCall.setOnClickListener {
                val uri = Uri.parse(PHONE_LINK + cafeOption.phone)
                goByUri(uri)
            }
            bottomSheetCafeOptionsNcShowMap.setOnClickListener {
                val uri =
                    Uri.parse(MAPS_LINK + cafeOption.latitude + COORDINATES_DIVIDER + cafeOption.longitude)
                goByUri(uri)
            }
        }
    }

    private fun goByUri(uri: Uri) {
        val intent = Intent(Intent.ACTION_DIAL, uri)
        startActivity(intent)
    }
}