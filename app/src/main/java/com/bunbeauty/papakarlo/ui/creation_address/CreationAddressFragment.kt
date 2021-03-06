package com.bunbeauty.papakarlo.ui.creation_address

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.databinding.FragmentCreationAddressBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.CartClickableFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.view_model.CreationAddressViewModel
import java.lang.ref.WeakReference

class CreationAddressFragment : CartClickableFragment<FragmentCreationAddressBinding,
        CreationAddressViewModel>(), CreationAddressNavigator {

    override lateinit var title: String

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_creation_address)

        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = WeakReference(this)
        viewModel.getStreets()

        viewDataBinding.viewModel = viewModel
    }

    override fun createAddress() {
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

        viewModel.creationAddress(
            Address(
                street = viewModel.streets.find { it.name == viewDataBinding.fragmentCreationAddressEtStreet.text.toString() },
                house = viewDataBinding.fragmentCreationAddressEtHouse.text.toString().trim(),
                flat = viewDataBinding.fragmentCreationAddressEtFlat.text.toString().trim(),
                entrance = viewDataBinding.fragmentCreationAddressEtEntrance.text.toString().trim(),
                intercom = viewDataBinding.fragmentCreationAddressEtIntercom.text.toString().trim(),
                floor = viewDataBinding.fragmentCreationAddressEtFloor.text.toString().trim(),
            )
        )
    }

    override fun goToCart(view: View) {
        findNavController().navigate(CreationAddressFragmentDirections.creationAddressBackToCartFragment())
    }

    override fun goToCreationOrder() {
        (activity as MainActivity).showMessage(requireContext().getString(R.string.msg_creation_address_created_address))
        findNavController().navigate(CreationAddressFragmentDirections.backToCreationOrder())
    }
}