package com.bunbeauty.papakarlo.feature.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.databinding.LayoutComposeBinding
import com.bunbeauty.papakarlo.extensions.setContentWithTheme
import com.bunbeauty.papakarlo.navigation.foodDeliveryNavGraphBuilder
import com.bunbeauty.papakarlo.navigation.splash.SplashScreenDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.layout_compose), IMessageHost {

    val viewModel: MainViewModel by viewModel()

    private val viewBinding: LayoutComposeBinding by viewBinding(LayoutComposeBinding::bind)

    private val requestPermissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        viewBinding.root.setContentWithTheme {
            val mainState by viewModel.mainState.collectAsStateWithLifecycle()
            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(mainState.eventList) {
                handleEventList(
                    eventList = mainState.eventList,
                    snackbarHostState = snackbarHostState
                )
            }

            MainScreen(
                mainState = mainState,
                snackbarHostState = snackbarHostState,
                backgroundColor = mainState.statusBarColor
                    ?: FoodDeliveryTheme.colors.mainColors.surface
            )
        }

        checkNotificationPermission()
    }

    override fun showInfoMessage(text: String, paddingBottom: Int) {
        viewModel.showInfoMessage(text = text, paddingBottom = paddingBottom)
    }

    override fun showErrorMessage(text: String) {
        viewModel.showErrorMessage(text)
    }

    fun setStatusBarColor(color: Color) {
        viewModel.setStatusColor(color = color)
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun MainScreen(
        mainState: MainState,
        snackbarHostState: SnackbarHostState,
        backgroundColor: Color
    ) {
        Scaffold(
            snackbarHost = {
                FoodDeliverySnackbarHost(
                    snackbarHostState = snackbarHostState,
                    paddingBottom = mainState.paddingBottomSnackbar
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .background(backgroundColor)
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                ConnectionErrorMessage(visible = mainState.connectionLost)
                StatusBarMessage(statusBarMessage = mainState.statusBarMessage)
                val navController = rememberNavController()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = SplashScreenDestination
                    ) {
                        foodDeliveryNavGraphBuilder(
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ConnectionErrorMessage(visible: Boolean) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(tween(300)),
            exit = slideOutVertically(tween(300))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FoodDeliveryTheme.colors.mainColors.error)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
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
    private fun StatusBarMessage(statusBarMessage: MainState.StatusBarMessage) {
        AnimatedVisibility(
            visible = statusBarMessage.isVisible,
            enter = slideInVertically(tween(300)),
            exit = slideOutVertically(tween(300))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FoodDeliveryTheme.colors.statusColors.warning)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (statusBarMessage.isVisible) {
                    Text(
                        text = stringResource(R.string.warning_no_order_available),
                        style = FoodDeliveryTheme.typography.bodyMedium,
                        color = FoodDeliveryTheme.colors.statusColors.onStatus
                    )
                }
            }
        }
    }

    @Composable
    private fun FoodDeliverySnackbarHost(
        snackbarHostState: SnackbarHostState,
        paddingBottom: Int
    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(bottom = paddingBottom.dp)
        ) { snackbarData ->
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
                    contentColor = contentColor
                )
            }
        }
    }

    private fun handleEventList(
        eventList: List<MainState.Event>,
        snackbarHostState: SnackbarHostState
    ) {
        eventList.forEach { event ->
            when (event) {
                is MainState.Event.ShowMessageEvent -> {
                    lifecycleScope.launch {
                        val snackbarJob = launch {
                            snackbarHostState.showSnackbar(
                                visuals = FoodDeliverySnackbarVisuals(
                                    event.message
                                )
                            )
                        }
                        delay(2_000)
                        snackbarJob.cancel()
                    }
                }
            }
        }

        viewModel.consumeEventList(eventList)
    }
}
