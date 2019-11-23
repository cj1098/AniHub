package com.example.anihub.ui.anime.detail

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.anihub.AniHubApplication
import com.example.anihub.BaseActivity
import com.example.anihub.R
import com.example.anihub.ui.anime.ViewModelFactory
import com.example.anihub.ui.anime.shared.AnimeSharedViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_anime.*
import javax.inject.Inject
import kotlin.math.abs


class AnimeActivity : BaseActivity() {

    private lateinit var animeSharedViewModel: AnimeSharedViewModel

    @Inject
    lateinit var modelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime)
        AniHubApplication.graph.inject(this)

        // CHRIS: REVISIT THIS. NOT GOOD. Need to check if arguments are null for whatever reason and fix this shit appropriately. MMKAY?
        val id: Int = intent.extras?.getInt(ID) ?: intent.extras!!.getInt(ID)

        setSupportActionBar(toolbar)

        animeSharedViewModel = modelFactory.create(AnimeSharedViewModel::class.java)
        setupObservableViewModels()
        val tabsPager = AnimeDetailPagerAdapter(this, supportFragmentManager, id)
        anime_details_view_pager.adapter = tabsPager
        tab_layout.setupWithViewPager(anime_details_view_pager)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            anime_detail_title.alpha =
                abs(verticalOffset / appBarLayout!!.totalScrollRange.toFloat())
            //expanded_image.alpha = (255 * (1.0f -  abs(verticalOffset.toFloat()) / appBarLayout.totalScrollRange.toFloat()))
            //Log.d("alphaz", expanded_image.alpha.toString())
        })

        animeSharedViewModel.loadAnimeById(id)
    }

    private fun setupObservableViewModels() {
        animeSharedViewModel.searchAnimeByIdLiveData.observe(this, Observer { it ->
            it.data()?.media?.let {
                anime_detail_title.text = it.title?.romaji
                Glide.with(this).load(it.coverImage?.large).into(expanded_image)
            }
        })

        animeSharedViewModel.animeError.observe(this, Observer { onError(it) })
    }

    companion object {
        @JvmField
        val TAG: String = AnimeActivity::class.java.simpleName

        const val ID = "ID"

    }
}