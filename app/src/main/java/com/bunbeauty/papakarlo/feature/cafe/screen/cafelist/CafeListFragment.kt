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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseComposeFragment
import com.bunbeauty.papakarlo.common.extension.navigateSafe
import com.bunbeauty.papakarlo.common.ui.element.FoodDeliveryScaffold
import com.bunbeauty.papakarlo.common.ui.element.card.FoodDeliveryItem
import com.bunbeauty.papakarlo.common.ui.element.topbar.FoodDeliveryCartAction
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItem
import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItemAndroid
import com.bunbeauty.papakarlo.feature.productdetails.ProductDetailsFragmentDirections
import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.domain.model.cafe.CafeOpenState
import com.bunbeauty.shared.presentation.cafe_list.CafeList
import com.bunbeauty.shared.presentation.cafe_list.CafeListViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CafeListFragment :
    BaseComposeFragment<CafeList.DataState, CafeListViewState, CafeList.Action, CafeList.Event>() {

    override val viewModel: CafeListViewModel by viewModel()
    private val cafeListViewStateMapper: CafeListViewStateMapper by inject()

    @Composable
    override fun CafeList.DataState.mapState(): CafeListViewState {
        return cafeListViewStateMapper.map(cafeListState = this)
    }

    @Composable
    override fun Screen(viewState: CafeListViewState, onAction: (CafeList.Action) -> Unit) {
        CafeListScreen(
            cafeListViewState = viewState,
            onAction = onAction
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)
        viewModel.onAction(CafeList.Action.Init)
    }

    @Composable
    private fun CafeListScreen(
        cafeListViewState: CafeListViewState,
        onAction: (CafeList.Action) -> Unit,
    ) {
        FoodDeliveryScaffold(
            title = stringResource(R.string.title_cafe_list),
            topActions = listOf(
                FoodDeliveryCartAction(
                    topCartUi = cafeListViewState.topCartUi,
                    onClick = {
                        onAction(CafeList.Action.onCartClicked)
                    },
                )
            ),
            backgroundColor = FoodDeliveryTheme.colors.mainColors.surface,
        ) {
            Crossfade(
                targetState = cafeListViewState.state,
                label = "CafeListScreen"
            ) { state ->
                when (state) {
                    is CafeListViewState.State.Error -> ErrorScreen(
                        mainTextId = R.string.error_cafe_list_loading,
                        onClick = {
                            onAction(CafeList.Action.onRefreshClicked)
                        }
                    )

                    CafeListViewState.State.Loading -> LoadingScreen()
                    CafeListViewState.State.Success -> CafeListSuccessScreen(
                        cafeListViewState.cafeList,
                        onAction = onAction
                    )
                }
            }
        }
    }

    @Composable
    private fun CafeListSuccessScreen(
        cafeItemList: List<CafeItemAndroid>,
        onAction: (CafeList.Action) -> Unit,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(cafeItemList) { i, cafeItem ->
                FoodDeliveryItem(needDivider = !cafeItem.isLast) {
                    CafeItem(
                        modifier = Modifier,
                        cafeItem = cafeItem,
                        onClick = {
                            onAction(CafeList.Action.onCafeClicked(cafeUuid = cafeItem.uuid))
                        }
                    )
                }
            }
        }
    }

    override fun handleEvent(event: CafeList.Event) {
        when (event) {
            is CafeList.Event.OpenCafeOptionsBottomSheet -> findNavController().navigateSafe(
                CafeListFragmentDirections.toCafeOptionsBottomSheet(event.uuid)
            )

            CafeList.Event.OpenConsumerCartProduct -> findNavController().navigateSafe(
                ProductDetailsFragmentDirections.globalConsumerCartFragment()
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CafeListSuccessScreenPreview() {
        FoodDeliveryTheme {
            CafeListScreen(
                cafeListViewState = CafeListViewState(
                    cafeList = listOf(
                        CafeItemAndroid(
                            uuid = "",
                            address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                            workingHours = "9:00 - 22:00",
                            cafeStatusText = "Open",
                            phone = "00000000",
                            cafeOpenState = CafeOpenState.Opened,
                            isLast = false
                        ),
                        CafeItemAndroid(
                            uuid = "",
                            address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                            workingHours = "9:00 - 22:00",
                            cafeStatusText = "Close soon",
                            phone = "00000000",
                            cafeOpenState = CafeOpenState.CloseSoon(30),
                            isLast = false
                        ),
                        CafeItemAndroid(
                            uuid = "",
                            address = "улица Чапаева, д. 22аб кв. 55, 1 подъезд, 1 этаж",
                            workingHours = "9:00 - 22:00",
                            cafeStatusText = "Closed",
                            phone = "00000000",
                            cafeOpenState = CafeOpenState.Closed,
                            isLast = true
                        )
                    ),
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2"
                    ),
                    state = CafeListViewState.State.Success
                ),
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CafeListLoadingScreenPreview() {
        FoodDeliveryTheme {
            CafeListScreen(
                cafeListViewState = CafeListViewState(
                    state = CafeListViewState.State.Loading,
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2"
                    ),
                    cafeList = emptyList()
                ),
                onAction = {}
            )
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CafeListErrorScreenPreview() {
        FoodDeliveryTheme {
            CafeListScreen(
                cafeListViewState = CafeListViewState(
                    state = CafeListViewState.State.Error(Throwable()),
                    topCartUi = TopCartUi(
                        cost = "100",
                        count = "2"
                    ),
                    cafeList = emptyList()
                ),
                onAction = {}
            )
        }
    }
}
