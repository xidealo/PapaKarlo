package com.bunbeauty.papakarlo.ui.custom

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.papakarlo.R
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import javax.inject.Inject

class MarginItemDecoration @Inject constructor(private val resourcesProvider: IResourcesProvider) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val smallMargin = resourcesProvider.getDimension(R.dimen.small_margin)
        val mediumMargin = resourcesProvider.getDimension(R.dimen.medium_margin)
        outRect.run {
            if (isFistItem(view, parent)) {
                top = mediumMargin
            }
            bottom = if (isLastItem(view, parent, state)) {
                mediumMargin
            } else {
                smallMargin
            }
            left = mediumMargin
            right = mediumMargin
        }
    }

    private fun isFistItem(view: View, parent: RecyclerView):Boolean {
        return parent.getChildAdapterPosition(view) == 0
    }

    private fun isLastItem(view: View, parent: RecyclerView, state: RecyclerView.State):Boolean {
        return parent.getChildAdapterPosition(view) == state.itemCount - 1
    }
}