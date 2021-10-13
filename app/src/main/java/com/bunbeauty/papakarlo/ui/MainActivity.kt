package com.bunbeauty.papakarlo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.bunbeauty.papakarlo.extensions.toggleVisibility
import com.bunbeauty.papakarlo.presentation.MainViewModel
import com.bunbeauty.papakarlo.presentation.base.ViewModelFactory
import com.bunbeauty.papakarlo.ui.base.IBottomNavigationBar
import com.bunbeauty.papakarlo.ui.base.IToolbar
import com.bunbeauty.papakarlo.ui.fragment.profile.settings.SettingsFragmentDirections.toLogoutBottomSheet
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IToolbar, IBottomNavigationBar {

    private var mutableViewDataBinding: ActivityMainBinding? = null
    val viewDataBinding: ActivityMainBinding by lazy {
        checkNotNull(mutableViewDataBinding)
    }

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

        viewModel = ViewModelProvider(this, modelFactory).get(MainViewModel::class.java)

        viewModel?.connectWS()

        setupToolbar()
        setupBottomNavigationBar()

        router.attach(this, R.id.activity_main_fcv_container)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu?.size() == 0) {
            menuInflater.inflate(R.menu.top_menu, menu)
        }
        val isSettings =
            findNavController(R.id.activity_main_fcv_container).currentDestination?.id == R.id.settings_fragment
        menu?.findItem(R.id.logoutBottomSheet)?.isVisible = isSettings

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

    //google in update
    fun checkUpdates() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)

        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            // If there is an update available, prepare to promote it.
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.IMMEDIATE for immediate updates.
                    AppUpdateType.FLEXIBLE,
                    // The current activity.
                    this,
                    REQUEST_CODE
                )
            }
            // If the process of downloading is finished, start the completion flow.
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                Snackbar.make(
                    viewDataBinding.activityMainClMain,
                    resourcesProvider.getString(R.string.msg_main_activity_downloaded),
                    Snackbar.LENGTH_INDEFINITE
                ).apply {
                    setAction(
                        resourcesProvider.getString(R.string.action_main_activity_reload),
                    ) {
                        appUpdateManager.completeUpdate()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }
                    show()
                }
            }
        }.addOnFailureListener { e ->
            Log.e("Login Activity", "Failure appUpdateManager: $e")
        }
    }

    override fun setToolbarConfiguration(
        isToolbarVisible: Boolean,
        isLogoVisible: Boolean,
        isCartVisible: Boolean
    ) {
        viewDataBinding.activityMainTbToolbar.toggleVisibility(isToolbarVisible)
        viewDataBinding.activityMainIvLogo.toggleVisibility(isLogoVisible)
        viewDataBinding.activityMainClCart.toggleVisibility(isCartVisible)
        viewDataBinding.activityMainIvCart.toggleVisibility(isCartVisible)
    }

    override fun setCartText(cartText: String) {
        viewDataBinding.activityMainTvCart.text = cartText
    }

    override fun setCartProductCount(cartProductCount: String) {
        viewDataBinding.activityMainTvCartCount.text = cartProductCount
    }

    override fun setupBottomNavigationBar(isVisible: Boolean) {
        viewDataBinding.activityMainBnvBottomNavigation.toggleVisibility(isVisible)
    }

    override fun onDestroy() {
        router.detach()
        mutableViewDataBinding = null

        super.onDestroy()
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

    companion object {
        private const val REQUEST_CODE = 1
    }

}