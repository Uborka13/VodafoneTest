package hu.ubi.soft.vodafonetest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.ubi.soft.vodafonetest.network.Services
import hu.ubi.soft.vodafonetest.repositories.Repository
import hu.ubi.soft.vodafonetest.util.UserCredManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRepository(services: Services, userCredManager: UserCredManager): Repository {
        return Repository(services, userCredManager)
    }

    @Provides
    @Singleton
    fun provideUserCredManager(@ApplicationContext context: Context): UserCredManager {
        return UserCredManager(context)
    }

}