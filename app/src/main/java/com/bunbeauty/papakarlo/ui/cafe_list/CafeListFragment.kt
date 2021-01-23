package com.bunbeauty.papakarlo.ui.cafe_list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity
import com.bunbeauty.papakarlo.databinding.FragmentCafeListBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.TopBarFragment
import com.bunbeauty.papakarlo.utils.contact_info.ICafeHelper
import com.bunbeauty.papakarlo.utils.uri.IUriHelper
import com.bunbeauty.papakarlo.view_model.ContactsViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class CafeListFragment : TopBarFragment<FragmentCafeListBinding, ContactsViewModel>(),
    CafeListNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_cafe_list
    override var viewModelClass = ContactsViewModel::class.java
    override lateinit var title: String

    @Inject
    lateinit var cafeHelper: ICafeHelper

    @Inject
    lateinit var uriHelper: IUriHelper

    @Inject
    lateinit var cafeAdapter: CafeAdapter

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = resources.getString(R.string.title_cafe_list)

        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.fragmentCafeListRvCafeList.adapter = cafeAdapter

        viewModel.navigator = WeakReference(this)
        viewModel.refreshCafeList()
        viewModel.cafeListLiveData.observe(viewLifecycleOwner) { cafeList ->
            cafeAdapter.submitList(cafeList)
        }
    }

    private fun goToAddress(cafeEntity: CafeEntity) {
        val uri = Uri.parse("http://maps.google.com/maps?daddr=" + cafeEntity.coordinate.latitude + "," + cafeEntity.coordinate.longitude)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun goToPhone() {
        val intent = Intent(Intent.ACTION_DIAL)
        //intent.data = Uri.parse("tel:${viewModel.contactInfoLiveData.value!!.phone}")
        startActivity(intent)
    }
}