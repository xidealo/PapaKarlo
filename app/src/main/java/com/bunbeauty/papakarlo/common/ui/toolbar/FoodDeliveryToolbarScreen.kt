package com.bunbeauty.papakarlo.common.ui.toolbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDeliveryToolbarScreen(
    title: String,
    backActionClick: () -> Unit,
    actions: List<FoodDeliveryToolbarActions> = emptyList(),
    content: (@Composable () -> Unit)
) {
    val appBarState = rememberTopAppBarState()
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)
    val scrollBehavior = remember { behavior }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FoodDeliveryToolbar(
                title = title,
                backActionClick = backActionClick,
                scrollBehavior = scrollBehavior,
                actions = actions
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = FoodDeliveryTheme.colors.background
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            content()
        }
    }
}
