package com.bunbeauty.papakarlo.extensions

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle

inline fun <reified T> SavedStateHandle.navArg(key: String): T? {
    return when {
        Parcelable::class.java.isAssignableFrom(T::class.java) -> {
            get<Parcelable>(key) as T
        }
        T::class.java == Boolean::class.java -> {
            get<Boolean>(key) as T
        }
        T::class.java == String::class.java -> {
            get<String>(key) as T
        }
        else -> null
    }
}