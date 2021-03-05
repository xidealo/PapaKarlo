package com.bunbeauty.papakarlo.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bunbeauty.papakarlo.NavMainDirections.globalToCartFragment
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

class MainActivity : AppCompatActivity() {

    private var mutableViewDataBinding: ActivityMainBinding? = null
    val viewDataBinding: ActivityMainBinding by lazy {
        checkNotNull(mutableViewDataBinding)
    }

    private val toolbarFragmentIdList = listOf(
        R.id.cafe_list_fragment,
        R.id.confirmFragment,
        R.id.consumerCartFragment,
        R.id.create_address_fragment,
        R.id.create_order_fragment,
        R.id.loginFragment,
        R.id.menu_fragment,
        R.id.order_derails_fragment,
        R.id.orders_fragment,
        R.id.product_fragment,
        R.id.profile_fragment,
        R.id.settings_fragment
    )
    private val logoFragmentIdList = listOf(R.id.menu_fragment)
    private val cartFragmentIdList = listOf(
        R.id.cafe_list_fragment,
        R.id.menu_fragment,
        R.id.product_fragment,
        R.id.profile_fragment,
    )
    private val bottomNavigationFragmentIdList = listOf(
        R.id.cafe_list_fragment,
        R.id.menu_fragment,
        R.id.profile_fragment,
    )

    private var viewModel: MainViewModel? = null

    @Inject
    lateinit var modelFactory: ViewModelFactory

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var resourcesProvider: IResourcesProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        (application as PapaKarloApplication).appComponent
            .getViewModelComponent()
            .create(this)
            .inject(this)

        mutableViewDataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewDataBinding.root)

        viewModel = ViewModelProvider(this, modelFactory)[MainViewModel::class.java]

        setupToolbar()
        setupBottomNavigationBar()
        setupNavigationListener()
        observeCart()

        router.attach(this, R.id.activity_main_fcv_container)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (menu.size() == 0) {
            menuInflater.inflate(R.menu.top_menu, menu)
        }
        val isSettings =
            findNavController(R.id.activity_main_fcv_container).currentDestination?.id == R.id.settings_fragment
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
        mutableViewDataBinding = null

        super.onDestroy()
    }

    private fun setCartText(cartText: String) {
        viewDataBinding.activityMainTvCart.text = cartText
    }

    private fun setCartProductCount(cartProductCount: String) {
        viewDataBinding.activityMainTvCartCount.text = cartProductCount
    }

    private fun setupNavigationListener() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.activity_main_fcv_container) as NavHostFragment).navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isToolbarVisible = destination.id in toolbarFragmentIdList
            viewDataBinding.activityMainTbToolbar.toggleVisibility(isToolbarVisible)
            val isLogoVisible = destination.id in logoFragmentIdList
            viewDataBinding.activityMainIvLogo.toggleVisibility(isLogoVisible)
            val isCartVisible = destination.id in cartFragmentIdList
            viewDataBinding.activityMainClCart.toggleVisibility(isCartVisible)
            viewDataBinding.activityMainIvCart.toggleVisibility(isCartVisible)
            val isBottomNavigationVisible = destination.id in bottomNavigationFragmentIdList
            viewDataBinding.activityMainBnvBottomNavigation.toggleVisibility(
                isBottomNavigationVisible
            )
        }
    }

    private fun setupToolbar() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.activity_main_fcv_container) as NavHostFragment).navController

        setSupportActionBar(viewDataBinding.activityMainTbToolbar)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.cafe_list_fragment, R.id.menu_fragment, R.id.profile_fragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        viewDataBinding.activityMainClCart.setOnClickListener {
            goToCart()
        }
        viewDataBinding.activityMainTbToolbar.setNavigationOnClickListener {
            router.navigateUp()
        }
    }

    private fun goToCart() {
        router.navigate(globalToCartFragment())
    }

    private fun setupBottomNavigationBar() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.activity_main_fcv_container) as NavHostFragment).navController

        viewDataBinding.activityMainBnvBottomNavigation.setupWithNavController(navController)
        viewDataBinding.activityMainBnvBottomNavigation.setOnItemReselectedListener {}
    }

    private fun observeCart() {
        viewModel?.run {
            cartCost.onEach { cartCost ->
                setCartText(cartCost)
            }.startedLaunch(this@MainActivity)
            cartProductCount.onEach { cartProductCount ->
                setCartProductCount(cartProductCount)
            }.startedLaunch(this@MainActivity)
        }
    }
}