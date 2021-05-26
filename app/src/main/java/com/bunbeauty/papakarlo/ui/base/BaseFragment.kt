package com.bunbeauty.papakarlo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.getBinding
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    abstract val viewModel: BaseViewModel

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

        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()

        viewModel.message.onEach { message ->
            showSnackbar(message, R.color.white, R.color.colorPrimary)
        }.startedLaunch(viewLifecycleOwner)
        viewModel.error.onEach { error ->
            showSnackbar(error, R.color.white, R.color.errorColor)
        }.startedLaunch(viewLifecycleOwner)
    }

    @Deprecated("use flow")
    protected fun <T> subscribe(liveData: LiveData<T>, observer: (T) -> Unit) {
        liveData.observe(viewLifecycleOwner, observer::invoke)
    }

    private fun showSnackbar(errorMessage: String, textColorId: Int, backgroundColorId: Int) {
        val snack = Snackbar.make(viewDataBinding.root, errorMessage, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), backgroundColorId))
            .setTextColor(ContextCompat.getColor(requireContext(), textColorId))
            .setActionTextColor(ContextCompat.getColor(requireContext(), textColorId))
        val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
            gravity = Gravity.TOP
            setMargins(16, 16, 16, 0)
        }
        with(snack) {
            view.layoutParams = layoutParams
            view.findViewById<TextView>(R.id.snackbar_text).textAlignment = TEXT_ALIGNMENT_CENTER
            show()
        }
    }
}