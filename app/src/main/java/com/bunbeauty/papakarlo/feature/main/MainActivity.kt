package com.bunbeauty.papakarlo.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.NavMainDirections.*
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.papakarlo.databinding.ActivityMainBinding
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.feature.profile.settings.SettingsFragmentDirections.toLogoutBottomSheet
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
        R.id.cafeListFragment,
        R.id.confirmFragment,
        R.id.consumerCartFragment,
        R.id.createAddressFragment,
        R.id.createOrderFragment,
        R.id.loginFragment,
        R.id.menuFragment,
        R.id.orderDetailsFragment,
        R.id.ordersFragment,
        R.id.productFragment,
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
            item.onNavDestinationSelected(findNavController(R.id.activity_main_fcv_container))
                    || super.onOptionsItemSelected(item)
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
                val isToolbarVisible = destination.id in toolbarFragmentIdList
                viewBinding.activityMainTbToolbar.toggleVisibility(isToolbarVisible)
                val isLogoVisible = destination.id in logoFragmentIdList
                viewBinding.activityMainIvLogo.toggleVisibility(isLogoVisible)
                val isCartVisible = destination.id in cartFragmentIdList
                viewBinding.activityMainClCart.toggleVisibility(isCartVisible)
                viewBinding.activityMainIvCart.toggleVisibility(isCartVisible)
                val isBottomNavigationVisible = destination.id in bottomNavigationFragmentIdList
                viewBinding.activityMainBnvBottomNavigation.toggleVisibility(
                    isBottomNavigationVisible
                )
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
            viewBinding.activityMainTvInternetWarning.toggleVisibility(!isOnline)
        }
    }

    private inline fun <T> Flow<T>.startedLaunch(crossinline block: suspend (T) -> Unit) {
        startedLaunch(this@MainActivity, block)
    }


}