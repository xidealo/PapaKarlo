package com.bunbeauty.papakarlo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navOptions
import com.bunbeauty.papakarlo.navigation.cafelist.cafeListScreenRoute
import com.bunbeauty.papakarlo.navigation.cafelist.navigateToCafeListScreen
import com.bunbeauty.papakarlo.navigation.confirm.confirmScreenRoute
import com.bunbeauty.papakarlo.navigation.confirm.navigateToConfirmScreen
import com.bunbeauty.papakarlo.navigation.consumercart.ConsumerCartScreenDestination
import com.bunbeauty.papakarlo.navigation.consumercart.consumerCartScreenRoute
import com.bunbeauty.papakarlo.navigation.consumercart.navigateConsumerCartScreen
import com.bunbeauty.papakarlo.navigation.createaddress.createAddressScreenRoute
import com.bunbeauty.papakarlo.navigation.createaddress.navigateToCreateAddressScreenDestination
import com.bunbeauty.papakarlo.navigation.createorder.createOrderScreenRoute
import com.bunbeauty.papakarlo.navigation.createorder.navigateToCreateOrderScreen
import com.bunbeauty.papakarlo.navigation.login.loginScreenRoute
import com.bunbeauty.papakarlo.navigation.login.navigateToLoginScreen
import com.bunbeauty.papakarlo.navigation.menu.MenuScreenDestination
import com.bunbeauty.papakarlo.navigation.menu.menuScreenRoute
import com.bunbeauty.papakarlo.navigation.menu.navigateToMenuScreen
import com.bunbeauty.papakarlo.navigation.orderdetails.navigateToOrderDetailsScreen
import com.bunbeauty.papakarlo.navigation.orderdetails.orderDetailsScreenRoute
import com.bunbeauty.papakarlo.navigation.orderlist.navigateToOrderListScreen
import com.bunbeauty.papakarlo.navigation.orderlist.orderListScreenRoute
import com.bunbeauty.papakarlo.navigation.productdetails.navigateToProductDetailsScreen
import com.bunbeauty.papakarlo.navigation.productdetails.productDetailsScreenRoute
import com.bunbeauty.papakarlo.navigation.profile.ProfileScreenDestination
import com.bunbeauty.papakarlo.navigation.profile.navigateToProfileScreen
import com.bunbeauty.papakarlo.navigation.profile.profileScreenRoute
import com.bunbeauty.papakarlo.navigation.selectcity.navigateToSelectCityScreen
import com.bunbeauty.papakarlo.navigation.selectcity.selectCityScreenRoute
import com.bunbeauty.papakarlo.navigation.settings.navigateToSettingsScreen
import com.bunbeauty.papakarlo.navigation.settings.settingsScreenRoute
import com.bunbeauty.papakarlo.navigation.splash.splashScreenRoute
import com.bunbeauty.papakarlo.navigation.update.navigateToUpdateScreen
import com.bunbeauty.papakarlo.navigation.update.updateScreenRoute
import com.bunbeauty.papakarlo.navigation.useraddresslist.navigateToUserAddressListScreen
import com.bunbeauty.papakarlo.navigation.useraddresslist.userAddressListScreenRoute

internal val emptyNavOptions = navOptions { }

fun NavGraphBuilder.foodDeliveryNavGraphBuilder(
    navController: NavController,
) {
    cafeListScreenRoute(
        back = navController::navigateUp
    )

    splashScreenRoute(
        goToUpdateFragment = {
            navController.navigateToUpdateScreen(emptyNavOptions)
        },
        goToSelectCityFragment = {
            navController.navigateToSelectCityScreen(emptyNavOptions)
        },
        goToMenuFragment = {
            navController.navigateToMenuScreen(emptyNavOptions)
        }
    )

    updateScreenRoute()

    menuScreenRoute(
        goToProductDetailsFragment = { uuid, name, productDetailsOpenedFrom ->
            navController.navigateToProductDetailsScreen(
                uuid = uuid,
                name = name,
                productDetailsOpenedFrom = productDetailsOpenedFrom,
                additionUuidList = emptyList(),
                cartProductUuid = null,
                navOptions = emptyNavOptions
            )
        },
        goToProfile = {
            navController.navigateToProfileScreen(emptyNavOptions)
        },
        goToConsumerCart = {
            navController.navigateConsumerCartScreen(emptyNavOptions)
        }
    )
    productDetailsScreenRoute(
        back = navController::navigateUp
    )
    orderDetailsScreenRoute(back = navController::navigateUp)
    settingsScreenRoute(back = navController::navigateUp)
    createAddressScreenRoute(back = navController::navigateUp)
    orderListScreenRoute(
        back = navController::navigateUp,
        goToOrderDetails = { orderUuid ->
            navController.navigateToOrderDetailsScreen(
                navOptions = emptyNavOptions,
                orderUuid = orderUuid
            )
        }
    )

    consumerCartScreenRoute(
        back = navController::navigateUp,
        goToMenuFragment = navController::navigateUp,
        goToCreateOrderFragment = {
            navController.navigateToCreateOrderScreen(emptyNavOptions)
        },
        goToLoginFragment = { successLoginDirection ->
            navController.navigateToLoginScreen(
                navOptions = emptyNavOptions,
                successLoginDirection = successLoginDirection
            )
        },
        goToProductFragment = {
                uuid, name, productDetailsOpenedFrom, additionUuidList,
                cartProductUuid,
            ->
            navController.navigateToProductDetailsScreen(
                uuid = uuid,
                name = name,
                productDetailsOpenedFrom = productDetailsOpenedFrom,
                additionUuidList = additionUuidList,
                cartProductUuid = cartProductUuid,
                navOptions = emptyNavOptions
            )
        }
    )
    selectCityScreenRoute(
        goToMenuFragment = {
            navController.navigateToMenuScreen(emptyNavOptions)
        }
    )
    createOrderScreenRoute(
        back = navController::navigateUp,
        goToProfile = {
            navController.navigateToProfileScreen(
                navOptions = navOptions {
                    popUpTo(MenuScreenDestination) {
                        inclusive = false
                    }
                }
            )
        },
        goToCreateAddress = {
            navController.navigateToCreateAddressScreenDestination(emptyNavOptions)
        }
    )
    userAddressListScreenRoute(
        back = navController::navigateUp,
        goToCreateAddress = {
            navController.navigateToCreateAddressScreenDestination(emptyNavOptions)
        }
    )
    loginScreenRoute(
        back = navController::navigateUp,
        goToConfirm = { phoneNumber, successLoginDirection ->
            navController.navigateToConfirmScreen(
                phoneNumber = phoneNumber,
                successLoginDirection = successLoginDirection,
                navOptions = emptyNavOptions
            )
        }
    )
    confirmScreenRoute(
        back = navController::navigateUp,
        goBackToProfileFragment = {
            navController.navigateToProfileScreen(
                navOptions = navOptions {
                    popUpTo<ProfileScreenDestination> {
                        inclusive = true
                        saveState = false
                    }
                }
            )
        },
        goToCreateOrderFragment = {
            navController.navigateToCreateOrderScreen(
                navOptions = navOptions {
                    popUpTo(ConsumerCartScreenDestination) {
                        inclusive = false
                    }
                }
            )
        }
    )

    profileScreenRoute(
        back = navController::navigateUp,
        goToUserAddress = {
            navController.navigateToUserAddressListScreen(emptyNavOptions)
        },
        goToLogin = { successLoginDirection ->
            navController.navigateToLoginScreen(emptyNavOptions, successLoginDirection)
        },
        goToOrderDetailsFragment = { orderUuid ->
            navController.navigateToOrderDetailsScreen(
                navOptions = emptyNavOptions,
                orderUuid = orderUuid
            )
        },
        goToOrdersFragment = {
            navController.navigateToOrderListScreen(emptyNavOptions)
        },
        goToSettingsFragment = {
            navController.navigateToSettingsScreen(emptyNavOptions)
        },
        goToCafeListFragment = {
            navController.navigateToCafeListScreen(emptyNavOptions)
        }
    )
}
