package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.domain.model.local.cafe.Coordinate
import com.bunbeauty.papakarlo.databinding.BottomSheetCafeOptionsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.cafe.CafeOptionsViewModel
import com.bunbeauty.papakarlo.ui.CafeOptionsBottomSheetArgs.fromBundle
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog

class CafeOptionsBottomSheet : BaseBottomSheetDialog<BottomSheetCafeOptionsBinding>() {

    override val viewModel: CafeOptionsViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            with(viewDataBinding) {
                val cafe =
                    viewModel.getCafeOptionModel(fromBundle(requireArguments()).cafeAdapterModel)
                bottomSheetCafeOptionsMcvCall.setOnClickListener {
                    goToPhone(cafe.phone)
                }
                bottomSheetCafeOptionsMcvShowMap.setOnClickListener {
                    goToAddress(cafe.coordinate)
                }
                bottomSheetCafeOptionsTvShowMap.text = cafe.address
                bottomSheetCafeOptionsTvCall.text = cafe.phoneWithText
            }
    }

    private fun goToAddress(coordinate: Coordinate) {
        val uri =
            Uri.parse("http://maps.google.com/maps?daddr=${coordinate.latitude},${coordinate.longitude}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun goToPhone(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        startActivity(intent)
    }
}