package com.example.anihub

import android.content.Context
import androidx.annotation.NonNull
import com.apollographql.apollo.api.Operation.Variables
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.CacheKeyResolver
import com.apollographql.apollo.cache.normalized.NormalizedCache
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.ApolloSqlHelper
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import org.jetbrains.annotations.NotNull


@Suppress("UNCHECKED_CAST")
class CacheFactory(val context: Context) {
    fun <T : NormalizedCache> provideNormalizedDiskCache(): Pair<NormalizedCacheFactory<T>, CacheKeyResolver> {
        // Create the ApolloSqlHelper. Please note that if null is passed in as the name, you will get an in-memory
        // Sqlite database that will not persist across restarts of the app.
        val apolloSqlHelper = ApolloSqlHelper.create(context, "anihub_db")

        // Create NormalizedCacheFactory
        val cacheFactory: NormalizedCacheFactory<T> = SqlNormalizedCacheFactory(apolloSqlHelper) as NormalizedCacheFactory<T>

        // Create the cache key resolver, this example works well when all types have globally unique ids.
        val resolver: CacheKeyResolver = object : CacheKeyResolver() {
            @NotNull
            override fun fromFieldRecordSet(@NotNull field: ResponseField, @NotNull recordSet: Map<String, Any>): CacheKey {
                return formatCacheKey(recordSet["media"] as String?)
            }

            override fun fromFieldArguments(field: ResponseField, @NonNull variables: Variables): CacheKey {
                return formatCacheKey(field.resolveArgument("id", variables) as String?)
            }

            private fun formatCacheKey(id: String?): CacheKey {
                return if (id == null || id.isEmpty()) {
                    CacheKey.NO_KEY
                } else {
                    CacheKey.from(id)
                }
            }
        }

        return Pair(cacheFactory, resolver)
    }
}