package com.bunbeauty.papakarlo.common.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class ComposeBottomSheet<T : Any> : BottomSheetDialogFragment(R.layout.bottom_sheet_compose) {

    protected var callback: Callback<T>? = null

    private val binding by viewBinding(BottomSheetComposeBinding::bind)
    protected val behavior by lazy { (dialog as BottomSheetDialog).behavior }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogStyle
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        binding.root.setContentWithTheme {
            Content()
        }
    }

    @Composable
    abstract fun Content()

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        callback?.onResult(null)
    }

    protected fun toggleDraggable(isDraggable: Boolean) {
        behavior.isDraggable = isDraggable
    }

    protected interface Callback<T> {
        fun onResult(result: T?)
    }
}
