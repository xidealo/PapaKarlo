package com.bunbeauty.papakarlo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.bunbeauty.domain.IMessageShowable
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import com.bunbeauty.papakarlo.presentation.base.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment(),
    IMessageShowable {

    private var _viewDataBinding: B? = null
    protected val viewDataBinding get() = _viewDataBinding!!
    protected lateinit var viewModel: VM

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

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)

        viewModel = ViewModelProvider(this, modelFactory).get(getViewModelClass())
        viewModel.messageShowable = WeakReference(this)

        val viewBindingClass = getViewBindingClass()
        val inflateMethod = viewBindingClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java,
        )
        _viewDataBinding = inflateMethod.invoke(viewBindingClass, inflater, container, false) as B

        return viewDataBinding.root
    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewBindingClass() =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<B>

    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass() =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>

    protected fun <T> subscribe(liveData: LiveData<T>, observer: (T) -> Unit) {
        liveData.observe(viewLifecycleOwner, observer::invoke)
    }

    override fun showMessage(message: String) {
        val snack = Snackbar.make(viewDataBinding.root, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snack.view.findViewById<TextView>(R.id.snackbar_text).textAlignment =
            View.TEXT_ALIGNMENT_CENTER
        snack.show()
    }

    override fun showError(error: String) {
        val snack = Snackbar.make(viewDataBinding.root, error, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.errorColor))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        snack.view.findViewById<TextView>(R.id.snackbar_text).textAlignment =
            View.TEXT_ALIGNMENT_CENTER
        snack.show()
    }
}