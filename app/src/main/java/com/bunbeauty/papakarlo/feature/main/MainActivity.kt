package com.bunbeauty.papakarlo.feature.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.FloatingWindow
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.element.bottom_bar.FoodDeliveryNavigationBar
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.FragmentContainerBinding
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.layout_compose), IMessageHost {

    val viewModel: MainViewModel by viewModel()

    private val viewBinding: LayoutComposeBinding by viewBinding(LayoutComposeBinding::bind)

    private val requestPermissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }
    }

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        viewBinding.root.setContentWithTheme {
            val mainState by viewModel.mainState.collectAsStateWithLifecycle()
            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(mainState.eventList) {
                handleEventList(
                    eventList = mainState.eventList,
                    snackbarHostState = snackbarHostState,
                )
            }
            MainScreen(
                mainState = mainState,
                snackbarHostState = snackbarHostState
            )
        }

        checkNotificationPermission()
    }

    override fun showInfoMessage(text: String) {
        viewModel.showInfoMessage(text)
    }

    override fun showErrorMessage(text: String) {
        viewModel.showErrorMessage(text)
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainScreen(mainState: MainState, snackbarHostState: SnackbarHostState) {
        Scaffold(
            snackbarHost = {
                FoodDeliverySnackbarHost(snackbarHostState)
            },
            bottomBar = {
                FoodDeliveryNavigationBar(options = mainState.navigationBarOptions)
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                ConnectionErrorMessage(visible = mainState.connectionLost)
                Box(modifier = Modifier.weight(1f)) {
                    AndroidViewBinding(factory = ::fragmentContainerFactory)
                }
            }
        }
    }

    @Composable
    private fun ConnectionErrorMessage(visible: Boolean) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FoodDeliveryTheme.colors.mainColors.error)
                    .padding(8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = resources.getString(R.string.error_no_internet),
                    style = FoodDeliveryTheme.typography.bodyMedium,
                    color = FoodDeliveryTheme.colors.mainColors.onError
                )
            }
        }
    }

    @Composable
    private fun FoodDeliverySnackbarHost(snackbarHostState: SnackbarHostState) {
        SnackbarHost(hostState = snackbarHostState) { snackbarData ->
            (snackbarData.visuals as? FoodDeliverySnackbarVisuals)?.let { visuals ->
                val containerColor = when (visuals.foodDeliveryMessage.type) {
                    FoodDeliveryMessageType.INFO -> FoodDeliveryTheme.colors.mainColors.primary
                    FoodDeliveryMessageType.ERROR -> FoodDeliveryTheme.colors.mainColors.error
                }
                val contentColor = when (visuals.foodDeliveryMessage.type) {
                    FoodDeliveryMessageType.INFO -> FoodDeliveryTheme.colors.mainColors.onPrimary
                    FoodDeliveryMessageType.ERROR -> FoodDeliveryTheme.colors.mainColors.onError
                }
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = containerColor,
                    contentColor = contentColor,
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun handleEventList(
        eventList: List<MainState.Event>,
        snackbarHostState: SnackbarHostState
    ) {
        eventList.forEach { event ->
            when (event) {
                is MainState.Event.ShowMessageEvent -> {
                    lifecycleScope.launch {
                        snackbarHostState.showSnackbar(
                            FoodDeliverySnackbarVisuals(event.message)
                        )
                    }
                }
            }
        }

        viewModel.consumeEventList(eventList)
    }

    private fun fragmentContainerFactory(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ): FragmentContainerBinding =
        FragmentContainerBinding.inflate(inflater, parent, attachToParent).also {
            setupNavigationListener()
        }

    private fun setupNavigationListener() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.containerFcv) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if (destination !is FloatingWindow) {
                viewModel.onNavDestinationUpdated(destination.id, controller)
            }
        }
    }
}
