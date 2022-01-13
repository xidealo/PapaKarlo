package com.bunbeauty.papakarlo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.extensions.showSnackbar
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

abstract class BaseBottomSheet(@LayoutRes private val layoutId: Int) : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    abstract val viewModel: BaseViewModel
    abstract val viewBinding: ViewBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val viewModelComponent =
            (requireActivity().application as PapaKarloApplication).appComponent
                .getViewModelComponent()
                .create(this)
        inject(viewModelComponent)
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetTheme
    }

    abstract fun inject(viewModelComponent: ViewModelComponent)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bundle = arguments

        val colorPrimary = resourcesProvider.getColorByAttr(R.attr.colorPrimary)
        val colorOnPrimary = resourcesProvider.getColorByAttr(R.attr.colorOnPrimary)
        val colorError = resourcesProvider.getColorByAttr(R.attr.colorError)
        val colorOnError = resourcesProvider.getColorByAttr(R.attr.colorOnError)
        viewModel.message.onEach { message ->
            viewBinding.root.showSnackbar(
                message.message,
                colorOnPrimary,
                colorPrimary,
                message.isTop
            )
        }.startedLaunch()
        viewModel.error.onEach { error ->
            viewBinding.root.showSnackbar(error.message, colorOnError, colorError, error.isTop)
        }.startedLaunch()
    }

    protected fun Flow<*>.startedLaunch() {
        startedLaunch(viewLifecycleOwner)
    }
}