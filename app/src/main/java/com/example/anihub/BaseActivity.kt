package com.example.anihub

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.exception.ApolloException
import java.lang.Exception

open class BaseActivity : AppCompatActivity() {

    fun onError(exception: Exception) {
        when (exception) {
            is ApolloException -> {
                Log.e(ERROR, exception?.message)
            }
            is IllegalStateException -> {
                //Log.e(ERROR, getString(R.string.no_implementation))
            }
        }
    }
}