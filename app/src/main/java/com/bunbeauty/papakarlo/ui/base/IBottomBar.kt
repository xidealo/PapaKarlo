package com.bunbeauty.papakarlo.ui.base

import android.view.MenuItem
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.databinding.PartBottomPanelBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


interface IBottomBar : BottomNavigationView.OnNavigationItemSelectedListener {

    var bottomBarBinding: PartBottomPanelBinding

    fun initBottomPanel(selectedItemId: Int = -1) {
        updateBottomPanel(selectedItemId)
        bottomBarBinding.partBottomBarTb.setOnNavigationItemSelectedListener(this)
    }

    fun updateBottomPanel(selectedItemId: Int = -1) {
        bottomBarBinding.partBottomBarTb.selectedItemId = selectedItemId
        bottomBarBinding.partBottomBarTb.menu.findItem(selectedItemId)?.isEnabled = false
        if (selectedItemId == -1) {
            bottomBarBinding.partBottomBarTb.menu.setGroupCheckable(0, false, true)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_contacts -> {
                goToContacts()
            }
            R.id.navigation_menu -> {
                goToMenu()
            }
            R.id.navigation_orders -> {
                goToOrders()
            }
        }

        return true
    }

    fun goToContacts()

    fun goToMenu()

    fun goToOrders()
}
