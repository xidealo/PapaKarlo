package com.bunbeauty.papakarlo.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.PLAY_MARKET_LINK
import com.bunbeauty.papakarlo.databinding.FragmentUpdateBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.EmptyViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment

class UpdateFragment : BaseFragment<FragmentUpdateBinding>() {

    override val viewModel: EmptyViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.fragmentUpdateBtnUpdate.setOnClickListener {
            val uri = Uri.parse(PLAY_MARKET_LINK)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}