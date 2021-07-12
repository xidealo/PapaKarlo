package com.bunbeauty.papakarlo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.domain.model.local.cafe.Coordinate
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetCafeOptionsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BaseBottomSheetDialog
import com.bunbeauty.papakarlo.presentation.CafeOptionsViewModel
import com.bunbeauty.papakarlo.ui.CafeOptionsBottomSheetArgs.fromBundle
import javax.inject.Inject

class CafeOptionsBottomSheet : BaseBottomSheetDialog<BottomSheetCafeOptionsBinding>() {

    override var layoutId = R.layout.bottom_sheet_cafe_options
    override val viewModel: CafeOptionsViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @Inject
    lateinit var stringHelper: IStringHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cafe = fromBundle(requireArguments()).cafeAdapterModel
        with(viewDataBinding) {
            bottomSheetCafeOptionsMcvCall.setOnClickListener {
                goToPhone(cafe.phone)
            }
            bottomSheetCafeOptionsMcvShowMap.setOnClickListener {
                goToAddress(cafe.coordinate)
            }
            bottomSheetCafeOptionsTvShowMap.text =
                "${iResourcesProvider.getString(R.string.title_cafe_options_show_map)} ${
                    cafe.address
                }"

            bottomSheetCafeOptionsTvCall.text =
                "${iResourcesProvider.getString(R.string.title_cafe_options_call)} ${
                    cafe.phone
                }"
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