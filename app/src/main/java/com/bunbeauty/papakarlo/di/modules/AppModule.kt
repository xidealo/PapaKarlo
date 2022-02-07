package com.bunbeauty.papakarlo.di.modules

import android.content.Context
import android.net.ConnectivityManager
import androidx.work.WorkManager
import coil.imageLoader
import coil.request.ImageRequest
import com.bunbeauty.papakarlo.Router
import com.bunbeauty.papakarlo.common.decorator.MarginItemHorizontalDecoration
import com.bunbeauty.papakarlo.common.decorator.MarginItemVerticalDecoration
import com.bunbeauty.papakarlo.feature.address.AddressAdapter
import com.bunbeauty.papakarlo.feature.cafe.cafe_list.CafeAdapter
import com.bunbeauty.papakarlo.feature.consumer_cart.CartProductAdapter
import com.bunbeauty.papakarlo.feature.menu.category.CategoryAdapter
import com.bunbeauty.papakarlo.feature.menu.menu_product.MenuProductAdapter
import com.bunbeauty.papakarlo.feature.profile.order.order_details.OrderProductAdapter
import com.bunbeauty.papakarlo.feature.profile.order.order_list.OrderAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun appModule() = module {
    single { androidContext().resources }
    single { Router() }
    single { androidContext().imageLoader }
    single { ImageRequest.Builder(androidContext()) }
    single { WorkManager.getInstance(androidContext()) }
    single { androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    single { CategoryAdapter(resourcesProvider = get()) }
    single { MenuProductAdapter() }
    single { CartProductAdapter() }
    single { OrderAdapter() }
    single { OrderProductAdapter() }
    single { CafeAdapter() }
    single { AddressAdapter() }
    single { MarginItemHorizontalDecoration(resourcesProvider = get()) }
    single { MarginItemVerticalDecoration(resourcesProvider = get()) }
}