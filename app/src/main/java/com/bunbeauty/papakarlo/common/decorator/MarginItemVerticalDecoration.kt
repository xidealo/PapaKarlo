package com.bunbeauty.papakarlo.common.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import javax.inject.Inject

class MarginItemVerticalDecoration @Inject constructor(private val resourcesProvider: IResourcesProvider) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val smallMargin = resourcesProvider.getDimensionPixelOffset(R.dimen.small_margin)
        val mediumMargin = resourcesProvider.getDimensionPixelOffset(R.dimen.medium_margin)

        val currentPosition = parent.getChildAdapterPosition(view).takeIf { position ->
            position != RecyclerView.NO_POSITION
        } ?: return
        val isFistItem = currentPosition == 0
        val isLastItem = currentPosition == state.itemCount - 1

        outRect.run {
            if (isFistItem) {
                top = mediumMargin
            }
            bottom = if (isLastItem) {
                mediumMargin
            } else {
                smallMargin
            }
            left = mediumMargin
            right = mediumMargin
        }
    }
}