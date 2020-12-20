package com.bunbeauty.papakarlo.ui.base

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    abstract val layoutId: Int
    abstract val dataBindingVariable: Int
    abstract val viewModel: BaseViewModel<*>

    lateinit var viewDataBinding: T

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModelComponent = (application as PapaKarloApplication).appComponent
            .getViewModelComponent()
            .create(this)
        inject(viewModelComponent)

        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        viewDataBinding.setVariable(dataBindingVariable, viewModel)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()
    }

    abstract fun inject(viewModelComponent: ViewModelComponent)
}