package com.bunbeauty.papakarlo.feature.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import com.bunbeauty.designsystem.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.BuildConfig
import com.bunbeauty.papakarlo.R
import com.bunbeauty.shared.presentation.MainViewModel
import com.bunbeauty.shared.ui.main.IMessageHost
import com.bunbeauty.shared.ui.screen.main.MainScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity :
    AppCompatActivity(R.layout.layout_compose),
    IMessageHost {
    val viewModel: MainViewModel by viewModel()

    private val requestPermissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            navigationBarStyle =
                SystemBarStyle.dark(
                    scrim = android.graphics.Color.TRANSPARENT,
                ),
        )

        setContent {
            val color = FoodDeliveryTheme.colors.mainColors.surface
            val statusBarColor = remember { mutableStateOf(color) }
            FoodDeliveryTheme(
                flavor = BuildConfig.FLAVOR,
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    MainScreen(
                        modifier =
                            Modifier
                                .imePadding(),
                        barColorCallback = {
                            statusBarColor.value = it
                        },
                    )

                    Spacer(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(
                                    with(LocalDensity.current) {
                                        WindowInsets.navigationBars.getBottom(this).toDp()
                                    },
                                ).background(
                                    Brush.verticalGradient(
                                        colors =
                                            listOf(
                                                statusBarColor.value.copy(alpha = 0.1f),
                                                statusBarColor.value.copy(alpha = 0.2f),
                                                statusBarColor.value.copy(alpha = 0.3f),
                                                statusBarColor.value.copy(alpha = 0.4f),
                                                statusBarColor.value.copy(alpha = 0.6f),
                                                statusBarColor.value.copy(alpha = 0.8f),
                                            ),
                                    ),
                                ).blur(radius = 10.dp)
                                .align(Alignment.BottomCenter),
                    )
                }
            }
        }

        checkNotificationPermission()
    }

    override fun showInfoMessage(
        text: String,
        paddingBottom: Int,
    ) {
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
}
