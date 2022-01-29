package com.bunbeauty.papakarlo.feature.update

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.PLAY_MARKET_LINK
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.view_model.EmptyViewModel
import com.bunbeauty.papakarlo.databinding.FragmentUpdateBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent

class UpdateFragment : BaseFragment(R.layout.fragment_update) {

    override val viewModel: EmptyViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentUpdateBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentUpdateBtnUpdate.setOnClickListener {
            val uri = Uri.parse(PLAY_MARKET_LINK)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}