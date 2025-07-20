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
    cafeListScreenRoute(
        back = navController::navigateUp
    )

    splashScreenRoute()
    updateScreenRoute()
    menuScreenRoute(
        goToProductDetailsFragment = { uuid, name, productDetailsOpenedFrom -> },
        goToProfile = {},
        goToConsumerCart = {},
    )
    productDetailsScreenRoute(
        back = navController::navigateUp
    )
    orderDetailsScreenRoute(back = navController::navigateUp)
    settingsScreenRoute(back = navController::navigateUp)
    createAddressScreenRoute(back = navController::navigateUp)
    orderListScreenRoute(
        back = navController::navigateUp,
        goToOrderDetails = {},
    )

    consumerCartScreenRoute(
        back = navController::navigateUp,
        goToMenuFragment = {},
        goToCreateOrderFragment = {},
        goToLoginFragment = {},
        goToProductFragment = {
                uuid, name, productDetailsOpenedFrom, additionUuidList,
                cartProductUuid,
            ->

        },
    )
    selectCityScreenRoute()
    createOrderScreenRoute(
        back = navController::navigateUp,
        goToProfile = {},
        goToCreateAddress = {},
    )
    userAddressListScreenRoute(
        back = navController::navigateUp,
        goToCreateAddress = {}
    )
    loginScreenRoute(
        back = navController::navigateUp,
        goToConfirm = { phoneNumber, successLoginDirection ->

        }
    )

    confirmScreenRoute(
        back = navController::navigateUp,
        goBackToProfileFragment = {},
        goToCreateOrderFragment = {},
    )

    profileScreenRoute(
        back = navController::navigateUp,
        goToUserAddress ={},
        goToLogin ={},
        goToOrderDetailsFragment ={},
        goToOrdersFragment ={},
        goToSettingsFragment ={},
        goToAboutAppBottomSheet ={},
        goToCafeListFragment ={},
    )
}


/*
*
* TODO сделать ботом щиты
*  <dialog
        android:id="@+id/cafeOptionsBottomSheet"
        android:name="com.bunbeauty.papakarlo.feature.cafe.screen.cafeoptions.CafeOptionsBottomSheet"
        android:label="CafeOptionsBottomSheet">
        <argument
            android:name="cafeUuid"
            app:argType="string" />
    </dialog>

    <!--Не нужно создавать навигацию (часть экрана)-->

    <dialog
        android:id="@+id/feedbackBottomSheet"
        android:name="com.bunbeauty.papakarlo.feature.profile.screen.feedback.FeedbackBottomSheet"
        android:label="FeedbackBottomSheet" />
    <!--Не нужно создавать навигацию (часть экрана)-->

    <dialog
        android:id="@+id/aboutAppBottomSheet"
        android:name="com.bunbeauty.papakarlo.feature.profile.screen.aboutapp.AboutAppBottomSheet"
        android:label="AboutAppBottomSheet" />

    <!--Не нужно создавать навигацию (часть экрана)-->

    <dialog
        android:id="@+id/logoutBottomSheet"
        android:name="com.bunbeauty.papakarlo.feature.profile.screen.logout.LogoutBottomSheet"
        android:label="LogoutBottomSheet">
        <action
            android:id="@+id/back_to_profileFragment"
            app:destination="@id/profileFragment"
            app:popUpTo="@id/profileFragment"
            app:popUpToInclusive="true" />
    </dialog>
*
*
*
* */