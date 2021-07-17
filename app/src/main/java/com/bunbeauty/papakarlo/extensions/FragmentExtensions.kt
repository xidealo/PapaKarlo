package com.bunbeauty.papakarlo.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
internal fun <B : ViewDataBinding> Fragment.getBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
): B {
    val bindingClass: Class<B> =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<B>
    val inflateMethod = bindingClass.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java,
    )
    return inflateMethod.invoke(bindingClass, inflater, container, false) as B
}