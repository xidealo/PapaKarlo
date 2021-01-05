package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.papakarlo.data.api.firebase.ApiRepository
import com.bunbeauty.papakarlo.data.api.firebase.IApiRepository
import com.bunbeauty.papakarlo.data.local.datastore.DataStoreHelper
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.utils.contact_info.ContactInfoHelper
import com.bunbeauty.papakarlo.utils.contact_info.IContactInfoHelper
import com.bunbeauty.papakarlo.utils.resoures.IResourcesProvider
import com.bunbeauty.papakarlo.utils.resoures.ResourcesProvider
import com.bunbeauty.papakarlo.utils.uri.IUriHelper
import com.bunbeauty.papakarlo.utils.uri.UriHelper
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
    abstract fun bindContactInfoHelper(contactInfoHelper: ContactInfoHelper): IContactInfoHelper

    @Binds
    abstract fun bindUriHelper(uriHelper: UriHelper): IUriHelper


}