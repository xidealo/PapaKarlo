package com.bunbeauty.papakarlo.common.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.BottomSheetComposeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class ComposeBottomSheet<T : Any> : BottomSheetDialogFragment() {

    protected var callback: Callback<T>? = null

    protected val binding by viewBinding(BottomSheetComposeBinding::bind)
    protected val behavior by lazy { (dialog as BottomSheetDialog).behavior }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_compose, container, true)
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogStyle
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

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
