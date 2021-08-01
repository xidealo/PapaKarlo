package com.bunbeauty.papakarlo.utils

import android.widget.ArrayAdapter
import androidx.databinding.BindingAdapter
import com.bunbeauty.papakarlo.R
import com.google.android.material.textfield.MaterialAutoCompleteTextView

object DataBinding {

    @JvmStatic
    @BindingAdapter("bind:setAdapter")
    fun setAdapter(autoCompleteTextView: MaterialAutoCompleteTextView, data: List<String>?) {
        if (data != null) {
            val adapter = ArrayAdapter(
                autoCompleteTextView.context,
                R.layout.support_simple_spinner_dropdown_item,
                data
            )
            autoCompleteTextView.setAdapter(adapter)
        }
    }
}