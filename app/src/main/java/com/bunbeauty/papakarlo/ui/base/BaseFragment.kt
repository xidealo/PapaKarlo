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
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    abstract var layoutId: Int
    lateinit var viewDataBinding: B
    abstract val viewModel: BaseViewModel

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var iResourcesProvider: IResourcesProvider

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
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()

        lifecycleScope.launchWhenStarted {
            viewModel.messageSharedFlow.onEach {
                showMessage(it)
            }.startedLaunch(lifecycle)
        }
        lifecycleScope.launchWhenStarted {
            viewModel.errorSharedFlow.onEach {
                showError(it)
            }.startedLaunch(lifecycle)
        }
    }

    protected fun <T> subscribe(liveData: LiveData<T>, observer: (T) -> Unit) {
        liveData.observe(viewLifecycleOwner, observer::invoke)
    }

    fun <T> Flow<T>.startedLaunch(lifecycle: Lifecycle){
        lifecycle.coroutineScope.launch {
            this@startedLaunch
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect()
        }
    }

    fun <T> Flow<T>.resumedLaunch(lifecycle: Lifecycle){
        lifecycle.coroutineScope.launch {
            this@resumedLaunch
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect()
        }
    }

    fun showMessage(message: String) {
        val snack = Snackbar.make(viewDataBinding.root, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snack.view.findViewById<TextView>(R.id.snackbar_text).textAlignment =
            View.TEXT_ALIGNMENT_CENTER
        snack.show()
    }

    fun showError(error: String) {
        val snack = Snackbar.make(viewDataBinding.root, error, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.errorColor))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snack.view.findViewById<TextView>(R.id.snackbar_text).textAlignment =
            View.TEXT_ALIGNMENT_CENTER
        snack.show()
    }
}