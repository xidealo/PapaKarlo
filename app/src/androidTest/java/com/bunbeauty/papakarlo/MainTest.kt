package com.bunbeauty.papakarlo

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.bunbeauty.papakarlo.ui.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
abstract class MainTest{

    lateinit var scenario: ActivityScenario<MainActivity>

    var context: Context = ApplicationProvider.getApplicationContext()


    @Before
    fun setup() {
        val intent =
            Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    companion object {
        const val PAUSE = 3000L
        const val LONG_PAUSE = 10000L
        const val SHORT_PAUSE = 500L
    }

}