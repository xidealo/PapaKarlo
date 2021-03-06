package com.bunbeauty.papakarlo.ui.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bunbeauty.papakarlo.PapaKarloApplication
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.ActivityMainBinding
import com.bunbeauty.papakarlo.view_model.MainViewModel
import com.bunbeauty.papakarlo.view_model.base.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import kotlin.reflect.KFunction1

class MainActivity : AppCompatActivity() {

    lateinit var viewDataBinding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var modelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
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
        viewModel.getDiscounts()
        viewModel.cartLiveData.observe(this) { cartText ->
            viewDataBinding.activityMainTbTopBar.partTopBarTvCart.text = cartText
        }

        val navController =
            (supportFragmentManager.findFragmentById(R.id.activity_main_fcv_container) as NavHostFragment).findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        viewDataBinding.activityMainTbTopBar.partTbTopBar.setupWithNavController(
            navController,
            appBarConfiguration
        )

        // Uploading menu products to FB
        // viewModel.saveMenu(resources.getStringArray(R.array.menu_arr).asList())
    }

    fun setCartClickListener(clickListener: KFunction1<View, Unit>) {
        viewDataBinding.activityMainTbTopBar.partTopBarTvCart.setOnClickListener(clickListener)
    }

    fun setTopBarTitle(title: String) {
        viewDataBinding.activityMainTbTopBar.partTbTopBar.title = title
    }

    fun setTopBarImage(drawableId: Int) {
        viewDataBinding.activityMainTbTopBar.partTopBarIvImage.setImageResource(drawableId)
    }

    fun showMessage(message: String) {
        val snack = Snackbar.make(viewDataBinding.root, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.colorPrimary))
            .setTextColor(ContextCompat.getColor(this, R.color.white))
            .setActionTextColor(ContextCompat.getColor(this, R.color.white))
        snack.view.findViewById<TextView>(R.id.snackbar_text).textAlignment =
            View.TEXT_ALIGNMENT_CENTER
        snack.show()
    }

    fun showError(messageError: String) {
        val snack = Snackbar.make(viewDataBinding.root, messageError, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.errorColor))
            .setTextColor(ContextCompat.getColor(this, R.color.white))
            .setActionTextColor(ContextCompat.getColor(this, R.color.white))
        snack.view.findViewById<TextView>(R.id.snackbar_text).textAlignment =
            View.TEXT_ALIGNMENT_CENTER
        snack.show()
    }

}