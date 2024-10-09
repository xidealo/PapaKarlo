package com.bunbeauty

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bunbeauty.papakarlo.feature.main.MainActivity
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
class AuthScenarioTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.withComposeSupport()
) {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    /*
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
    */

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test() = run {
        step("Выбор города") {
            onComposeScreen<SelectCityScreenViews>(composeTestRule) {
                list {
                    firstChild<LazyListItemNode> {
                        performClick()
                    }
                }
            }
        }
//        step("Переход в профиль") {
//            onComposeScreen<MainScreenViews>(composeTestRule) {
//                profileBottomItem {
//                    performClick()
//                }
//            }
//        }
//        step("Нажимаем войти") {
//            onComposeScreen<ProfileScreenViews>(composeTestRule) {
//                enterProfileButton {
//                    performClick()
//                }
//                //icon
//            }
//        }
    }
}