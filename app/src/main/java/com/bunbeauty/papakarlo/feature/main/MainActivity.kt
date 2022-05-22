package com.bunbeauty.papakarlo.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bunbeauty.papakarlo.NavMainDirections.globalConsumerCartFragment
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.papakarlo.common.ui.element.OverflowingText
import com.bunbeauty.papakarlo.common.ui.theme.FoodDeliveryTheme
import com.bunbeauty.papakarlo.common.ui.theme.largeRoundedCornerShape
import com.bunbeauty.papakarlo.databinding.ActivityMainBinding
import com.bunbeauty.papakarlo.extensions.startedLaunch
import com.bunbeauty.papakarlo.extensions.showOrGone
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
        R.id.updateFragment,
        R.id.selectCityFragment,
        R.id.cafeListFragment,
        R.id.confirmFragment,
        R.id.consumerCartFragment,
        R.id.createAddressFragment,
        R.id.createOrderFragment,
        R.id.loginFragment,
        R.id.menuFragment,
        R.id.orderDetailsFragment,
        R.id.orderListFragment,
        R.id.productFragment,
        R.id.profileFragment,
        R.id.userAddressListFragment,
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
                viewBinding.activityMainTbToolbar.showOrGone(isToolbarVisible)
                val isLogoVisible = destination.id in logoFragmentIdList
                viewBinding.activityMainIvLogo.showOrGone(isLogoVisible)
                val isCartVisible = destination.id in cartFragmentIdList
                viewBinding.activityMainClCart.showOrGone(isCartVisible)
                viewBinding.activityMainIvCart.showOrGone(isCartVisible)
                val isBottomNavigationVisible = destination.id in bottomNavigationFragmentIdList
                viewBinding.activityMainBnvBottomNavigation.showOrGone(
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
            viewBinding.activityMainTvInternetWarning.showOrGone(!isOnline)
        }
    }

    private inline fun <T> Flow<T>.startedLaunch(crossinline block: suspend (T) -> Unit) {
        startedLaunch(this@MainActivity, block)
    }

    @Composable
    private fun TopBar(modifier: Modifier = Modifier) {
        TopAppBar(
            modifier = modifier,
            title = {
                OverflowingText(
                    text = "Title",
                    style = FoodDeliveryTheme.typography.h1,
                    color = FoodDeliveryTheme.colors.onSurface
                )
            },
            navigationIcon = {
                IconButton(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(largeRoundedCornerShape)
                        .background(FoodDeliveryTheme.colors.done),
                    onClick = { }
                ) {
                    Icon(
                        modifier = Modifier.padding(FoodDeliveryTheme.dimensions.mediumSpace),
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = stringResource(R.string.description_back_icon)
                    )
                }
            },
            backgroundColor = FoodDeliveryTheme.colors.surface,
            contentColor = FoodDeliveryTheme.colors.onSurface,
            elevation = FoodDeliveryTheme.dimensions.elevation
        )
    }

    @Preview
    @Composable
    private fun TopBarPreview() {
        TopBar()
    }

}