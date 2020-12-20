package com.bunbeauty.papakarlo.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
    }

    fun setCartClickListener(clickListener: KFunction1<View, Unit>) {
        viewDataBinding.activityMainTbTopBar.partTopBarTvCart.setOnClickListener(clickListener)
    }

}