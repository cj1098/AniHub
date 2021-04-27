package com.example.anihub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.apollographql.apollo.exception.ApolloException
import java.lang.Exception

open class BaseFragment : Fragment() {
    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    // This function could somehow be just used in BaseActivity...
    fun onError(exception: Exception) {
        when (exception) {
            is ApolloException -> {
                Toast.makeText(requireContext(), "Error occured", Toast.LENGTH_SHORT).show()
                Log.e(ERROR, exception?.message)
            }
            is IllegalStateException -> {
                //Log.e(ERROR, getString(R.string.no_implementation))
            }
        }
    }
}