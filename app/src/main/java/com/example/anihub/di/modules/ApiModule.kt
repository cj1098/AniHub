package com.example.anihub.di.modules

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation.Variables
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.CacheKeyResolver
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.ApolloSqlHelper
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCache
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.example.anihub.BASE_URL
import com.example.anihub.CacheFactory
import com.example.anihub.ui.anime.shared.AnimeSharedRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.jetbrains.annotations.NotNull

@Module (includes = [CacheModule::class])
class ApiModule {

    @Provides
    fun providesApolloClient(): ApolloClient {
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

        return ApolloClient.builder().okHttpClient(okHttpClient).serverUrl(BASE_URL).build()
    }

    @Provides
    fun providesAnimeRepository(apolloClient: ApolloClient): AnimeSharedRepository {
        return AnimeSharedRepository(
            apolloClient
        )
    }
}