package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bunbeauty.domain.model.local.address.UserAddress
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentCreationAddressBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.domain.util.resources.IResourcesProvider
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

        viewModel.getStreets()
        viewDataBinding.viewModel = viewModel
        viewDataBinding.fragmentCreationAddressBtnCreationAddress.setOnClickListener {
            createAddress()
        }
    }

    private fun createAddress() {
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentCreationAddressEtStreet.text.toString(),
                true,
                50
            ) || viewModel.streets.find { it.name == viewDataBinding.fragmentCreationAddressEtStreet.text.toString() } == null
        ) {
            viewDataBinding.fragmentCreationAddressTilStreet.error =
                resources.getString(R.string.error_creation_address_name)
            viewDataBinding.fragmentCreationAddressEtStreet.requestFocus()
            return
        }
        viewDataBinding.fragmentCreationAddressTilStreet.error = ""

        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentCreationAddressEtHouse.text.toString(),
                true,
                5
            )
        ) {
            viewDataBinding.fragmentCreationAddressTilHouse.error = " "
            viewDataBinding.fragmentCreationAddressEtHouse.requestFocus()
            return
        }
        viewDataBinding.fragmentCreationAddressTilHouse.error = ""

        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentCreationAddressEtFlat.text.toString(),
                false,
                5
            )
        ) {
            viewDataBinding.fragmentCreationAddressTilFlat.error = " "
            viewDataBinding.fragmentCreationAddressEtFlat.requestFocus()
            return
        }
        viewDataBinding.fragmentCreationAddressTilFlat.error = ""

        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentCreationAddressEtEntrance.text.toString(),
                false,
                5
            )
        ) {
            viewDataBinding.fragmentCreationAddressTilEntrance.error = " "
            viewDataBinding.fragmentCreationAddressEtEntrance.requestFocus()
            return
        }
        viewDataBinding.fragmentCreationAddressTilEntrance.error = ""

        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentCreationAddressEtIntercom.text.toString(),
                false,
                5
            )
        ) {
            viewDataBinding.fragmentCreationAddressTilIntercom.error = " "
            viewDataBinding.fragmentCreationAddressEtIntercom.requestFocus()
            return
        }
        viewDataBinding.fragmentCreationAddressTilIntercom.error = ""

        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentCreationAddressEtFloor.text.toString(),
                false,
                5
            )
        ) {
            viewDataBinding.fragmentCreationAddressTilFloor.error = " "
            viewDataBinding.fragmentCreationAddressEtFloor.requestFocus()
            return
        }
        viewDataBinding.fragmentCreationAddressTilFloor.error = ""

        viewModel.onCreateAddressClicked(
            UserAddress().apply {
                street = viewModel.streets.find { it.name == viewDataBinding.fragmentCreationAddressEtStreet.text.toString() }
                house = viewDataBinding.fragmentCreationAddressEtHouse.text.toString().trim()
                flat = viewDataBinding.fragmentCreationAddressEtFlat.text.toString().trim()
                entrance = viewDataBinding.fragmentCreationAddressEtEntrance.text.toString().trim()
                intercom = viewDataBinding.fragmentCreationAddressEtIntercom.text.toString().trim()
                floor = viewDataBinding.fragmentCreationAddressEtFloor.text.toString().trim()
            }
        )
    }
}