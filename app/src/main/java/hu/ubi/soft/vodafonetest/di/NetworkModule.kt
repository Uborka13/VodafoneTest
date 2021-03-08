package hu.ubi.soft.vodafonetest.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.ubi.soft.vodafonetest.BuildConfig
import hu.ubi.soft.vodafonetest.network.Services
import hu.ubi.soft.vodafonetest.util.UserCredManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CONNECT_TIMEOUT: Long = 60
    private const val READ_TIMEOUT: Long = 60
    private const val WRITE_TIMEOUT: Long = 60

    @Provides
    fun provideServices(retrofit: Retrofit): Services {
        return retrofit.create(Services::class.java)
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun provideHeaderInterceptor(userCredManager: UserCredManager): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader("Content-Type", "application/json")
            requestBuilder.addHeader("Accept", "application/json")
            userCredManager.getAccessToken()?.let {
                requestBuilder.addHeader("Authorization", "Bearer $it")
            }
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    fun provideHttpClient(
        headerInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(headerInterceptor) // TODO Nem szükséges most, de én így szoktam header-t bele rakni a kérésbe
        httpClient.addInterceptor(httpLoggingInterceptor)
        httpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        httpClient.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(httpClient)
            .baseUrl(BuildConfig.SERVER_URL)
            .build()
    }

}