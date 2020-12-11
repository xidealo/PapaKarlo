package com.bunbeauty.papakarlo.ui.base

import android.content.Context
import androidx.core.content.ContextCompat
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.PartTopBarBinding

interface ITopBar {

     var topBarBinding: PartTopBarBinding

    fun setTitle(title: String) {
        topBarBinding.partTbTopBar.title = title
    }

    fun hideTitle() {
        topBarBinding.partTbTopBar.title = ""
    }

    // 1

    fun showBackArrow(context: Context) {
        topBarBinding.partTbTopBar.navigationIcon =
            ContextCompat.getDrawable(context, R.drawable.ic_back)
    }

}