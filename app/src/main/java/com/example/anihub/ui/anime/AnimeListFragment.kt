package com.example.anihub.ui.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import api.BrowseAnimeQuery
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.anihub.R
import com.example.anihub.dagger.DaggerAppComponent
import kotlinx.android.synthetic.main.fragment_anime_list.*
import javax.inject.Inject

class AnimeListFragment : Fragment() {

    @Inject
    lateinit var apolloClient: ApolloClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val test = BrowseAnimeQuery(1)
        apolloClient.query(test).enqueue(object : ApolloCall.Callback<BrowseAnimeQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                println("failure :(" + e.localizedMessage)
            }

            override fun onResponse(response: Response<BrowseAnimeQuery.Data>) {
                this@AnimeListFragment.activity?.runOnUiThread { val animeListAdapter = AnimeListAdapter(this@AnimeListFragment.context!!, response.data())
                    anime_list.layoutManager = GridLayoutManager(container?.context, 3)
                    anime_list.adapter = animeListAdapter }
            }

        })

        return inflater.inflate(R.layout.fragment_anime_list, container, false)

    }

}