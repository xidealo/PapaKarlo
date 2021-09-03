package com.bunbeauty.papakarlo.ui.base

interface IToolbar {
    fun setToolbarConfiguration(
        isToolbarVisible: Boolean,
        isLogoVisible: Boolean,
        isCartVisible: Boolean
    )

    fun setCartText(cartText: String)
    fun setCartProductCount(cartProductCount: String)
}