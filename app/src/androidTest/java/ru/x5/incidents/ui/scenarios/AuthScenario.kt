package ru.x5.incidents.ui.scenarios

import android.Manifest
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.feature.main.MainActivity
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.x5.incidents.ui.screens.plg.LazyListItemNode
import ru.x5.incidents.ui.screens.plg.SelectCityScreen
@RunWith(AndroidJUnit4::class)
class AuthScenario() : TestCase(
    kaspressoBuilder = Kaspresso.Builder.withComposeSupport()
) {
    @Rule
    @JvmField
    val composeTestRule = createComposeRule()

    @get:Rule
    val runtimePermissionRules: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.CAMERA
    )

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test() {
        composeTestRule.setContent {
            FoodDeliveryTheme {

            }
        }
        //step("Здесь выбираем город") {
            onComposeScreen<SelectCityScreen>(composeTestRule) {
                list {
                    firstChild<LazyListItemNode> {
                        performClick()
                    }
                }
            }
        //}
    }
}
