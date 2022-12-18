package com.bunbeauty.papakarlo.common

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.coroutines.flow.Flow
import org.koin.android.ext.android.inject

abstract class BaseFragmentWithSharedViewModel(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    abstract val viewBinding: ViewBinding
    val resourcesProvider: IResourcesProvider by inject()

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

    protected fun overrideBackPressedCallback() {
        isBackPressedOverridden = true
    }

}
