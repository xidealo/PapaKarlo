package com.bunbeauty.papakarlo.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet(@LayoutRes private val layoutId: Int) : BottomSheetDialogFragment() {

    abstract val viewModel: BaseViewModel
    abstract val viewBinding: ViewBinding

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogStyle
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, true)
    }
}
