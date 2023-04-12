package com.bunbeauty.papakarlo.common.delegates

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bunbeauty.papakarlo.extensions.put
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class FragmentNullableArgumentDelegate<T : Any?> : ReadWriteProperty<Fragment, T?> {

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        val key = property.name
        return thisRef.arguments?.get(key) as? T
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T?) {
        val args = thisRef.arguments
            ?: Bundle().also(thisRef::setArguments)
        val key = property.name
        value?.let { args.put(key, it) } ?: args.remove(key)
    }
}

fun <T : Any> nullableArgument(): ReadWriteProperty<Fragment, T?> =
    FragmentNullableArgumentDelegate()
