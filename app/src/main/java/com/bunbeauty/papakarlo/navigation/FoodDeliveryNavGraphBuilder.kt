package com.bunbeauty.papakarlo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navOptions
import com.bunbeauty.papakarlo.navigation.cafelist.cafeListScreenRoute
import com.bunbeauty.papakarlo.navigation.confirm.confirmScreenRoute
import com.bunbeauty.papakarlo.navigation.consumercart.consumerCartScreenRoute
import com.bunbeauty.papakarlo.navigation.createaddress.createAddressScreenRoute
import com.bunbeauty.papakarlo.navigation.createorder.createOrderScreenRoute
import com.bunbeauty.papakarlo.navigation.login.loginScreenRoute
import com.bunbeauty.papakarlo.navigation.menu.menuScreenRoute
import com.bunbeauty.papakarlo.navigation.orderdetails.orderDetailsScreenRoute
import com.bunbeauty.papakarlo.navigation.orderlist.orderListScreenRoute
import com.bunbeauty.papakarlo.navigation.productdetails.productDetailsScreenRoute
import com.bunbeauty.papakarlo.navigation.profile.profileScreenRoute
import com.bunbeauty.papakarlo.navigation.selectcity.selectCityScreenRoute
import com.bunbeauty.papakarlo.navigation.settings.settingsScreenRoute
import com.bunbeauty.papakarlo.navigation.splash.splashScreenRoute
import com.bunbeauty.papakarlo.navigation.update.updateScreenRoute
import com.bunbeauty.papakarlo.navigation.useraddresslist.userAddressListScreenRoute

internal val emptyNavOptions = navOptions { }

fun NavGraphBuilder.foodDeliveryNavGraphBuilder(
    navController: NavController,
) {
    cafeListScreenRoute(back = {})
    splashScreenRoute()
    updateScreenRoute()
    menuScreenRoute(
        goToProductDetailsFragment = { uuid, name, productDetailsOpenedFrom -> },
        goToProfile = {},
        goToConsumerCart = {},
    )
    productDetailsScreenRoute(
        back = {}
    )
    orderDetailsScreenRoute(back = {})
    settingsScreenRoute(back = {})
    createAddressScreenRoute(back = {})
    orderListScreenRoute(
        back = {},
        goToOrderDetails = {},
    )

    confirmScreenRoute()

    consumerCartScreenRoute()
    createOrderScreenRoute()
    loginScreenRoute()
    profileScreenRoute()
    selectCityScreenRoute()
    userAddressListScreenRoute()

}
