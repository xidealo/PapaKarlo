package com.example.papakarlo.ui.activity.base

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.papakarlo.PapaKarloApplication
import com.example.papakarlo.R
import com.example.papakarlo.di.components.ViewModelComponent
import com.example.papakarlo.view_model.base.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    abstract val layoutId: Int
    abstract val dataBindingVariable: Int
    abstract val viewModel: BaseViewModel

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

    fun showError(messageError: String, layout: View) {
        val snack = Snackbar.make(layout, messageError, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.red))
            .setTextColor(ContextCompat.getColor(this, R.color.white))
            .setActionTextColor(ContextCompat.getColor(this, R.color.white))
        snack.view.findViewById<TextView>(R.id.snackbar_text).textAlignment =
            View.TEXT_ALIGNMENT_CENTER
        snack.show()
    }

    fun showMessage(message: String, layout: View) {
        val snack = Snackbar.make(layout, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.colorPrimary))
            .setTextColor(ContextCompat.getColor(this, R.color.white))
            .setActionTextColor(ContextCompat.getColor(this, R.color.white))
        snack.view.findViewById<TextView>(R.id.snackbar_text).textAlignment =
            View.TEXT_ALIGNMENT_CENTER
        snack.show()
    }

    fun onFragmentAttached() {

    }

    fun onFragmentDetached(tag: String?) {

    }
}