package com.bunbeauty.papakarlo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.clearErrorFocus
import com.bunbeauty.papakarlo.extensions.setErrorFocus
import com.bunbeauty.papakarlo.extensions.showSnackbar
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    abstract val viewBinding: ViewBinding
    abstract val viewModel: BaseViewModel

    protected val textInputMap = HashMap<String, TextInputLayout>()

    var isBackPressedOverridden = false
    var onBackPressedCallback: OnBackPressedCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val viewModelComponent =
            (requireActivity().application as PapaKarloApplication).appComponent
                .getViewModelComponent()
                .create(this)
        inject(viewModelComponent)
    }

    abstract fun inject(viewModelComponent: ViewModelComponent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.invalidateOptionsMenu()

        val colorPrimary = resourcesProvider.getColorByAttr(R.attr.colorPrimary)
        val colorOnPrimary = resourcesProvider.getColorByAttr(R.attr.colorOnPrimary)
        val colorError = resourcesProvider.getColorByAttr(R.attr.colorError)
        val colorOnError = resourcesProvider.getColorByAttr(R.attr.colorOnError)
        viewModel.message.onEach { message ->
            viewBinding.root.showSnackbar(message.message, colorOnPrimary, colorPrimary, false)
        }.startedLaunch()
        viewModel.error.onEach { error ->
            viewBinding.root.showSnackbar(error.message, colorOnError, colorError, true)
        }.startedLaunch()
        viewModel.fieldError.onEach { fieldError ->
            textInputMap.values.forEach { textInput ->
                textInput.clearErrorFocus()
            }
            textInputMap[fieldError.key]?.setErrorFocus(fieldError.message)
        }.startedLaunch()
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

    protected fun Flow<*>.startedLaunch() {
        startedLaunch(viewLifecycleOwner)
    }
}