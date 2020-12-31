package com.bunbeauty.papakarlo.ui.contacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentContactsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.TopBarFragment
import com.bunbeauty.papakarlo.utils.contact_info.ContactInfoHelper
import com.bunbeauty.papakarlo.utils.contact_info.IContactInfoHelper
import com.bunbeauty.papakarlo.view_model.ContactsViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ContactsFragment : TopBarFragment<FragmentContactsBinding, ContactsViewModel>(),
    ContactsNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_contacts
    override var viewModelClass = ContactsViewModel::class.java

    @Inject
    lateinit var contactInfoHelper: IContactInfoHelper

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = WeakReference(this)
        title = resources.getString(R.string.title_contacts)

        viewModel.contactInfoLiveData.observe(viewLifecycleOwner) { contactInfo ->
            Log.d("test", "contactInfo")
            viewDataBinding.fragmentContactsTvAddress.text = contactInfo.address
            viewDataBinding.fragmentContactsTvWorkTime.text = contactInfoHelper.getWorkTimeString(contactInfo)
            viewDataBinding.fragmentContactsTvWorkPhone.text = contactInfo.phone
        }
        viewModel.getContactsInfo()
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
}