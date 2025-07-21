package com.bunbeauty.papakarlo.common

import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bunbeauty.papakarlo.common.viewmodel.BaseViewModel

@Deprecated("Use BaseFragmentWithSharedViewModel")
abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    abstract val viewBinding: ViewBinding
    abstract val viewModel: BaseViewModel

    var isBackPressedOverridden = false
    var onBackPressedCallback: OnBackPressedCallback? = null

    override fun onStart() {
        super.onStart()

        // TODO move to extension function: fun Fragment.overrideBackPressedCallback()
        if (isBackPressedOverridden) {
            onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback {
                activity?.moveTaskToBack(true)
            }
        }
    }

    override fun onStop() {
        if (isBackPressedOverridden) {
            onBackPressedCallback?.remove()
        }

        super.onStop()
    }

    protected fun overrideBackPressedCallback() {
        isBackPressedOverridden = true
    }
}
