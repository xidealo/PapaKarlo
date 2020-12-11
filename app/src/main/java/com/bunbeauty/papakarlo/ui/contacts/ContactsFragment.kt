package com.bunbeauty.papakarlo.ui.contacts

import android.os.Bundle
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentContactsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.view_model.ContactsViewModel

class ContactsFragment : BaseFragment<FragmentContactsBinding, ContactsViewModel>(),
    ContactsNavigator {
    override var title: String = "Контакты"
    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_contacts
    override var viewModelClass = ContactsViewModel::class.java
    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    companion object {
        const val TAG = "ContactsFragment"

        @JvmStatic
        fun newInstance() =
            ContactsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}