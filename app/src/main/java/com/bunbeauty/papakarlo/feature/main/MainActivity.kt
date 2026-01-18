package com.bunbeauty.papakarlo.feature.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        enableEdgeToEdge()
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        setContent {
            FoodDeliveryTheme(
                flavor = BuildConfig.FLAVOR,
            ) {
                MainScreen(
                    modifier =
                        Modifier
                            .statusBarsPadding()
                            .imePadding(),
                )
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
