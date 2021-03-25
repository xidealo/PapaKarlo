package com.bunbeauty.papakarlo.utils

import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.common.extensions.gone
import com.bunbeauty.common.extensions.visible
import com.bunbeauty.data.model.BaseModel
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.ui.adapter.BaseAdapter
import com.bunbeauty.papakarlo.ui.view.ProgressButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

object DataBinding {

    @JvmStatic
    @BindingAdapter("android:text")
    fun setInt(textView: TextView, intValue: Int) {
        textView.text = intValue.toString()
    }

    @JvmStatic
    @BindingAdapter("bind:isLoading")
    fun setLoading(progressButton: ProgressButton, isLoading: Boolean) {
        if (isLoading) {
            progressButton.showLoading()
        } else {
            progressButton.hideLoading()
        }
    }

    @JvmStatic
    @BindingAdapter("bind:items")
    fun <T : BaseModel> setListItems(
        recyclerView: RecyclerView,
        items: List<T>?
    ) {
        if (items != null && recyclerView.adapter != null) {
            (recyclerView.adapter as BaseAdapter<out RecyclerView.ViewHolder, T>).setItemList(items)
        }
    }

    @JvmStatic
    @BindingAdapter("bind:removeItem")
    fun <T : BaseModel> removeItem(recyclerView: RecyclerView, item: T?) {
        if (item != null && recyclerView.adapter != null) {
            (recyclerView.adapter as BaseAdapter<out RecyclerView.ViewHolder, T>).removeItem(item)
        }
    }

    @JvmStatic
    @BindingAdapter("bind:addItem")
    fun <T : BaseModel> addItem(recyclerView: RecyclerView, item: T?) {
        if (item != null && recyclerView.adapter != null) {
            (recyclerView.adapter as BaseAdapter<out RecyclerView.ViewHolder, T>).addItem(item)
        }
    }

    @JvmStatic
    @BindingAdapter("bind:isLoading")
    fun setLoading(progressBar: ProgressBar, isLoading: Boolean) {
        //TODO(change to toggleVisibility)
        if (isLoading) {
            progressBar.visible()
        } else {
            progressBar.gone()
        }
    }

    @JvmStatic
    @BindingAdapter("bind:leftIcon")
    fun setLeftIcon(textView: TextView, iconId: Int) {
        textView.setCompoundDrawablesWithIntrinsicBounds(iconId, 0, 0, 0)
    }

    @JvmStatic
    @BindingAdapter("bind:image")
    fun setImage(imageView: ImageView, link: String?) {
        if (!link.isNullOrEmpty()) {
            Picasso.get()
                .load(link)
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