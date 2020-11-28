package com.example.papakarlo.ui.activity.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.papakarlo.PapaKarloApplication
import com.example.papakarlo.di.components.ViewModelComponent
import com.example.papakarlo.view_model.base.BaseViewModel
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

    abstract var layoutId: Int
    abstract var viewModelVariable: Int
    abstract var viewModelClass: Class<V>

    lateinit var viewDataBinding: T
    var activity: BaseActivity<*>? = null

    lateinit var viewModel: V

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val viewModelComponent = (requireActivity().application as PapaKarloApplication).appComponent
            .getViewModelComponent()
            .create(this)
        inject(viewModelComponent)

        viewModel = ViewModelProvider(this, modelFactory).get(viewModelClass)

        if (context is BaseActivity<*>) {
            activity = context
        }
    }

    abstract fun inject(viewModelComponent: ViewModelComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(false)
    }

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

        viewDataBinding.setVariable(viewModelVariable, viewModel)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
    }

    override fun onDetach() {
        activity = null
        super.onDetach()
    }
}