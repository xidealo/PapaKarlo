package com.bunbeauty.papakarlo.di.modules

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkManager
import coil.ImageLoader
import coil.imageLoader
import coil.request.ImageRequest
import com.bunbeauty.papakarlo.di.components.ViewModelComponent
import dagger.Module
import dagger.Provides
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@Module(subcomponents = [ViewModelComponent::class])
class AppModule {

    @Provides
    fun provideResources(context: Context): Resources = context.resources

    @Provides
    fun provideImageLoader(context: Context): ImageLoader = context.imageLoader

    @Provides
    fun provideImageRequestBuilder(context: Context): ImageRequest.Builder =
        ImageRequest.Builder(context)

    @Provides
    fun provideWorkManager(context: Context): WorkManager = WorkManager.getInstance(context)

    @Provides
    fun provideConnectivityManager(context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

}

fun appModule() = module {
    single { androidContext().resources }
    single { androidContext().imageLoader }
    single { ImageRequest.Builder(androidContext()) }
    single { WorkManager.getInstance(androidContext()) }
    single { androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
}