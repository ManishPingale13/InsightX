package com.example.insightx.di

import com.example.insightx.data.retrofit.api.AuthApi
import com.example.insightx.data.retrofit.api.MachineRecordApi
import com.example.insightx.data.retrofit.repository.AuthRepoImpl
import com.example.insightx.data.retrofit.repository.MachineRecordRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
//            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(MachineRecordApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideMachineRecordApi(retrofit: Retrofit): MachineRecordApi =
        retrofit.create(MachineRecordApi::class.java)

    @Provides
    @Singleton
    fun provideMachineRecordRepo(recordApi: MachineRecordApi): MachineRecordRepoImpl =
        MachineRecordRepoImpl(recordApi)

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideAuthRepo(authApi: AuthApi): AuthRepoImpl =
        AuthRepoImpl(authApi)

}