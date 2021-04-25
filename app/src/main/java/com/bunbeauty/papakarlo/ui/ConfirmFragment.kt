package com.bunbeauty.papakarlo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentConfirmBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.ConfirmViewModel
import com.bunbeauty.papakarlo.ui.base.BarsFragment

class ConfirmFragment : BarsFragment<FragmentConfirmBinding>() {

    override var layoutId = R.layout.fragment_confirm
    override val viewModel: ConfirmViewModel by viewModels { modelFactory }
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.fragmentConfirmTvPhoneInformation.text =
            "${viewDataBinding.fragmentConfirmTvPhoneInformation.text} ${
                ConfirmFragmentArgs.fromBundle(requireArguments()).phone
            }"
    }

}