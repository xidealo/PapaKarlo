package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.NavMainDirections.*
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.papakarlo.databinding.ActivityMainBinding
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.MainViewModel
import com.bunbeauty.papakarlo.presentation.base.ViewModelFactory
import com.bunbeauty.papakarlo.ui.fragment.profile.settings.SettingsFragmentDirections.toLogoutBottomSheet
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

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

    private var viewModel: MainViewModel? = null

    @Inject
    lateinit var modelFactory: ViewModelFactory

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

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

        (application as PapaKarloApplication).appComponent
            .getViewModelComponent()
            .create(this)
            .inject(this)
        setContentView(viewBinding.root)

        viewModel = ViewModelProvider(this, modelFactory)[MainViewModel::class.java]

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
        viewModel?.run {
            cartCost.onEach { cartCost ->
                viewBinding.activityMainTvCart.text = cartCost
            }.startedLaunch(this@MainActivity)
            cartProductCount.onEach { cartProductCount ->
                viewBinding.activityMainTvCartCount.text = cartProductCount
            }.startedLaunch(this@MainActivity)
        }
    }

    private fun observeIsOnline() {
        viewModel?.isOnline?.onEach { isOnline ->
            viewBinding.activityMainTvInternetWarning.toggleVisibility(!isOnline)
        }?.startedLaunch(this)
    }
}