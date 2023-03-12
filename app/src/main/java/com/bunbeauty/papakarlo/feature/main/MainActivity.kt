package com.bunbeauty.papakarlo.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.NavMainDirections.globalConsumerCartFragment
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.papakarlo.databinding.ActivityMainBinding
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.feature.profile.screen.settings.SettingsFragmentDirections.toLogoutBottomSheet
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

    private val toolbarFragmentIdList = listOf(
        R.id.menuFragment,
        R.id.profileFragment,
        R.id.settingsFragment
    )
    private val logoFragmentIdList = listOf(R.id.menuFragment)
    private val cartFragmentIdList = listOf(
        R.id.cafeListFragment,
        R.id.menuFragment,
        R.id.productFragment,
        R.id.profileFragment,
    )
    private val bottomNavigationFragmentIdList = listOf(
        R.id.cafeListFragment,
        R.id.menuFragment,
        R.id.profileFragment,
    )

    private val appBarConfiguration = AppBarConfiguration(
        topLevelDestinationIds = setOf(
            R.id.updateFragment,
            R.id.selectCityFragment,
            R.id.cafeListFragment,
            R.id.menuFragment,
            R.id.profileFragment
        ),
        fallbackOnNavigateUpListener = ::onSupportNavigateUp
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        viewBinding.root

        lifecycle.addObserver(viewModel)

        val navController = getNavController()

        setupBottomNavigationBar(navController)
        setupNavigationListener(navController)
        setupToolbar(navController, appBarConfiguration)
        observeCart()
        observeIsOnline()

        router.attach(this, R.id.activity_main_fcv_container)
    }

    override fun onSupportNavigateUp(): Boolean {
        return getNavController().navigateUp(appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (menu.size() == 0) {
            menuInflater.inflate(R.menu.top_menu, menu)
        }
        val isSettings =
            findNavController(R.id.activity_main_fcv_container).currentDestination?.id == R.id.settingsFragment
        menu.findItem(R.id.logoutBottomSheet)?.isVisible = isSettings

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.logoutBottomSheet) {
            router.navigate(toLogoutBottomSheet())
            true
        } else {
            item.onNavDestinationSelected(findNavController(R.id.activity_main_fcv_container)) ||
                super.onOptionsItemSelected(item)
        }
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
                viewBinding.activityMainTbToolbar.isVisible =
                    (destination.id in toolbarFragmentIdList)
                viewBinding.activityMainIvLogo.isVisible = (destination.id in logoFragmentIdList)
                viewBinding.activityMainClCart.isVisible = (destination.id in cartFragmentIdList)
                viewBinding.activityMainIvCart.isVisible = (destination.id in cartFragmentIdList)
                viewBinding.activityMainBnvBottomNavigation.isVisible =
                    (destination.id in bottomNavigationFragmentIdList)
            }
        }
    }

    private fun setupToolbar(
        navController: NavController,
        appBarConfiguration: AppBarConfiguration
    ) {
        setSupportActionBar(viewBinding.activityMainTbToolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        viewBinding.activityMainClCart.setOnClickListener {
            if (router.checkPrevious(R.id.consumerCartFragment)) {
                router.navigateUp()
            } else {
                router.navigate(globalConsumerCartFragment())
            }
        }
        viewBinding.activityMainTbToolbar.setNavigationOnClickListener {
            router.navigateUp()
        }
    }

    private fun setupBottomNavigationBar(navController: NavController) {
        viewBinding.activityMainBnvBottomNavigation.setupWithNavController(navController)
        viewBinding.activityMainBnvBottomNavigation.setOnItemReselectedListener {}
    }

    private fun observeCart() {
        viewModel.cartCost.startedLaunch { cartCost ->
            viewBinding.activityMainTvCart.text = cartCost
        }
        viewModel.cartProductCount.startedLaunch { cartProductCount ->
            viewBinding.activityMainTvCartCount.text = cartProductCount
        }
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
