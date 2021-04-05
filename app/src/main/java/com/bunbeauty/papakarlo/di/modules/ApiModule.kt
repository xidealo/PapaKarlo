package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.domain.repository.api.ApiRepository
import com.bunbeauty.domain.repository.api.IApiRepository
import com.bunbeauty.data.utils.DataStoreHelper
import com.bunbeauty.data.utils.IDataStoreHelper
import com.bunbeauty.domain.network.INetworkHelper
import com.bunbeauty.domain.network.NetworkHelper
import com.bunbeauty.domain.product.IProductHelper
import com.bunbeauty.domain.product.ProductHelper
import com.bunbeauty.domain.resources.IResourcesProvider
import com.bunbeauty.domain.resources.ResourcesProvider
import com.bunbeauty.domain.string_helper.IStringHelper
import com.bunbeauty.domain.string_helper.StringHelper
import com.bunbeauty.domain.uri.IUriHelper
import com.bunbeauty.domain.uri.UriHelper
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ApiModule {

    //NETWORK

    @Binds
    abstract fun bindApiRepository(apiRepository: ApiRepository): IApiRepository

    // DATA_STORE

    @Singleton
    @Binds
    abstract fun bindDataStoreHelper(dataStoreHelper: DataStoreHelper): IDataStoreHelper

    // HELPERS

    @Binds
    abstract fun bindResourcesProvider(resourcesProvider: ResourcesProvider): IResourcesProvider

    @Binds
    abstract fun bindUriHelper(uriHelper: UriHelper): IUriHelper

    @Binds
    abstract fun bindStringHelper(stringHelper: StringHelper): IStringHelper

    @Binds
    abstract fun bindNetworkHelper(networkHelper: NetworkHelper): INetworkHelper

    @Binds
    abstract fun bindProductHelper(productHelper: ProductHelper): IProductHelper
}