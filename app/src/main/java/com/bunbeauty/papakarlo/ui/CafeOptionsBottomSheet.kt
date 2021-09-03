package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.domain.model.ui.Cafe
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
                goToPhone(cafeOption.phone)
            }
            bottomSheetCafeOptionsNcShowMap.setOnClickListener {
                goToAddress(cafeOption.latitude, cafeOption.longitude)
            }
        }
    }

    private fun goToAddress(latitude: Double, longitude: Double) {
        val uri = Uri.parse("http://maps.google.com/maps?daddr=$latitude,$longitude")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun goToPhone(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        startActivity(intent)
    }
}