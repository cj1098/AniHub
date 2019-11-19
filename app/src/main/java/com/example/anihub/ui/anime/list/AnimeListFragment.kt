package com.example.anihub.ui.anime.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.apollographql.apollo.ApolloClient
import com.example.anihub.R
import com.example.anihub.di.DaggerAppComponent
import com.example.anihub.ui.GridSpaceDecoration
import com.example.anihub.ui.anime.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_anime_list.*
import javax.inject.Inject

class AnimeListFragment : Fragment() {

    @Inject
    lateinit var apolloClient: ApolloClient

    private lateinit var animeListAdapter: AnimeListAdapter
    private lateinit var animeListViewModel: AnimeListViewModel

    @Inject
    lateinit var modelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_anime_list, container, false)
        view.apply {

        }
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        activity?.let {
            animeListViewModel = modelFactory.create(AnimeListViewModel::class.java)
        }
        anime_list.layoutManager = GridLayoutManager(view.context, 4)
        anime_list.addItemDecoration(GridSpaceDecoration(10))
        animeListAdapter =
            AnimeListAdapter(requireContext())
        anime_list.adapter = animeListAdapter
        setupObservableViewModels()
        animeListViewModel.getAllAnime(1)
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupObservableViewModels() {
        animeListViewModel.browseAllAnimeLiveData.observe(this, Observer { it ->
            it.data()?.page?.media.let {
                if (!it.isNullOrEmpty()) {
                    initial_loading_pb.isGone = true
                    empty_results_view.isGone = true
                } else {
                    initial_loading_pb.isVisible = true
                    empty_results_view.isVisible = true
                    anime_list.isGone = true
                }
                animeListAdapter.setItems(it?.filterNotNull())
            }
        })

        animeListViewModel.browseAllAnimeError.observe(this, Observer {
        })
    }

//    interface OnAnimeSelected {
//        fun onAnimeItemSelected(githubRepository: GithubRepository, transitionName: String, sharedView: View)
//    }

    companion object {
        @JvmField
        val TAG: String = AnimeListFragment::class.java.simpleName

        fun newInstance(): AnimeListFragment =
            AnimeListFragment()
    }

}