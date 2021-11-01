package com.bunbeauty.papakarlo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.*
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    abstract val viewModel: BaseViewModel

    protected val textInputMap = HashMap<String, TextInputLayout>()

    private var mutableViewDataBinding: B? = null
    val viewDataBinding: B
        get() = checkNotNull(mutableViewDataBinding)

    open val isToolbarVisible = true
    open val isLogoVisible = false
    open val isCartVisible = false
    open val isBottomBarVisible = false

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val viewModelComponent =
            (requireActivity().application as PapaKarloApplication).appComponent
                .getViewModelComponent()
                .create(this)
        inject(viewModelComponent)
    }

    abstract fun inject(viewModelComponent: ViewModelComponent)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mutableViewDataBinding = getBinding(inflater, container)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? IBottomNavigationBar)?.setupBottomNavigationBar(isBottomBarVisible)
        (activity as? IToolbar)?.setToolbarConfiguration(
            isToolbarVisible,
            isLogoVisible,
            isCartVisible
        )
        activity?.invalidateOptionsMenu()

        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()

        viewModel.message.onEach { message ->
            viewDataBinding.root.showSnackbar(
                message,
                R.color.messageTextColor,
                R.color.colorPrimary
            )
        }.startedLaunch()
        viewModel.error.onEach { error ->
            viewDataBinding.root.showSnackbar(
                error,
                R.color.messageTextColor,
                R.color.errorBackgroundColor
            )
        }.startedLaunch()
        viewModel.fieldError.onEach { fieldError ->
            textInputMap.values.forEach { textInput ->
                textInput.clearErrorFocus()
            }
            textInputMap[fieldError.key]?.setErrorFocus(fieldError.message)
        }.startedLaunch()
    }

    protected fun hideKeyboard() {
        activity?.currentFocus?.let { currentFocus ->
            val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mutableViewDataBinding = null
    }

    protected fun Flow<*>.startedLaunch() {
        startedLaunch(viewLifecycleOwner)
    }
}