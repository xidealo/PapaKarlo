package com.bunbeauty.papakarlo.common

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseFragmentWithSharedViewModel(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    abstract val viewBinding: ViewBinding

    var isBackPressedOverridden = false
    var onBackPressedCallback: OnBackPressedCallback? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.invalidateOptionsMenu()
    }

    override fun onStart() {
        super.onStart()

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

    fun Fragment.launchOnLifecycle(
        state: Lifecycle.State = Lifecycle.State.STARTED,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state, block)
        }
    }

    protected fun overrideBackPressedCallback() {
        isBackPressedOverridden = true
    }
}
