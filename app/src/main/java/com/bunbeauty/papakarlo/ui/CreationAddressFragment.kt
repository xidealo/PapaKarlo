package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.common.Constants
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.databinding.FragmentCreationAddressBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.address.CreationAddressViewModel
import com.bunbeauty.papakarlo.ui.base.BaseFragment
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
        textInputMap[Constants.STREET_ERROR_KEY] = viewDataBinding.fragmentCreationAddressTilStreet
        textInputMap[Constants.HOUSE_ERROR_KEY] = viewDataBinding.fragmentCreationAddressTilHouse
        textInputMap[Constants.FLAT_ERROR_KEY] = viewDataBinding.fragmentCreationAddressTilFlat
        textInputMap[Constants.ENTRANCE_ERROR_KEY] =
            viewDataBinding.fragmentCreationAddressTilEntrance
        textInputMap[Constants.COMMENT_ERROR_KEY] =
            viewDataBinding.fragmentCreationAddressTilComment
        textInputMap[Constants.FLOOR_ERROR_KEY] = viewDataBinding.fragmentCreationAddressTilFloor

        viewModel.getStreets()
        viewDataBinding.viewModel = viewModel
        viewDataBinding.fragmentCreationAddressBtnCreationAddress.setOnClickListener {
            createAddress()
        }
    }

    private fun createAddress() {
        viewModel.onCreateAddressClicked(
            street = viewDataBinding.fragmentCreationAddressEtStreet.text.toString(),
            house = viewDataBinding.fragmentCreationAddressEtHouse.text.toString().trim(),
            flat = viewDataBinding.fragmentCreationAddressEtFlat.text.toString().trim(),
            entrance = viewDataBinding.fragmentCreationAddressEtEntrance.text.toString().trim(),
            comment = viewDataBinding.fragmentCreationAddressEtComment.text.toString().trim(),
            floor = viewDataBinding.fragmentCreationAddressEtFloor.text.toString().trim()
        )
    }
}