package com.bunbeauty.papakarlo.feature.cafe.cafe_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.compose.element.CircularProgressBar
import com.bunbeauty.papakarlo.compose.item.CafeItem
import com.bunbeauty.papakarlo.compose.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentCafeListBinding
import com.bunbeauty.papakarlo.extensions.compose
import org.koin.androidx.viewmodel.ext.android.viewModel

class CafeListFragment : BaseFragment(R.layout.fragment_cafe_list) {

    override val viewBinding by viewBinding(FragmentCafeListBinding::bind)
    override val viewModel: CafeListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewBinding.fragmentCafeListCvMain.compose {
            val cafeItemList by viewModel.cafeItemList.collectAsState()
            CafeListScreen(cafeItemList)
        }
    }

    @Composable
    private fun CafeListScreen(cafeItemList: List<CafeItemModel>?) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(FoodDeliveryTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            if (cafeItemList == null) {
                CircularProgressBar()
            } else {
                CafeListSuccessScreen(cafeItemList)
            }
        }
    }

    @Composable
    private fun CafeListSuccessScreen(cafeItemList: List<CafeItemModel>) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace)
        ) {
            itemsIndexed(cafeItemList) { i, cafeItem ->
                CafeItem(
                    modifier = Modifier.padding(
                        top = FoodDeliveryTheme.dimensions.getItemSpaceByIndex(i)
                    ),
                    cafeItem = cafeItem
                ) {
                    viewModel.onCafeCardClicked(cafeItem)
                }
            }
        }
    }

    @Preview
    @Composable
    private fun CafeListSuccessScreenPreview() {
        CafeListScreen(
            cafeItemList = listOf(
                CafeItemModel(
                    uuid = "",
                    address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                    workingHours = "9:00 - 22:00",
                    isOpenMessage = "Открыто",
                    cafeStatus = CafeStatus.OPEN,
                ),
                CafeItemModel(
                    uuid = "",
                    address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                    workingHours = "9:00 - 22:00",
                    isOpenMessage = "Открыто. Закроется через 30 минут",
                    cafeStatus = CafeStatus.CLOSE_SOON,
                ),
                CafeItemModel(
                    uuid = "",
                    address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                    workingHours = "9:00 - 22:00",
                    isOpenMessage = "Закрыто",
                    cafeStatus = CafeStatus.CLOSED,
                )
            )
        )
    }

    @Preview
    @Composable
    private fun CafeListLoadingScreenPreview() {
        CafeListScreen(cafeItemList = null)
    }
}