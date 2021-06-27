package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.data.repository.ApiRepository
import com.bunbeauty.data.repository.DataStoreRepository
import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.util.field_helper.FieldHelper
import com.bunbeauty.domain.util.field_helper.IFieldHelper
import com.bunbeauty.domain.util.network.INetworkHelper
import com.bunbeauty.domain.util.network.NetworkHelper
import com.bunbeauty.domain.util.order.IOrderUtil
import com.bunbeauty.domain.util.order.OrderUtil
import com.bunbeauty.domain.util.product.IProductHelper
import com.bunbeauty.domain.util.product.ProductHelper
import com.bunbeauty.domain.util.resources.IResourcesProvider
import com.bunbeauty.domain.util.resources.ResourcesProvider
import com.bunbeauty.domain.util.string_helper.IStringHelper
import com.bunbeauty.domain.util.string_helper.StringHelper
import com.bunbeauty.domain.util.uri.IUriHelper
import com.bunbeauty.domain.util.uri.UriHelper
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ApiModule {

    //NETWORK

    @Binds
    abstract fun bindApiRepository(apiRepository: ApiRepository): ApiRepo

    // DATA_STORE

    @Singleton
    @Binds
    abstract fun bindDataStoreHelper(dataStoreRepository: DataStoreRepository): DataStoreRepo

    // HELPERS

    @Binds
    abstract fun bindResourcesProvider(resourcesProvider: ResourcesProvider): IResourcesProvider

    @Binds
    abstract fun bindUriHelper(uriHelper: UriHelper): IUriHelper

    @Binds
    abstract fun bindStringHelper(stringHelper: StringHelper): IStringHelper

    @Binds
    abstract fun bindOrderUtil(orderUtil: OrderUtil): IOrderUtil

    @Binds
    abstract fun bindNetworkHelper(networkHelper: NetworkHelper): INetworkHelper

    @Binds
    abstract fun bindProductHelper(productHelper: ProductHelper): IProductHelper

    @Binds
    abstract fun bindFieldHelper(fieldHelper: FieldHelper): IFieldHelper
}