package com.bunbeauty.papakarlo.feature.cafe.screen.cafe_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.state.State
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentCafeListBinding
import com.bunbeauty.papakarlo.extensions.compose
import com.bunbeauty.papakarlo.feature.cafe.model.CafeItem
import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItem
import com.bunbeauty.shared.domain.model.cafe.CafeStatus
import com.google.android.material.transition.MaterialFadeThrough
import org.koin.androidx.viewmodel.ext.android.viewModel

class CafeListFragment : BaseFragment(R.layout.fragment_cafe_list) {

    override val viewBinding by viewBinding(FragmentCafeListBinding::bind)
    override val viewModel: CafeListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCafeItemList()
        viewBinding.fragmentCafeListCvMain.compose {
            val cafeItemList by viewModel.cafeItemList.collectAsState()
            CafeListScreen(cafeItemList)
        }
    }

    @Composable
    private fun CafeListScreen(cafeItemListState: State<List<CafeItem>>) {
        when (cafeItemListState) {
            is State.Success -> CafeListSuccessScreen(cafeItemListState.data)
            is State.Loading -> LoadingScreen()
            is State.Error -> {
                ErrorScreen(mainTextId = R.string.error_cafe_list_loading) {
                    viewModel.getCafeItemList()
                }
            }
            else -> Unit
        }
    }

    @Composable
    private fun CafeListSuccessScreen(cafeItemList: List<CafeItem>) {
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

    @Preview(showSystemUi = true)
    @Composable
    private fun CafeListSuccessScreenPreview() {
        CafeListScreen(
            cafeItemListState = State.Success(
                listOf(
                    CafeItem(
                        uuid = "",
                        address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                        workingHours = "9:00 - 22:00",
                        isOpenMessage = "Открыто",
                        cafeStatus = CafeStatus.OPEN,
                    ),
                    CafeItem(
                        uuid = "",
                        address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                        workingHours = "9:00 - 22:00",
                        isOpenMessage = "Открыто. Закроется через 30 минут",
                        cafeStatus = CafeStatus.CLOSE_SOON,
                    ),
                    CafeItem(
                        uuid = "",
                        address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                        workingHours = "9:00 - 22:00",
                        isOpenMessage = "Закрыто",
                        cafeStatus = CafeStatus.CLOSED,
                    )
                )
            )
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CafeListLoadingScreenPreview() {
        CafeListScreen(cafeItemListState = State.Loading())
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CafeListErrorScreenPreview() {
        CafeListScreen(cafeItemListState = State.Error("Не удалось загрузить список рестиков"))
    }
}
