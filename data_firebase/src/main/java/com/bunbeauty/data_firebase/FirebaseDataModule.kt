package com.bunbeauty.data_firebase

import android.content.Context
import androidx.room.Room
import com.bunbeauty.data_firebase.FirebaseLocalDatabase
import com.bunbeauty.papakarlo.BuildConfig.FB_LINK
import com.bunbeauty.papakarlo.phone_verification.IPhoneVerificationUtil
import com.bunbeauty.papakarlo.phone_verification.PhoneVerificationUtil
import com.example.data_api.ApiLocalDatabase
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import javax.inject.Singleton

@Module
class FirebaseDataModule {

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance(FB_LINK)

    @Singleton
    @Provides
    fun provideDatabase(context: Context): FirebaseLocalDatabase = Room.databaseBuilder(
        context,
        FirebaseLocalDatabase::class.java,
        "FirebaseLocalDatabase"
    ).fallbackToDestructiveMigration().build()

    //DAO

    @Singleton
    @Provides
    fun provideCartProductDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getCartProductDao()

    @Singleton
    @Provides
    fun provideOrderDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getOrderDao()

    @Singleton
    @Provides
    fun provideMenuProductDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getMenuProductDao()

    @Singleton
    @Provides
    fun provideUserAddressDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getUserAddressDao()

    @Singleton
    @Provides
    fun provideCafeDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getCafeDao()

    @Singleton
    @Provides
    fun provideDistrictDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getDistrictDao()

    @Singleton
    @Provides
    fun provideStreetDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getStreetDao()

    @Singleton
    @Provides
    fun provideUserDao(firebaseLocalDatabase: FirebaseLocalDatabase) =
        firebaseLocalDatabase.getUserDao()
}

fun firebaseDataModule() = module {
    single { FirebaseDatabase.getInstance(FB_LINK) }
    single {
        Room.databaseBuilder(
            androidContext(),
            FirebaseLocalDatabase::class.java,
            "FirebaseLocalDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<FirebaseLocalDatabase>().getCartProductDao() }
    single { get<FirebaseLocalDatabase>().getOrderDao() }
    single { get<FirebaseLocalDatabase>().getMenuProductDao() }
    single { get<FirebaseLocalDatabase>().getUserAddressDao() }
    single { get<FirebaseLocalDatabase>().getCafeDao() }
    single { get<FirebaseLocalDatabase>().getDistrictDao() }
    single { get<FirebaseLocalDatabase>().getStreetDao() }
    single { get<FirebaseLocalDatabase>().getUserDao() }
}