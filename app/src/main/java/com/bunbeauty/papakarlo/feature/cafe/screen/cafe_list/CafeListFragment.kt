package com.bunbeauty.papakarlo.feature.cafe.screen.cafe_list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.BaseFragment
import com.bunbeauty.papakarlo.common.ui.screen.ErrorScreen
import com.bunbeauty.papakarlo.common.ui.screen.LoadingScreen
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.toolbar.FoodDeliveryCartAction
import com.bunbeauty.papakarlo.common.ui.toolbar.FoodDeliveryToolbarScreen
import com.bunbeauty.papakarlo.databinding.FragmentCafeListBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.feature.cafe.model.CafeItem
import com.bunbeauty.papakarlo.feature.cafe.ui.CafeItem
import com.bunbeauty.papakarlo.feature.product_details.ProductDetailsFragmentDirections
import com.bunbeauty.papakarlo.feature.top_cart.TopCartUi
import com.bunbeauty.shared.domain.model.cafe.CafeStatus
import com.google.android.material.transition.MaterialFadeThrough
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CafeListFragment : BaseFragment(R.layout.fragment_cafe_list) {

    override val viewBinding by viewBinding(FragmentCafeListBinding::bind)
    override val viewModel: CafeListViewModel by viewModel()
    private val cafeListUiStateMapper: CafeListUiStateMapper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        overrideBackPressedCallback()
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCafeItemList()
        viewBinding.fragmentCafeListCvMain.setContentWithTheme {
            val cafeItemList by viewModel.cafeListState.collectAsStateWithLifecycle()
            CafeListScreen(
                cafeListState = cafeListUiStateMapper.map(cafeItemList),
                onCafeClicked = viewModel::onCafeCardClicked,
                onRefreshClicked = viewModel::getCafeItemList
            )
            LaunchedEffect(cafeItemList.eventList) {
                handleEventList(cafeItemList.eventList)
            }
        }
    }

    @Composable
    private fun CafeListScreen(
        cafeListState: CafeListUi,
        onCafeClicked: (CafeItem) -> Unit,
        onRefreshClicked: () -> Unit,
    ) {
        FoodDeliveryToolbarScreen(
            title = stringResource(R.string.title_cafe_list),
            topActions = listOf(
                FoodDeliveryCartAction(
                    topCartUi = cafeListState.topCartUi,
                ) {
                    val backQueue = findNavController().backQueue
                    if ((backQueue.size > 1) &&
                        (backQueue[backQueue.lastIndex - 1].destination.id == R.id.consumerCartFragment)
                    ) {
                        findNavController().popBackStack()
                    } else {
                        findNavController().navigate(ProductDetailsFragmentDirections.globalConsumerCartFragment())
                    }
                }
            ),
        ) {
            when (cafeListState.state) {
                is CafeListState.State.Success -> CafeListSuccessScreen(
                    cafeListState.cafeList,
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

    @Composable
    private fun CafeListSuccessScreen(
        cafeItemList: List<CafeItem>,
        onCafeClicked: (CafeItem) -> Unit,
    ) {
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

    private fun handleEventList(eventList: List<CafeListState.Event>) {
        eventList.forEach { event ->
            when (event) {
                is CafeListState.Event.OpenCafeOptionsBottomSheet -> {
                    findNavController().navigate(
                        CafeListFragmentDirections.toCafeOptionsBottomSheet(
                            event.uuid
                        )
                    )
                }
            }
        }
        viewModel.consumeEventList(eventList)
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CafeListSuccessScreenPreview() {
        CafeListScreen(
            cafeListState = CafeListUi(
                cafeList = listOf(
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
                ),
                state = CafeListState.State.Success,
                topCartUi = TopCartUi(
                    cost = "100",
                    count = "2",
                ),
            ),
            onCafeClicked = {},
            onRefreshClicked = {}
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CafeListLoadingScreenPreview() {
        CafeListScreen(
            cafeListState = CafeListUi(
                state = CafeListState.State.Loading,
                topCartUi = TopCartUi(
                    cost = "100",
                    count = "2",
                ),
            ),
            onCafeClicked = {},
            onRefreshClicked = {}
        )
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun CafeListErrorScreenPreview() {
        CafeListScreen(
            cafeListState = CafeListUi(
                state = CafeListState.State.Error(Throwable()),
                topCartUi = TopCartUi(
                    cost = "100",
                    count = "2",
                ),
            ),
            onCafeClicked = {},
            onRefreshClicked = {}
        )
    }
}
