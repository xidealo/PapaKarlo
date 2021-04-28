package com.bunbeauty.papakarlo.ui.base

interface IToolbar {
    fun setToolbarConfiguration(
        isVisible: Boolean,
        isLogoVisible: Boolean,
        isCartProductVisible: Boolean
    )

    fun setCartText(cartText: String)
}