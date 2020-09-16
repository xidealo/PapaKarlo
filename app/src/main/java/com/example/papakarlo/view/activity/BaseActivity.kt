package com.example.papakarlo.view.activity

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity:AppCompatActivity() {

    fun buildDagger(){

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0,0)
    }
}