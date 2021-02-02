package com.bunbeauty.papakarlo.ui.creation_address

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.Street
import com.bunbeauty.papakarlo.databinding.FragmentCreationAddressBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.CartClickableFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.view_model.CreationAddressViewModel
import java.lang.ref.WeakReference

class CreationAddressFragment :
    CartClickableFragment<FragmentCreationAddressBinding, CreationAddressViewModel>(),
    CreationAddressNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_creation_address
    override var viewModelClass = CreationAddressViewModel::class.java
    override lateinit var title: String

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_creation_address)
        viewModel.navigator = WeakReference(this)
        viewModel.getStreets()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun createAddress() {
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentCreationAddressEtStreet.text.toString(),
                true,
                50
            )
        ) {
            viewDataBinding.fragmentCreationAddressEtStreet.error =
                resources.getString(R.string.error_creation_address_street)
            viewDataBinding.fragmentCreationAddressEtStreet.requestFocus()
            return
        }
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentCreationAddressEtHouse.text.toString(),
                true,
                5
            )
        ) {
            viewDataBinding.fragmentCreationAddressEtHouse.error =
                resources.getString(R.string.error_creation_address_house)
            viewDataBinding.fragmentCreationAddressEtHouse.requestFocus()
            return
        }
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentCreationAddressEtFlat.text.toString(),
                false,
                5
            )
        ) {
            viewDataBinding.fragmentCreationAddressEtFlat.error =
                resources.getString(R.string.error_creation_address_flat)
            viewDataBinding.fragmentCreationAddressEtFlat.requestFocus()
            return
        }
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentCreationAddressEtEntrance.text.toString(),
                false,
                5
            )
        ) {
            viewDataBinding.fragmentCreationAddressEtEntrance.error =
                resources.getString(R.string.error_creation_address_entrance)
            viewDataBinding.fragmentCreationAddressEtEntrance.requestFocus()
            return
        }
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentCreationAddressEtIntercom.text.toString(),
                false,
                5
            )
        ) {
            viewDataBinding.fragmentCreationAddressEtIntercom.error =
                resources.getString(R.string.error_creation_address_intercom)
            viewDataBinding.fragmentCreationAddressEtIntercom.requestFocus()
            return
        }
        if (!viewModel.isCorrectFieldContent(
                viewDataBinding.fragmentCreationAddressEtFloor.text.toString(),
                false,
                5
            )
        ) {
            viewDataBinding.fragmentCreationAddressEtFloor.error =
                resources.getString(R.string.error_creation_address_floor)
            viewDataBinding.fragmentCreationAddressEtFloor.requestFocus()
            return
        }

        viewModel.creationAddress(
            Address(
                street  = viewModel.streets.find { it.name == viewDataBinding.fragmentCreationAddressEtStreet.text.toString()},
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