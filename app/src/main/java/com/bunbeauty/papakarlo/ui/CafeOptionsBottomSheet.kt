package com.bunbeauty.papakarlo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bunbeauty.data.model.cafe.Coordinate
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetCafeOptionsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog

import com.bunbeauty.papakarlo.presentation.CafeOptionsViewModel
import com.bunbeauty.papakarlo.ui.CafeOptionsBottomSheetArgs.fromBundle
import javax.inject.Inject

class CafeOptionsBottomSheet :
    BaseBottomSheetDialog<BottomSheetCafeOptionsBinding, CafeOptionsViewModel>() {

    override var layoutId = R.layout.bottom_sheet_cafe_options
    override var viewModelVariable = BR.viewModel

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var stringHelper: IStringHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cafe = fromBundle(requireArguments()).cafe

        viewDataBinding.bottomSheetCafeOptionsBtnCall.setOnClickListener {
            goToPhone(cafe.cafeEntity.phone)
        }
        viewDataBinding.bottomSheetCafeOptionsBtnShowMap.setOnClickListener {
            goToAddress(cafe.cafeEntity.coordinate)
        }
        viewDataBinding.bottomSheetCafeOptionsTvAddress.text = stringHelper.toString(cafe.address)
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