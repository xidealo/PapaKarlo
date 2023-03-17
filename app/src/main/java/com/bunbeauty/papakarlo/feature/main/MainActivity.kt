package com.bunbeauty.papakarlo.feature.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.papakarlo.databinding.ActivityMainBinding
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.Flow
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    val router: Router by inject()

    val resourcesProvider: IResourcesProvider by inject()

    val viewModel: MainViewModel by viewModel()

    private val viewBinding: ActivityMainBinding by viewBinding(
        ActivityMainBinding::bind,
        R.id.activity_main_cl_main
    )

    private val bottomNavigationFragmentIdList = listOf(
        R.id.cafeListFragment,
        R.id.menuFragment,
        R.id.profileFragment,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        viewBinding.root

        lifecycle.addObserver(viewModel)

        val navController = getNavController()

        setupBottomNavigationBar(navController)
        setupNavigationListener(navController)
        observeIsOnline()

        router.attach(this, R.id.activity_main_fcv_container)
    }

    override fun onDestroy() {
        router.detach()

        super.onDestroy()
    }

    private fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_main_fcv_container)
                as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupNavigationListener(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination !is FloatingWindow) {
                viewBinding.activityMainBnvBottomNavigation.isVisible =
                    (destination.id in bottomNavigationFragmentIdList)
            }
        }
    }

    private fun setupBottomNavigationBar(navController: NavController) {
        viewBinding.activityMainBnvBottomNavigation.setupWithNavController(navController)
        viewBinding.activityMainBnvBottomNavigation.setOnItemReselectedListener {}
    }

    private fun observeIsOnline() {
        viewModel.isOnline.startedLaunch { isOnline ->
            viewBinding.activityMainTvInternetWarning.isGone = isOnline
        }
    }

    private inline fun <T> Flow<T>.startedLaunch(crossinline block: suspend (T) -> Unit) {
        startedLaunch(this@MainActivity, block)
    }
}
