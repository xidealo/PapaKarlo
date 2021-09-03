package com.bunbeauty.papakarlo.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.presentation.base.CartViewModel
import kotlinx.coroutines.flow.onEach

/**
 * Base class for each fragment which has top bar cart
 */
abstract class TopbarCartFragment<T : ViewDataBinding> : BaseFragment<T>() {

    override val viewModel: CartViewModel by viewModels { modelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cartCost.onEach { cartCost ->
            (activity as? IToolbar)?.setCartText(cartCost)
        }.startedLaunch(viewLifecycleOwner)

        viewModel.cartProductCount.onEach { cartProductCount ->
            (activity as? IToolbar)?.setCartProductCount(cartProductCount)
        }.startedLaunch(viewLifecycleOwner)
    }
}