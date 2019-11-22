package com.example.anihub.ui.anime.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.anihub.AniHubApplication
import com.example.anihub.BaseFragment
import com.example.anihub.R
import com.example.anihub.ui.anime.ViewModelFactory
import com.example.anihub.ui.anime.shared.AnimeSharedViewModel
import kotlinx.android.synthetic.main.fragment_anime_detail.*
import kotlinx.android.synthetic.main.fragment_anime_detail.tab_layout
import javax.inject.Inject

class AnimeDetailFragment : BaseFragment() {

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
        return inflater.inflate(R.layout.fragment_anime_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        animeSharedViewModel = modelFactory.create(AnimeSharedViewModel::class.java)
        setupObservableViewModels()
        val tabsPager = AnimeDetailPagerAdapter(requireContext(), activity?.supportFragmentManager!!)
        anime_details_view_pager.adapter = tabsPager
        tab_layout.setupWithViewPager(anime_details_view_pager)


        //figure out why I have to use the double bang !!...
        val id: Int = arguments?.getInt(AnimeActivity.ID) ?: arguments!!.getInt(AnimeActivity.ID)
        animeSharedViewModel.loadAnimeById(id)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupObservableViewModels() {
        animeSharedViewModel.searchAnimeByIdLiveData.observe(this, Observer { it ->
            it.data()?.media?.let {
                val bannerImage = if (!it.bannerImage.isNullOrEmpty()) it.bannerImage else it.coverImage?.large

                Glide.with(requireContext()).load(bannerImage).into(expanded_image)
            }
        })

        animeSharedViewModel.animeError.observe(this, Observer { onError(it) })
    }

    companion object {
        @JvmField
        val TAG = AnimeDetailFragment::class.java.simpleName
        const val ID = "ID"

        fun newInstance(id: Int): AnimeDetailFragment {
            val animeDetailFragment = AnimeDetailFragment()
            val bundle = Bundle()
            bundle.putInt(ID, id)
            animeDetailFragment.arguments = bundle
            return animeDetailFragment
        }
    }
}