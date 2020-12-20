package com.bunbeauty.papakarlo.ui.contacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentContactsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BaseFragment
import com.bunbeauty.papakarlo.view_model.ContactsViewModel
import java.lang.ref.WeakReference


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.navigator = WeakReference(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun goToAddress(longitude: Double, latitude: Double) {
        val label = "Папа Карло"
        val uriBegin = "geo:$longitude,$latitude"
        val query = "$longitude,$latitude($label)"
        val encodedQuery = Uri.encode(query)
        val uriString = "$uriBegin?q=$encodedQuery&z=16"
        val uri = Uri.parse(uriString)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun goToTime() {

    }

    override fun goToPhone(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        startActivity(intent)
    }

    companion object {
        const val TAG = "ContactsFragment"
    }
}