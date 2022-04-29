package com.bunbeauty.papakarlo.common

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.extensions.showSnackbar
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.Flow
import org.koin.android.ext.android.inject

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    abstract val viewBinding: ViewBinding
    abstract val viewModel: BaseViewModel
    val resourcesProvider: IResourcesProvider by inject()

    var isBackPressedOverridden = false
    var onBackPressedCallback: OnBackPressedCallback? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.invalidateOptionsMenu()

        val colorPrimary = resourcesProvider.getColorByAttr(R.attr.colorPrimary)
        val colorOnPrimary = resourcesProvider.getColorByAttr(R.attr.colorOnPrimary)
        val colorError = resourcesProvider.getColorByAttr(R.attr.colorError)
        val colorOnError = resourcesProvider.getColorByAttr(R.attr.colorOnError)
        viewModel.message.startedLaunch { message ->
            viewBinding.root.showSnackbar(
                message.message,
                colorOnPrimary,
                colorPrimary,
                message.isTop
            )
        }
        viewModel.error.startedLaunch { error ->
            viewBinding.root.showSnackbar(
                error.message,
                colorOnError,
                colorError,
                error.isTop
            )
        }
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

    protected fun hideKeyboard() {
        activity?.currentFocus?.let { currentFocus ->
            val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

    protected inline fun <T> Flow<T>.startedLaunch(crossinline block: suspend (T) -> Unit) {
        startedLaunch(viewLifecycleOwner, block)
    }
}