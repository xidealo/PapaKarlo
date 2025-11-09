package com.bunbeauty.shared.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navOptions
import com.bunbeauty.shared.ui.navigation.cafelist.cafeListScreenRoute
import com.bunbeauty.shared.ui.navigation.cafelist.navigateToCafeListScreen
import com.bunbeauty.shared.ui.navigation.confirm.confirmScreenRoute
import com.bunbeauty.shared.ui.navigation.confirm.navigateToConfirmScreen
import com.bunbeauty.shared.ui.navigation.consumercart.ConsumerCartScreenDestination
import com.bunbeauty.shared.ui.navigation.consumercart.consumerCartScreenRoute
import com.bunbeauty.shared.ui.navigation.consumercart.navigateConsumerCartScreen
import com.bunbeauty.shared.ui.navigation.createaddress.createAddressScreenRoute
import com.bunbeauty.shared.ui.navigation.createaddress.navigateToCreateAddressScreenDestination
import com.bunbeauty.shared.ui.navigation.createorder.createOrderScreenRoute
import com.bunbeauty.shared.ui.navigation.createorder.navigateToCreateOrderScreen
import com.bunbeauty.shared.ui.navigation.login.loginScreenRoute
import com.bunbeauty.shared.ui.navigation.login.navigateToLoginScreen
import com.bunbeauty.shared.ui.navigation.menu.MenuScreenDestination
import com.bunbeauty.shared.ui.navigation.menu.menuScreenRoute
import com.bunbeauty.shared.ui.navigation.menu.navigateToMenuScreen
import com.bunbeauty.shared.ui.navigation.orderdetails.navigateToOrderDetailsScreen
import com.bunbeauty.shared.ui.navigation.orderdetails.orderDetailsScreenRoute
import com.bunbeauty.shared.ui.navigation.orderlist.navigateToOrderListScreen
import com.bunbeauty.shared.ui.navigation.orderlist.orderListScreenRoute
import com.bunbeauty.shared.ui.navigation.productdetails.navigateToProductDetailsScreen
import com.bunbeauty.shared.ui.navigation.productdetails.productDetailsScreenRoute
import com.bunbeauty.shared.ui.navigation.profile.ProfileScreenDestination
import com.bunbeauty.shared.ui.navigation.profile.navigateToProfileScreen
import com.bunbeauty.shared.ui.navigation.profile.profileScreenRoute
import com.bunbeauty.shared.ui.navigation.selectcity.navigateToSelectCityScreen
import com.bunbeauty.shared.ui.navigation.selectcity.selectCityScreenRoute
import com.bunbeauty.shared.ui.navigation.settings.navigateToSettingsScreen
import com.bunbeauty.shared.ui.navigation.settings.settingsScreenRoute
import com.bunbeauty.shared.ui.navigation.splash.SplashScreenDestination
import com.bunbeauty.shared.ui.navigation.splash.splashScreenRoute
import com.bunbeauty.shared.ui.navigation.update.navigateToUpdateScreen
import com.bunbeauty.shared.ui.navigation.update.updateScreenRoute
import com.bunbeauty.shared.ui.navigation.useraddresslist.navigateToUserAddressListScreen
import com.bunbeauty.shared.ui.navigation.useraddresslist.userAddressListScreenRoute

internal val emptyNavOptions = navOptions { }

fun NavGraphBuilder.foodDeliveryNavGraphBuilder(
    navController: NavController,
    showInfoMessage: (String) -> Unit,
    showErrorMessage: (String) -> Unit,
) {
    cafeListScreenRoute(
        back = navController::navigateUp,
    )

    splashScreenRoute(
        goToUpdateFragment = {
            navController.navigateToUpdateScreen(emptyNavOptions)
        },
        goToSelectCityFragment = {
            navController.navigateToSelectCityScreen(emptyNavOptions)
        },
        goToMenuFragment = {
            navController.navigateToMenuScreen(
                navOptions =
                    navOptions {
                        popUpTo(SplashScreenDestination) {
                            inclusive = true
                        }
                    },
            )
        },
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
                navOptions = emptyNavOptions,
            )
        },
        goToProfile = {
            navController.navigateToProfileScreen(emptyNavOptions)
        },
        goToConsumerCart = {
            navController.navigateConsumerCartScreen(emptyNavOptions)
        },
        showErrorMessage = showErrorMessage
    )
    productDetailsScreenRoute(
        back = navController::navigateUp,
        showErrorMessage = showErrorMessage,
        showInfoMessage = showInfoMessage
    )
    orderDetailsScreenRoute(back = navController::navigateUp)
    settingsScreenRoute(
        back = navController::navigateUp,
        showErrorMessage = showErrorMessage,
        showInfoMessage = showInfoMessage
    )
    createAddressScreenRoute(
        back = navController::navigateUp,
        showErrorMessage = showErrorMessage,
        showInfoMessage = showInfoMessage
    )
    orderListScreenRoute(
        back = navController::navigateUp,
        goToOrderDetails = { orderUuid ->
            navController.navigateToOrderDetailsScreen(
                navOptions = emptyNavOptions,
                orderUuid = orderUuid,
            )
        },
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
                successLoginDirection = successLoginDirection,
            )
        },
        goToProductFragment = {
                uuid,
                name,
                productDetailsOpenedFrom,
                additionUuidList,
                cartProductUuid,
            ->
            navController.navigateToProductDetailsScreen(
                uuid = uuid,
                name = name,
                productDetailsOpenedFrom = productDetailsOpenedFrom,
                additionUuidList = additionUuidList,
                cartProductUuid = cartProductUuid,
                navOptions = emptyNavOptions,
            )
        },
        showErrorMessage = showErrorMessage,
    )
    selectCityScreenRoute(
        goToMenuFragment = {
            navController.navigateToMenuScreen(
                navOptions =
                    navOptions {
                        popUpTo(SplashScreenDestination) {
                            inclusive = true
                        }
                    },
            )
        },
    )
    createOrderScreenRoute(
        back = navController::navigateUp,
        goToProfile = {
            navController.navigateToProfileScreen(
                navOptions =
                    navOptions {
                        popUpTo(MenuScreenDestination) {
                            inclusive = false
                        }
                    },
            )
        },
        goToCreateAddress = {
            navController.navigateToCreateAddressScreenDestination(emptyNavOptions)
        },
        showErrorMessage = showErrorMessage,
        showInfoMessage = showInfoMessage
    )
    userAddressListScreenRoute(
        back = navController::navigateUp,
        goToCreateAddress = {
            navController.navigateToCreateAddressScreenDestination(emptyNavOptions)
        },
    )
    loginScreenRoute(
        back = navController::navigateUp,
        goToConfirm = { phoneNumber, successLoginDirection ->
            navController.navigateToConfirmScreen(
                phoneNumber = phoneNumber,
                successLoginDirection = successLoginDirection,
                navOptions = emptyNavOptions,
            )
        },
        showErrorMessage = showErrorMessage,
        showInfoMessage = showInfoMessage
    )
    confirmScreenRoute(
        back = navController::navigateUp,
        goBackToProfileFragment = {
            navController.navigateToProfileScreen(
                navOptions =
                    navOptions {
                        popUpTo<ProfileScreenDestination> {
                            inclusive = true
                            saveState = false
                        }
                    },
            )
        },
        goToCreateOrderFragment = {
            navController.navigateToCreateOrderScreen(
                navOptions =
                    navOptions {
                        popUpTo(ConsumerCartScreenDestination) {
                            inclusive = false
                        }
                    },
            )
        },
        showErrorMessage = showErrorMessage,
        showInfoMessage = showInfoMessage
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
                orderUuid = orderUuid,
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
        },
    )
}
