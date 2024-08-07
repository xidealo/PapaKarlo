package com.bunbeauty.shared.data.di

import com.bunbeauty.shared.data.dao.addition.AdditionDao
import com.bunbeauty.shared.data.dao.addition.IAdditionDao
import com.bunbeauty.shared.data.dao.addition_group.AdditionGroupDao
import com.bunbeauty.shared.data.dao.addition_group.IAdditionGroupDao
import com.bunbeauty.shared.data.dao.cafe.CafeDao
import com.bunbeauty.shared.data.dao.cafe.ICafeDao
import com.bunbeauty.shared.data.dao.cart_product.CartProductDao
import com.bunbeauty.shared.data.dao.cart_product.ICartProductDao
import com.bunbeauty.shared.data.dao.cart_product_addition.CartProductAdditionDao
import com.bunbeauty.shared.data.dao.cart_product_addition.ICartProductAdditionDao
import com.bunbeauty.shared.data.dao.category.CategoryDao
import com.bunbeauty.shared.data.dao.category.ICategoryDao
import com.bunbeauty.shared.data.dao.city.CityDao
import com.bunbeauty.shared.data.dao.city.ICityDao
import com.bunbeauty.shared.data.dao.link.ILinkDao
import com.bunbeauty.shared.data.dao.link.LinkDao
import com.bunbeauty.shared.data.dao.menu_product.IMenuProductDao
import com.bunbeauty.shared.data.dao.menu_product.MenuProductDao
import com.bunbeauty.shared.data.dao.menu_product_category_reference.IMenuProductCategoryReferenceDao
import com.bunbeauty.shared.data.dao.menu_product_category_reference.MenuProductCategoryReferenceDao
import com.bunbeauty.shared.data.dao.order.IOrderDao
import com.bunbeauty.shared.data.dao.order.OrderDao
import com.bunbeauty.shared.data.dao.order_addition.IOrderAdditionDao
import com.bunbeauty.shared.data.dao.order_addition.OrderAdditionDao
import com.bunbeauty.shared.data.dao.order_product.IOrderProductDao
import com.bunbeauty.shared.data.dao.order_product.OrderProductDao
import com.bunbeauty.shared.data.dao.payment_method.IPaymentMethodDao
import com.bunbeauty.shared.data.dao.payment_method.PaymentMethodDao
import com.bunbeauty.shared.data.dao.user.IUserDao
import com.bunbeauty.shared.data.dao.user.UserDao
import com.bunbeauty.shared.data.dao.user_address.IUserAddressDao
import com.bunbeauty.shared.data.dao.user_address.UserAddressDao
import org.koin.dsl.module

fun databaseModule() = module {
    single<ICafeDao> {
        CafeDao(foodDeliveryDatabase = get())
    }
    single<ICartProductDao> {
        CartProductDao(foodDeliveryDatabase = get())
    }
    single<ICityDao> {
        CityDao(foodDeliveryDatabase = get())
    }
    single<IMenuProductDao> {
        MenuProductDao(foodDeliveryDatabase = get())
    }
    single<ICategoryDao> {
        CategoryDao(foodDeliveryDatabase = get())
    }
    single<IMenuProductCategoryReferenceDao> {
        MenuProductCategoryReferenceDao(foodDeliveryDatabase = get())
    }
    single<IUserDao> {
        UserDao(foodDeliveryDatabase = get())
    }
    single<IUserAddressDao> {
        UserAddressDao(foodDeliveryDatabase = get())
    }
    single<IOrderDao> {
        OrderDao(foodDeliveryDatabase = get())
    }
    single<IPaymentMethodDao> {
        PaymentMethodDao(foodDeliveryDatabase = get())
    }
    single<ILinkDao> {
        LinkDao(foodDeliveryDatabase = get())
    }
    single<ICartProductAdditionDao> {
        CartProductAdditionDao(foodDeliveryDatabase = get())
    }
    single<IOrderProductDao> {
        OrderProductDao(foodDeliveryDatabase = get())
    }
    single<IAdditionDao> {
        AdditionDao(foodDeliveryDatabase = get())
    }
    single<IOrderAdditionDao> {
        OrderAdditionDao(foodDeliveryDatabase = get())
    }
    single<IAdditionGroupDao> {
        AdditionGroupDao(foodDeliveryDatabase = get())
    }
}
