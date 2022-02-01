package com.bunbeauty.papakarlo.feature.select_city

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.databinding.ElementCityBinding
import com.bunbeauty.papakarlo.databinding.FragmentSelectCityBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.toggleVisibility

class SelectCityFragment : BaseFragment(R.layout.fragment_select_city) {

    override val viewModel: SelectCityViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentSelectCityBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            viewModel.cityList.startedLaunch { cityList ->
                fragmentSelectCityPbLoading.toggleVisibility(cityList == null)
                if (cityList != null) {
                    fragmentSelectCityLlCityList.removeAllViews()
                    cityList.forEach { city ->
                        ElementCityBinding.inflate(
                            layoutInflater,
                            fragmentSelectCityLlCityList,
                            true
                        ).apply {
                            elementCityTvName.text = city.name
                            root.setOnClickListener {
                                viewModel.onCitySelected(city)
                            }
                        }
                    }
                }
            }
        }
    }
}