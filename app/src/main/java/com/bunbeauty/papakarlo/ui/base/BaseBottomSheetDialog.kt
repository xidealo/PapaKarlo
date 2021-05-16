package com.bunbeauty.papakarlo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

abstract class BaseBottomSheetDialog<B : ViewDataBinding> :
    BottomSheetDialogFragment() {

    abstract var layoutId: Int
    lateinit var viewDataBinding: B

    abstract val viewModel: BaseViewModel

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

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
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
    }

    fun <T> Flow<T>.launchWhenStarted(lifecycleCoroutineScope: LifecycleCoroutineScope) {
        lifecycleCoroutineScope.launchWhenStarted {
            this@launchWhenStarted.collect()
        }
    }

}