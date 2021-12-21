package com.bunbeauty.papakarlo.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants.PLAY_MARKET_LINK
import com.bunbeauty.papakarlo.databinding.ElementCityBinding
import com.bunbeauty.papakarlo.databinding.FragmentStartBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.StartViewModel
import com.bunbeauty.papakarlo.presentation.state.StartScreenState
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach

class StartFragment : BaseFragment<FragmentStartBinding>() {

    override val isToolbarVisible = false
    override val viewModel: StartViewModel by viewModels { viewModelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
            viewModel.startScreenState.onEach { startScreenState ->
                fragmentStartTvSelectCity.toggleVisibility(startScreenState is StartScreenState.CitiesLoaded)
                fragmentStartLlCityList.toggleVisibility(startScreenState is StartScreenState.CitiesLoaded)

                fragmentStartPbLoading.toggleVisibility(startScreenState is StartScreenState.Loading)

                fragmentStartTvUpdateApp.toggleVisibility(startScreenState is StartScreenState.NotUpdated)
                fragmentStartTvUpdateAppInfo.toggleVisibility(startScreenState is StartScreenState.NotUpdated)
                fragmentStartBtnUpdate.toggleVisibility(startScreenState is StartScreenState.NotUpdated)

                if (startScreenState is StartScreenState.CitiesLoaded) {
                    fragmentStartLlCityList.removeAllViews()
                    startScreenState.citiList.forEach { city ->
                        ElementCityBinding.inflate(layoutInflater, fragmentStartLlCityList, true)
                            .apply {
                                elementCityTvName.text = city.name
                                root.setOnClickListener {
                                    viewModel.onCitySelected(city)
                                }
                            }
                    }
                }
            }.startedLaunch()
            fragmentStartBtnUpdate.setOnClickListener {
                val uri = Uri.parse(PLAY_MARKET_LINK)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }
}