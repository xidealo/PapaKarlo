package com.bunbeauty.papakarlo.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.viewbinding.ViewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.view_model.BaseViewModel
import com.bunbeauty.papakarlo.extensions.showSnackbar
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.Flow
import org.koin.android.ext.android.inject

abstract class BaseBottomSheet(@LayoutRes private val layoutId: Int) : BottomSheetDialogFragment() {

    val resourcesProvider: IResourcesProvider by inject()

    abstract val viewModel: BaseViewModel
    abstract val viewBinding: ViewBinding

    override fun getTheme(): Int {
        return R.style.BottomSheetTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(layoutId, container, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colorPrimary = resourcesProvider.getColorByAttr(R.attr.colorPrimary)
        val colorOnPrimary = resourcesProvider.getColorByAttr(R.attr.colorOnPrimary)
        val colorError = resourcesProvider.getColorByAttr(R.attr.colorError)
        val colorOnError = resourcesProvider.getColorByAttr(R.attr.colorOnError)
        viewModel.message.startedLaunch { message ->
            viewBinding.root.showSnackbar(
                message.message,
                colorOnPrimary,
                colorPrimary,
                message.isTop
            )
        }
        viewModel.error.startedLaunch { error ->
            viewBinding.root.showSnackbar(error.message, colorOnError, colorError, error.isTop)
        }
    }

    @Composable
    protected fun DragHandle() {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Spacer(
                modifier = Modifier
                    .width(32.dp)
                    .height(4.dp)
                    .background(
                        color = FoodDeliveryTheme.colors.mainColors.onSurfaceVariant,
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    }

    protected inline fun <T> Flow<T>.startedLaunch(crossinline block: suspend (T) -> Unit) {
        startedLaunch(viewLifecycleOwner, block)
    }
}
