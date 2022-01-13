package com.bunbeauty.papakarlo.ui.fragment.address

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.common.Constants.HOUSE_ERROR_KEY
import com.bunbeauty.common.Constants.STREET_ERROR_KEY
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentCreateAddressBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.address.CreationAddressViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach

class CreateAddressFragment : BaseFragment(R.layout.fragment_create_address) {

    override val viewModel: CreationAddressViewModel by viewModels { viewModelFactory }
    override val viewBinding by viewBinding(FragmentCreateAddressBinding::bind)

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.run {
            textInputMap[STREET_ERROR_KEY] = fragmentCreateAddressTilStreet
            textInputMap[HOUSE_ERROR_KEY] = fragmentCreateAddressTilHouse

            viewModel.streetNameList.onEach { streetNameList ->
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    streetNameList
                )
                fragmentCreateAddressEtStreet.setAdapter(arrayAdapter)
            }.startedLaunch()

            fragmentCreateAddressBtnCreateAddress.setOnClickListener {
                viewModel.onCreateAddressClicked(
                    streetName = fragmentCreateAddressEtStreet.text.toString(),
                    house = fragmentCreateAddressEtHouse.text.toString().trim(),
                    flat = fragmentCreateAddressEtFlat.text.toString().trim(),
                    entrance = fragmentCreateAddressEtEntrance.text.toString().trim(),
                    comment = fragmentCreateAddressEtComment.text.toString().trim(),
                    floor = fragmentCreateAddressEtFloor.text.toString().trim()
                )
            }
        }
    }
}