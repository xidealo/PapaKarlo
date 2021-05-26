package com.bunbeauty.papakarlo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.getBinding
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
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
    }

    fun showMessage(message: String) {
        val snack = dialog?.window?.decorView?.let {
            Snackbar.make(
                it, // important part
                message,
                Snackbar.LENGTH_SHORT
            )
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
        snack?.view?.findViewById<TextView>(R.id.snackbar_text)?.textAlignment =
            View.TEXT_ALIGNMENT_CENTER
        snack?.show()
    }
}