package com.bunbeauty.papakarlo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseBottomSheetDialog<B : ViewDataBinding, VM : BaseViewModel> :
    BottomSheetDialogFragment() {

    abstract var layoutId: Int
    abstract var viewModelVariable: Int

    private var _viewDataBinding: B? = null
    protected val viewDataBinding get() = _viewDataBinding!!
    lateinit var viewModel: VM

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

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
        viewModel = ViewModelProvider(this, modelFactory).get(getViewModelClass())
        _viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.setVariable(viewModelVariable, viewModel)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass() =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>


    @Suppress("UNCHECKED_CAST")
    private fun getViewBindingClass() =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<B>

    override fun onDestroyView() {
        super.onDestroyView()
        _viewDataBinding = null
    }
}