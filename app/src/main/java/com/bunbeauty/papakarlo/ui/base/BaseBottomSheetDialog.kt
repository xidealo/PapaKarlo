package com.bunbeauty.papakarlo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseBottomSheetDialog<B : ViewDataBinding> :
    BottomSheetDialogFragment() {

    abstract var layoutId: Int
    lateinit var viewDataBinding: B

    abstract val viewModel: BaseViewModel

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var iResourcesProvider: IResourcesProvider

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

    fun showMessage(message: String) {
        val snack = dialog?.window?.decorView?.let {
            Snackbar.make(
                it, // important part
                message,
                Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
        snack?.view?.findViewById<TextView>(R.id.snackbar_text)?.textAlignment =
            View.TEXT_ALIGNMENT_CENTER
        snack?.show()
    }

    fun <T> Flow<T>.startedLaunch(lifecycle: Lifecycle){
        lifecycle.coroutineScope.launch {
            this@startedLaunch
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect()
        }
    }

}