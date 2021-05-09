package com.bunbeauty.papakarlo

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class LoginUITest : MainTest() {
    /**
     * Action:
     * Login
     * Expected:
     */
    @Test
    fun should_login_when_has_no_login() {
        testLoginWithLoginBefore("9969224186", "123456")
    }

    fun testLoginWithLoginBefore(phone: String, code: String) {
        onView(withId(R.id.profile_fragment))
            .perform(ViewActions.click())
        Thread.sleep(PAUSE)

        onView(withId(R.id.fragment_profile_mcv_settings))
            .perform(ViewActions.click())
        Thread.sleep(SHORT_PAUSE)

        onView(withId(R.id.fragment_settings_mcv_phone))
            .perform(ViewActions.click())

        onView(withId(R.id.fragment_login_et_phone))
            .perform(ViewActions.typeText(phone))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.fragment_login_btn_login))
            .perform(ViewActions.click())
        Thread.sleep(LONG_PAUSE)

        onView(withId(R.id.fragment_confirm_et_code))
            .perform(ViewActions.typeText(code))
        Espresso.closeSoftKeyboard()
        Thread.sleep(PAUSE)

        onView(withId(R.id.fragment_profile_mcv_settings))
            .perform(ViewActions.click())
        Thread.sleep(SHORT_PAUSE)

        onView(withId(R.id.fragment_settings_tv_phone_value))
            .check(matches(withText("+7 (996) 922-41-86")))
    }
}