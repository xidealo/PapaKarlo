package com.bunbeauty.papakarlo.di.modules

import com.bunbeauty.data.AuthRepository
import com.bunbeauty.data.DataStoreRepository
import com.bunbeauty.domain.repo.AuthRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.VersionRepo
import com.bunbeauty.domain.util.validator.ITextValidator
import com.bunbeauty.domain.util.validator.TextValidator
import com.bunbeauty.papakarlo.ResourcesProvider
import com.bunbeauty.presentation.util.network.INetworkHelper
import com.bunbeauty.presentation.util.network.NetworkHelper
import com.bunbeauty.presentation.util.resources.IResourcesProvider
import com.example.data_api.repository.VersionRepository
import dagger.Binds
import dagger.Module
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import javax.inject.Singleton

@Module
interface UtilModule {

    @Singleton
    @Binds
    fun bindDataStoreRepository(dataStoreRepository: DataStoreRepository): DataStoreRepo

    @Singleton
    @Binds
    fun bindRemoteConfigRepository(remoteConfigRepository: VersionRepository): VersionRepo

    // HELPERS

    @Binds
    fun bindResourcesProvider(resourcesProvider: ResourcesProvider): IResourcesProvider

    @Binds
    fun bindNetworkHelper(networkHelper: NetworkHelper): INetworkHelper

    @Binds
    fun bindFieldHelper(fieldHelper: TextValidator): ITextValidator

    @Binds
    fun bindAuthUtil(authRepository: AuthRepository): AuthRepo
}

fun utilModule() = module {
    single { DataStoreRepository(androidContext()) } bind DataStoreRepo::class
    single<VersionRepo> { VersionRepository(get()) }
    single { ResourcesProvider(androidContext()) } bind IResourcesProvider::class
    single { TextValidator() } bind ITextValidator::class
    single { AuthRepository(firebaseAuth = get()) } bind AuthRepo::class

}
