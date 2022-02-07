package com.bunbeauty.papakarlo

import android.content.Context.INPUT_METHOD_SERVICE
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.bunbeauty.common.Logger.NAV_TAG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class Router  constructor() : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job()

    private var activity: WeakReference<AppCompatActivity>? = null

    @IdRes
    private var navHostId: Int? = null

    fun attach(activity: AppCompatActivity, @IdRes navHostId: Int) {
        this.activity = WeakReference(activity)
        this.navHostId = navHostId
    }

    fun checkPrevious(destinationId: Int): Boolean {
        val backQueue = findNavController()?.backQueue
        return if (backQueue != null && backQueue.size > 1) {
            backQueue[backQueue.lastIndex - 1].destination.id == destinationId
        } else {
            false
        }
    }

    fun navigateUp() {
        launch(Main) {
            hideKeyboard()
            findNavController()?.navigateUp()
            Log.d(NAV_TAG, "navigateUp")
        }
    }

    fun navigate(navDirections: NavDirections) {
        val handler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
            Log.d(NAV_TAG, "exception " + exception.message)
        }
        launch(Main + handler) {
            hideKeyboard()
            findNavController()?.navigate(navDirections)
            Log.d(NAV_TAG, "navigate $navDirections")
        }
    }

    private fun findNavController(): NavController? {
        return navHostId?.let { navHostId ->
            activity?.get()?.findNavController(navHostId)
        }
    }

    fun detach() {
        activity = null
        navHostId = null
    }

    private fun hideKeyboard() {
        activity?.get()?.currentFocus?.also { view ->
            val imm = activity?.get()?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}