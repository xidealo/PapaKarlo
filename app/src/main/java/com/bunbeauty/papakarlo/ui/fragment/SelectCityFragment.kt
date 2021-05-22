package com.bunbeauty.papakarlo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.databinding.ElementCityBinding
import com.bunbeauty.papakarlo.databinding.FragmentSelectCityBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.SelectCityViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach

class SelectCityFragment : BaseFragment<FragmentSelectCityBinding>() {

    override val isToolbarVisible = false
    override val viewModel: SelectCityViewModel by viewModels { modelFactory }

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkIsCitySelected()
        viewDataBinding.run {
            viewModel.cityList.onEach { cityList ->
                fragmentSelectCityLlCityList.removeAllViews()
                cityList.forEach { city ->
                    ElementCityBinding.inflate(layoutInflater, fragmentSelectCityLlCityList, true)
                        .apply {
                            elementCityTvName.text = city.name
                            root.setOnClickListener {
                                viewModel.onCitySelected(city)
                            }
                        }
                }
            }.startedLaunch()
            viewModel.isLoading.onEach { isLoading ->
                fragmentSelectCityPbLoading.toggleVisibility(isLoading)
                fragmentSelectCityLlCityList.toggleVisibility(!isLoading)
                fragmentSelectCityTvTitle.toggleVisibility(!isLoading)
            }.startedLaunch()
        }
    }
}