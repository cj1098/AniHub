package com.example.anihub.ui.anime.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import api.SearchAnimeByIdQuery
import com.example.anihub.AniHubApplication
import com.example.anihub.BaseFragment
import com.example.anihub.R
import com.example.anihub.di.DaggerAppComponent
import com.example.anihub.ui.anime.ViewModelFactory
import com.example.anihub.ui.anime.shared.AnimeSharedViewModel
import kotlinx.android.synthetic.main.activity_anime.*
import kotlinx.android.synthetic.main.activity_anime.anime_detail_title
import kotlinx.android.synthetic.main.fragment_anime_detail_overview.*
import javax.inject.Inject

class AnimeDetailOverviewFragment : BaseFragment() {

    private lateinit var animeSharedViewModel: AnimeSharedViewModel

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
        return inflater.inflate(R.layout.fragment_anime_detail_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        animeSharedViewModel = modelFactory.create(AnimeSharedViewModel::class.java)
        setupObservableViewModels()
        val id: Int = arguments?.getInt(AnimeActivity.ID) ?: arguments!!.getInt(AnimeActivity.ID)
        animeSharedViewModel.loadAnimeById(id)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupObservableViewModels() {
        animeSharedViewModel.searchAnimeByIdLiveData.observe(this, Observer { it ->
            it.data()?.media?.let {
                anime_detail_title.text = it.title?.romaji
                anime_detail_rating.text = getString(R.string.anime_details_rating, (it.averageScore?.toFloat()?.div(RATING_DIVIDER)?.times(10F)).toString())
                anime_detail_information.text = getString(R.string.anime_details_information, it.startDate?.year.toString(), it.episodes.toString(), it.genres?.get(0), it.studios?.getAnimationStudioName())
                anime_detail_description.text = it.description
            }
        })

        animeSharedViewModel.animeError.observe(this, Observer { onError(it) })
    }

    companion object {

        val TAG = AnimeDetailOverviewFragment::class.java.simpleName
        val RATING_DIVIDER = 100F

        fun newInstance(id: Int): AnimeDetailOverviewFragment {
            val animeDetailOverviewFragment = AnimeDetailOverviewFragment()
            val bundle = Bundle()
            bundle.putInt(AnimeActivity.ID, id)
            animeDetailOverviewFragment.arguments = bundle
            return animeDetailOverviewFragment
        }

        fun SearchAnimeByIdQuery.Studio.getAnimationStudioName(): String {
            nodes?.forEach { if (it?.isAnimationStudio == true) return it.name }
            return ""
        }
    }
}