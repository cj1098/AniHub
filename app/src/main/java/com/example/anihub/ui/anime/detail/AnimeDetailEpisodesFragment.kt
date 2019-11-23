package com.example.anihub.ui.anime.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.anihub.AniHubApplication
import com.example.anihub.BaseFragment
import com.example.anihub.R
import com.example.anihub.ui.GridSpaceDecoration
import com.example.anihub.ui.anime.ViewModelFactory
import com.example.anihub.ui.anime.shared.AnimeSharedViewModel
import kotlinx.android.synthetic.main.fragment_anime_detail_episodes.*
import javax.inject.Inject

class AnimeDetailEpisodesFragment : BaseFragment() {

    private lateinit var animeSharedViewModel: AnimeSharedViewModel
    private lateinit var animeDetailEpisodesAdapter: AnimeDetailEpisodesAdapter

    @Inject
    lateinit var modelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AniHubApplication.graph.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_anime_detail_episodes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val id: Int = arguments?.getInt(AnimeActivity.ID) ?: arguments!!.getInt(AnimeActivity.ID)
        animeSharedViewModel = modelFactory.create(AnimeSharedViewModel::class.java)
        setupObservableViewModels()
        animeSharedViewModel.loadAnimeById(id)

        anime_details_episodes_recycler.layoutManager = GridLayoutManager(view.context, 4)
        anime_details_episodes_recycler.addItemDecoration(GridSpaceDecoration(10))
        animeDetailEpisodesAdapter = AnimeDetailEpisodesAdapter(requireContext())
        anime_details_episodes_recycler.adapter = animeDetailEpisodesAdapter
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupObservableViewModels() {
        animeSharedViewModel.searchAnimeByIdLiveData.observe(this, Observer { it ->
            it.data()?.media?.let {
                if (it.streamingEpisodes?.isEmpty() == true) {
                    // display no episodes screen
                } else {
                    animeDetailEpisodesAdapter.setItems(it.streamingEpisodes)
                }
            }
        })

        animeSharedViewModel.animeError.observe(this, Observer { onError(it) })
    }

    companion object {

        val TAG = AnimeDetailEpisodesFragment::class.java.simpleName

        fun newInstance(id: Int): AnimeDetailEpisodesFragment {
            val animeDetailEpisodesFragment = AnimeDetailEpisodesFragment()
            val bundle = Bundle()
            bundle.putInt(AnimeActivity.ID, id)
            animeDetailEpisodesFragment.arguments = bundle
            return animeDetailEpisodesFragment
        }
    }
}