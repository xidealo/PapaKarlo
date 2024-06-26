package com.bunbeauty.papakarlo.feature.cafe.screen.cafelist

import android.os.Bundle
import android.view.View
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragmentWithSharedViewModel
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.topbar.FoodDeliveryCartAction
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItem
import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItemAndroid
import com.bunbeauty.papakarlo.feature.productdetails.ProductDetailsFragmentDirections
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.domain.model.cafe.CafeOpenState
import com.bunbeauty.shared.presentation.cafe_list.CafeListState
import com.bunbeauty.shared.presentation.cafe_list.CafeListViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CafeListFragment : BaseFragmentWithSharedViewModel(R.layout.layout_compose) {

    override val viewBinding by viewBinding(LayoutComposeBinding::bind)
    private val viewModel: CafeListViewModel by viewModel()
    private val cafeListUiStateMapper: CafeListUiStateMapper by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeCafeList()
        viewBinding.root.setContentWithTheme {
            val cafeItemList by viewModel.cafeListState.collectAsStateWithLifecycle()
            CafeListScreen(
                cafeListUi = cafeListUiStateMapper.map(cafeItemList),
                onCafeClicked = viewModel::onCafeCardClicked,
                onRefreshClicked = viewModel::observeCafeList
            )
            LaunchedEffect(cafeItemList.eventList) {
                handleEventList(cafeItemList.eventList)
            }
        }
    }

    @Composable
    private fun CafeListScreen(
        cafeListUi: CafeListUi,
        onCafeClicked: (String) -> Unit,
        onRefreshClicked: () -> Unit
    ) {
        FoodDeliveryScaffold(
            title = stringResource(R.string.title_cafe_list),
            topActions = listOf(
                FoodDeliveryCartAction(
                    topCartUi = cafeListUi.topCartUi,
                    onClick = {
                        findNavController().navigateSafe(ProductDetailsFragmentDirections.globalConsumerCartFragment())
                    }
                )
            )
        ) {
            Crossfade(targetState = cafeListUi.state, label = "CafeListScreen") { state ->
                when (state) {
                    is CafeListState.State.Success -> CafeListSuccessScreen(
                        cafeListUi.cafeList,
                        onCafeClicked = onCafeClicked
                    )

                    is CafeListState.State.Loading -> LoadingScreen()
                    is CafeListState.State.Error -> {
                        ErrorScreen(
                            mainTextId = R.string.error_cafe_list_loading,
                            onClick = onRefreshClicked
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun CafeListSuccessScreen(
        cafeItemList: List<CafeItemAndroid>,
        onCafeClicked: (String) -> Unit
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(FoodDeliveryTheme.dimensions.mediumSpace),
            verticalArrangement = spacedBy(8.dp)
        ) {
            itemsIndexed(cafeItemList) { i, cafeItem ->
                CafeItem(
                    modifier = Modifier,
                    cafeItem = cafeItem,
                    onClick = {
                        onCafeClicked(cafeItem.uuid)
                    }
                )
            }
        }
    }

    private fun handleEventList(eventList: List<CafeListState.Event>) {
        eventList.forEach { event ->
            when (event) {
                is CafeListState.Event.OpenCafeOptionsBottomSheet -> {
                    findNavController().navigateSafe(
                        CafeListFragmentDirections.toCafeOptionsBottomSheet(event.uuid)
                    )
                }
            }
        }
        viewModel.consumeEventList(eventList)
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CafeListSuccessScreenPreview() {
        FoodDeliveryTheme {
            CafeListScreen(
                cafeListUi = CafeListUi(
                    cafeList = listOf(
                        CafeItemAndroid(
                            uuid = "",
                            address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                            workingHours = "9:00 - 22:00",
                            cafeStatusText = "Open",
                            phone = "00000000",
                            cafeOpenState = CafeOpenState.Opened
                        ),
                        CafeItemAndroid(
                            uuid = "",
                            address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                            workingHours = "9:00 - 22:00",
                            cafeStatusText = "Close soon",
                            phone = "00000000",
                            cafeOpenState = CafeOpenState.CloseSoon(30)
                        ),
                        CafeItemAndroid(
                            uuid = "",
                            address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                            workingHours = "9:00 - 22:00",
                            cafeStatusText = "Closed",
                            phone = "00000000",
                            cafeOpenState = CafeOpenState.Closed
                        )
                    ),
                    state = CafeListState.State.Success,
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2"
                    )
                ),
                onCafeClicked = {},
                onRefreshClicked = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CafeListLoadingScreenPreview() {
        FoodDeliveryTheme {
            CafeListScreen(
                cafeListUi = CafeListUi(
                    state = CafeListState.State.Loading,
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2"
                    )
                ),
                onCafeClicked = {},
                onRefreshClicked = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CafeListErrorScreenPreview() {
        FoodDeliveryTheme {
            CafeListScreen(
                cafeListUi = CafeListUi(
                    state = CafeListState.State.Error(Throwable()),
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2"
                    )
                ),
                onCafeClicked = {},
                onRefreshClicked = {}
            )
        }
    }
}
