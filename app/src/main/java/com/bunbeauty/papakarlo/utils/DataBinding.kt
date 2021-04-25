package com.bunbeauty.papakarlo.utils

import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bunbeauty.papakarlo.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

object DataBinding {

    @JvmStatic
    @BindingAdapter("bind:image")
    fun setImage(imageView: ImageView, link: String?) {
        if (!link.isNullOrEmpty()) {
            Picasso.get()
                .load(link)
                .fit()
                .placeholder(R.drawable.default_product)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("bind:enabled")
    fun setEnabled(materialCardView: MaterialCardView, isEnabled: Boolean) {
        materialCardView.isEnabled = isEnabled
    }

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