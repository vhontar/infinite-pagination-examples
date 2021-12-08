package com.easycoding.pagination.di

import android.content.Context
import com.easycoding.pagination.business.data.network.abstraction.RecipeNetwork
import com.easycoding.pagination.business.data.network.implementation.RecipeNetworkImpl
import com.easycoding.pagination.datasource.network.NetworkService
import com.easycoding.pagination.datasource.network.abstraction.RecipeNetworkDataSource
import com.easycoding.pagination.datasource.network.implementation.RecipeNetworkDataSourceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        // it's not a good way to hardcode them here.
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url
                val newHttpUrl = originalHttpUrl
                    .newBuilder()
                    .addQueryParameter("app_id", "0f0f4f98")
                    .addQueryParameter("app_key", "c5111ee612503305c45631ceef2763a7")
                    .build()
                chain.proceed(original.newBuilder().url(newHttpUrl).build())
            }
            .addInterceptor(interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl("https://api.edamam.com")
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideNetworkService(retrofit: Retrofit): NetworkService = retrofit.create(NetworkService::class.java)

    @Singleton
    @Provides
    fun provideRecipeNetworkDataSource(
        networkService: NetworkService,
        @ApplicationContext context: Context
    ): RecipeNetworkDataSource =
        RecipeNetworkDataSourceImpl(networkService, context)

    @Singleton
    @Provides
    fun provideRecipeNetwork(
        recipeNetworkDataSource: RecipeNetworkDataSource
    ): RecipeNetwork = RecipeNetworkImpl(recipeNetworkDataSource)
}