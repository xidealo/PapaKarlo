package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants
import com.bunbeauty.papakarlo.R
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.databinding.FragmentCreationAddressBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.presentation.address.CreationAddressViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CreationAddressFragment : BaseFragment<FragmentCreationAddressBinding>() {

    override val viewModel: CreationAddressViewModel by viewModels { modelFactory }

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.run {
            textInputMap[Constants.STREET_ERROR_KEY] = fragmentCreationAddressTilStreet
            textInputMap[Constants.HOUSE_ERROR_KEY] = fragmentCreationAddressTilHouse
            textInputMap[Constants.FLAT_ERROR_KEY] = fragmentCreationAddressTilFlat
            textInputMap[Constants.ENTRANCE_ERROR_KEY] = fragmentCreationAddressTilEntrance
            textInputMap[Constants.COMMENT_ERROR_KEY] = fragmentCreationAddressTilComment
            textInputMap[Constants.FLOOR_ERROR_KEY] = fragmentCreationAddressTilFloor

            viewModel.streetNameList.onEach { streetNameList ->
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    streetNameList
                )
                fragmentCreationAddressEtStreet.setAdapter(arrayAdapter)
            }.startedLaunch()

            fragmentCreationAddressBtnCreationAddress.setOnClickListener {
                viewModel.onCreateAddressClicked(
                    streetName = fragmentCreationAddressEtStreet.text.toString(),
                    house = fragmentCreationAddressEtHouse.text.toString().trim(),
                    flat = fragmentCreationAddressEtFlat.text.toString().trim(),
                    entrance = fragmentCreationAddressEtEntrance.text.toString().trim(),
                    comment = fragmentCreationAddressEtComment.text.toString().trim(),
                    floor = fragmentCreationAddressEtFloor.text.toString().trim()
                )
            }
        }
    }
}