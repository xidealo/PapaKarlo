package com.bunbeauty.papakarlo.ui.contacts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.ContactInfo
import com.bunbeauty.papakarlo.databinding.FragmentContactsBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.TopBarFragment
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.utils.contact_info.IContactInfoHelper
import com.bunbeauty.papakarlo.utils.uri.IUriHelper
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

    @Inject
    lateinit var uriHelper: IUriHelper

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigator = WeakReference(this)
        title = resources.getString(R.string.title_contacts)

        viewModel.contactInfoLiveData.observe(viewLifecycleOwner) { contactInfo ->
            viewDataBinding.fragmentContactsTvAddress.text = contactInfo.address
            viewDataBinding.fragmentContactsTvWorkTime.text =
                contactInfoHelper.getWorkTimeString(contactInfo)
            viewDataBinding.fragmentContactsTvWorkPhone.text = contactInfo.phone
        }
        viewModel.getContactsInfo()

        viewDataBinding.fragmentContactsMcPhone.setOnClickListener {
            goToPhone()
        }
        viewDataBinding.fragmentContactsMcAddress.setOnClickListener {
            goToAddress(viewModel.contactInfoLiveData.value!!)
        }
        viewDataBinding.fragmentContactsMcWorkTime.setOnClickListener {
            (activity as MainActivity).showMessage(viewModel.getIsClosedMessage())
        }
    }

    private fun goToAddress(contactInfo: ContactInfo) {
        val uri = Uri.parse("http://maps.google.com/maps?daddr=" + contactInfo.latitude + "," + contactInfo.longitude)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun goToPhone() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${viewModel.contactInfoLiveData.value!!.phone}")
        startActivity(intent)
    }
}