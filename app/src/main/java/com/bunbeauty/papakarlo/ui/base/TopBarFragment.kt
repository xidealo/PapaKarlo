package com.bunbeauty.papakarlo.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel

abstract class TopBarFragment<T : ViewDataBinding, V : BaseViewModel> : BaseFragment<T, V>() {

    abstract var title: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.setTopBarTitle(title)
    }
}