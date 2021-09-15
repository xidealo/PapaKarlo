package com.bunbeauty.papakarlo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.getBinding
import com.bunbeauty.papakarlo.extensions.showSnackbar
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class BaseBottomSheetDialog<B : ViewDataBinding> : BottomSheetDialogFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    abstract val viewModel: BaseViewModel

    private var mutableViewDataBinding: B? = null
    val viewDataBinding: B
        get() = checkNotNull(mutableViewDataBinding)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        setStyle(STYLE_NORMAL, R.style.BottomSheetTheme)

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

        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()

        viewModel.bundle = requireArguments()

        viewModel.message.onEach { message ->
            viewDataBinding.root.showSnackbar(message, R.color.white, R.color.colorPrimary)
        }.startedLaunch()
        viewModel.error.onEach { error ->
            viewDataBinding.root.showSnackbar(error, R.color.white, R.color.errorColor)
        }.startedLaunch()
    }

    protected fun Flow<*>.startedLaunch() {
        startedLaunch(viewLifecycleOwner)
    }
}