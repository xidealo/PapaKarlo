package com.bunbeauty.papakarlo.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bunbeauty.domain.util.resources.IResourcesProvider
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
import com.example.shared.Greeting
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import javax.inject.Inject


class MainActivity : AppCompatActivity(), IToolbar, IBottomNavigationBar {

    lateinit var viewDataBinding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var modelFactory: ViewModelFactory

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var iResourcesProvider: IResourcesProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        (application as PapaKarloApplication).appComponent
            .getViewModelComponent()
            .create(this)
            .inject(this)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.executePendingBindings()

        viewModel = ViewModelProvider(this, modelFactory).get(MainViewModel::class.java)
        viewModel.refreshCafeList()
        viewModel.refreshMenuProducts()
        viewModel.refreshDeliveryInfo()
        viewModel.refreshUserInfo()

        setupToolbar()
        setupBottomNavigationBar()

        router.attach(this, R.id.activity_main_fcv_container)
        Log.d("Login Activity", "Hello from shared module: " + (Greeting().greeting()))

        checkUpdates()
/*
        AppUpdater(this)
            .setDisplay(Display.DIALOG)
            .setCancelable(false)
            .setUpdateFrom(UpdateFrom.GITHUB)
            .setGitHubUserAndRepo("xidealo", "PapaKarlo")
            .start()
*/

        // Uploading menu products to FB
        // viewModel.saveMenu(resources.getStringArray(R.array.menu_arr).asList())
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
                    iResourcesProvider.getString(R.string.msg_main_activity_downloaded),
                    Snackbar.LENGTH_INDEFINITE
                ).apply {
                    setAction(
                        iResourcesProvider.getString(R.string.action_main_activity_reload),
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
        viewDataBinding.activityMainTvCart.toggleVisibility(isCartVisible)
    }

    override fun setCartText(cartText: String) {
        viewDataBinding.activityMainTvCart.text = cartText
    }

    override fun setupBottomNavigationBar(isVisible: Boolean) {
        viewDataBinding.activityMainBnvBottomNavigation.toggleVisibility(isVisible)
    }

    override fun onDestroy() {
        router.detach()

        super.onDestroy()
    }

    private fun setupToolbar() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.activity_main_fcv_container) as NavHostFragment).findNavController()

        setSupportActionBar(viewDataBinding.activityMainTbToolbar)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.cafe_list_fragment, R.id.menu_fragment, R.id.profile_fragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        viewDataBinding.activityMainTvCart.setOnClickListener {
            if (findNavController(R.id.activity_main_fcv_container).currentDestination?.id != R.id.cart_fragment) {
                router.navigate(globalToCartFragment())
            }
        }
        viewDataBinding.activityMainTbToolbar.setNavigationOnClickListener {
            router.navigateUp()
        }
    }

    private fun setupBottomNavigationBar() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.activity_main_fcv_container) as NavHostFragment).findNavController()

        viewDataBinding.activityMainBnvBottomNavigation.setupWithNavController(navController)
        viewDataBinding.activityMainBnvBottomNavigation.setOnNavigationItemReselectedListener {}
    }

    companion object {
        private const val REQUEST_CODE = 1
    }

}