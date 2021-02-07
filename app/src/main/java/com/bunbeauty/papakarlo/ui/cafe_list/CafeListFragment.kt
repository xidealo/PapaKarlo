package com.bunbeauty.papakarlo.ui.cafe_list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.BR
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentCafeListBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.TopBarFragment
import com.bunbeauty.papakarlo.ui.cafe_list.CafeListFragmentDirections.toCafeOptionsBottomSheet
import com.bunbeauty.papakarlo.utils.uri.IUriHelper
import com.bunbeauty.papakarlo.view_model.CafeListViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class CafeListFragment : TopBarFragment<FragmentCafeListBinding, CafeListViewModel>(),
    CafeListNavigator {

    override var viewModelVariable: Int = BR.viewModel
    override var layoutId: Int = R.layout.fragment_cafe_list
    override var viewModelClass = CafeListViewModel::class.java
    override lateinit var title: String

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

        cafeAdapter.cafeListViewModel = viewModel
        viewDataBinding.fragmentCafeListRvCafeList.adapter = cafeAdapter

        viewModel.navigator = WeakReference(this)
        viewModel.cafeListLiveData.observe(viewLifecycleOwner) { cafeList ->
            cafeAdapter.submitList(cafeList)
        }
    }

    override fun goToCafeOptions(cafeId: String) {
        findNavController().navigate(toCafeOptionsBottomSheet(cafeId))
    }
}