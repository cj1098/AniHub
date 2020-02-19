package com.example.anihub.di.modules

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCachePolicy.CACHE_FIRST
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCache
import com.example.anihub.BASE_URL
import com.example.anihub.CacheFactory
import com.example.anihub.ui.anime.shared.AnimeSharedRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module (includes = [ContextModule::class, AppModule::class, CacheModule::class])
class ApiModule {

    @Singleton
    @Provides
    fun providesApolloClient(cacheFactory: CacheFactory): ApolloClient {
//        if (BuildConfig.DEBUG) {
//            val httpLoggingInterceptor = HttpLoggingInterceptor()
//            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            okHttpBuilder.addInterceptor(httpLoggingInterceptor)
//        }
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method(),
                    original.body())

                chain.proceed(builder.build())
            }
            .build()

        // apollo caching

        return ApolloClient.builder().normalizedCache(cacheFactory.provideNormalizedDiskCache<SqlNormalizedCache>().first, cacheFactory.provideNormalizedDiskCache<SqlNormalizedCache>().second)
            .defaultHttpCachePolicy(CACHE_FIRST).okHttpClient(okHttpClient).serverUrl(BASE_URL).build()
    }

    @Singleton
    @Provides
    fun providesAnimeRepository(apolloClient: ApolloClient, context: Context): AnimeSharedRepository {
        return AnimeSharedRepository(apolloClient)
    }

}