package com.bunbeauty.papakarlo.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bunbeauty.papakarlo.generated.callback.OnClickListener
import com.bunbeauty.papakarlo.ui.main.MainActivity
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlin.reflect.KFunction1

abstract class TopBarFragment<T : ViewDataBinding, V : BaseViewModel<*>> : BaseFragment<T, V>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.setCartClickListener(::goToCart)
    }

    abstract fun goToCart(view: View)
}