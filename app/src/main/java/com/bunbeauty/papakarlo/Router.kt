package com.bunbeauty.papakarlo

import android.content.Context.INPUT_METHOD_SERVICE
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.bunbeauty.common.Logger.NAV_TAG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class Router @Inject constructor() : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job()

    private var activity: WeakReference<AppCompatActivity>? = null

    @IdRes
    private var navHostId: Int? = null

    fun attach(activity: AppCompatActivity, @IdRes navHostId: Int) {
        this.activity = WeakReference(activity)
        this.navHostId = navHostId
    }

    fun navigateUp() {
        navHostId?.let { navHostId ->
            launch(Main) {
                activity?.get()?.findNavController(navHostId)?.navigateUp()
                Log.d(NAV_TAG, "navigateUp")
            }
        }
    }

    fun navigate(navDirections: NavDirections) {
        navHostId?.let { navHostId ->
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                Log.d(NAV_TAG, "exception " + exception.message)
            }
            launch(Main + handler) {
                activity?.get()?.findNavController(navHostId)?.navigate(navDirections)
                Log.d(NAV_TAG, "navigate $navDirections")
            }
        }
    }

    fun detach() {
        activity = null
        navHostId = null
    }

    private fun hideKeyboard(activity: AppCompatActivity?) {
        activity?.currentFocus?.also { view ->
            val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}