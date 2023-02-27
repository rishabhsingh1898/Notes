package com.example.notesapp.di

import com.example.notesapp.api.AuthInterceptor
import com.example.notesapp.api.NoteApi
import com.example.notesapp.api.UserApi
import com.example.notesapp.utils.Constants.BASE_URL
import com.github.ybq.android.spinkit.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun provideUserApi(retrofitBuilder: Retrofit.Builder): UserApi {
        return retrofitBuilder.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideNotesApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): NoteApi {
        return retrofitBuilder.client(okHttpClient).build().create(NoteApi::class.java)
    }
}