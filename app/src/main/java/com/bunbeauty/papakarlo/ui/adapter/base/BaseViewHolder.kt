package com.bunbeauty.papakarlo.ui.adapter.base

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import com.bunbeauty.domain.model.BaseItem
import com.bunbeauty.papakarlo.R
import java.lang.ref.SoftReference

abstract class BaseViewHolder<I : BaseItem>(
    val binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: I

    open fun onBind(item: I) {
        this.item = item
    }

    open fun onBind(item: I, payloads: List<Any>) {
        this.item = item
    }

    inline fun ImageView.setPhoto(
        photoReference: SoftReference<Drawable?>,
        link: String,
        crossinline onPhotoLoaded: (Drawable) -> Unit
    ) {
        val photo = photoReference.get()
        if (photo == null) {
            load(link) {
                target { drawable ->
                    setImageDrawable(drawable)
                    onPhotoLoaded(drawable)
                }
                placeholder(R.drawable.placeholder)
            }
        } else {
            setImageDrawable(photo)
        }
    }
}