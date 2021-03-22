package com.bunbeauty.papakarlo.ui.cafe_list


import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.FragmentCafeListBinding
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.ui.base.BarsFragment
import com.bunbeauty.domain.uri.IUriHelper
import com.bunbeauty.papakarlo.presentation.CafeListViewModel
import javax.inject.Inject

class CafeListFragment : BarsFragment<FragmentCafeListBinding, CafeListViewModel>() {

    @Inject
    lateinit var uriHelper: IUriHelper

    @Inject
    lateinit var cafeAdapter: CafeAdapter

    override val isBottomBarVisible = true

    override fun inject(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cafeAdapter.cafeListViewModel = viewModel
        viewDataBinding.fragmentCafeListRvCafeList.adapter = cafeAdapter

        viewModel.cafeListLiveData.observe(viewLifecycleOwner) { cafeList ->
            cafeAdapter.submitList(cafeList)
        }

        viewDataBinding.fragmentCafeListBtnCardNumber.setOnClickListener {
            copyToBuffer("card number", requireContext().getString(R.string.pay_data_card_number))
            showMessage(requireContext().getString(R.string.msg_cafe_list_copy_card_number_copied))
        }
        viewDataBinding.fragmentCafeListBtnPhoneNumber.setOnClickListener {
            copyToBuffer("phone number",  requireContext().getString(R.string.pay_data_phone_number))
            showMessage(requireContext().getString(R.string.msg_cafe_list_copy_phone_number_copied))
        }
    }

    private fun copyToBuffer(label: String, data: String) {
        val clipboard = getSystemService(requireContext(), ClipboardManager::class.java)
        val clip = ClipData.newPlainText(label, data)
        clipboard?.setPrimaryClip(clip)
    }
}