package com.example.anihub.ui.anime.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.anihub.AniHubApplication
import com.example.anihub.BaseFragment
import com.example.anihub.R
import com.example.anihub.ui.GridSpaceDecoration
import com.example.anihub.ui.anime.ViewModelFactory
import com.example.anihub.ui.anime.detail.AnimeActivity.Companion.GENRES
import com.example.anihub.ui.anime.detail.AnimeActivity.Companion.TAGS
import com.example.anihub.ui.anime.shared.AnimeSharedViewModel
import kotlinx.android.synthetic.main.fragment_anime_detail_see_more.*
import javax.inject.Inject

class AnimeDetailSeeMoreFragment : BaseFragment() {

    // TODO: GET RID OF DUPLICATES. The api will return the same anime based on the genre tags we provide
    // TODO: So it's probably best to search the data before we send it off to the list and check for a matching name.
    private lateinit var animeSharedViewModel: AnimeSharedViewModel
    private lateinit var animeDetailSeeMoreAdapter: AnimeDetailSeeMoreAdapter

    private var currentPage = 1

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
        return inflater.inflate(R.layout.fragment_anime_detail_see_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animeSharedViewModel = modelFactory.create(AnimeSharedViewModel::class.java)
        animeDetailSeeMoreAdapter = AnimeDetailSeeMoreAdapter(requireContext())
        setupObservableViewModels()
        anime_detail_see_more_recycler.adapter = animeDetailSeeMoreAdapter
        anime_detail_see_more_recycler.addItemDecoration(GridSpaceDecoration(10))
        anime_detail_see_more_recycler.layoutManager = GridLayoutManager(requireContext(), 4)

        val tags = arguments?.getStringArrayList(TAGS)!!
        val genres = arguments?.getStringArrayList(GENRES)!!
        animeSharedViewModel.loadAnimeByKeywords(currentPage, tags, genres)

    }

    private fun setupObservableViewModels() {
        animeSharedViewModel.searchAnimeByGenresTagsLiveData.observe(this, Observer { it ->
            it.data()?.page?.media.let {
                if (!it.isNullOrEmpty()) {
                    initial_loading_pb.isGone = true
                    empty_results_view.isGone = true
                    animeDetailSeeMoreAdapter.setItems(it.requireNoNulls())
                } else {
                    initial_loading_pb.isGone = true
                    empty_results_view.isVisible = true
                    anime_detail_see_more_recycler.isGone = true
                }
            }
        })

        animeSharedViewModel.animeError.observe(this, Observer { onError(it) })
    }

    companion object {

        val TAG = AnimeDetailSeeMoreFragment::class.java.simpleName

        fun newInstance(tags: ArrayList<String?>, genres: ArrayList<String?>): AnimeDetailSeeMoreFragment {
            val animeDetailSeeMoreFragment = AnimeDetailSeeMoreFragment()
            val bundle = Bundle()
            bundle.putStringArrayList(TAGS, tags)
            bundle.putStringArrayList(GENRES, genres)
            animeDetailSeeMoreFragment.arguments = bundle
            return animeDetailSeeMoreFragment
        }
    }
}