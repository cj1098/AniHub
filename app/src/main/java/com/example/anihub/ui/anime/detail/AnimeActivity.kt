package com.example.anihub.ui.anime.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import api.SearchAnimeByIdQuery
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
    private lateinit var animeDetailPagerAdapter: AnimeDetailPagerAdapter

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

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        animeDetailPagerAdapter = AnimeDetailPagerAdapter(this, supportFragmentManager, id)
        val tabsPager = animeDetailPagerAdapter
        anime_details_view_pager.adapter = tabsPager
        tab_layout.setupWithViewPager(anime_details_view_pager)

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val offsetAlpha: Float = verticalOffset.toFloat() / app_bar.totalScrollRange
            anime_detail_title.alpha = abs(offsetAlpha)
            expanded_image.alpha = 1 - offsetAlpha * -1
        })

        animeSharedViewModel.loadAnimeById(id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                // Can't use the manifest solution of setting this Activites parent to MainActivity because
                // we create multiple instances of this activity and the back button needs to go back to each
                // of them.
                finish()
            }
            R.id.favorite -> {
                item.icon = ContextCompat.getDrawable(this, R.drawable.favorite_menu_icon_filled)
                // check if the user is logged in, if so we can show the favorite icon. If they aren't we should
                // hide it. Also: Once they click on the favorite icon, immediately change the icon and make
                // a call to change it on the server.
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_anime_menu, menu)
        return true
    }

    private fun setupObservableViewModels() {
        animeSharedViewModel.searchAnimeByIdLiveData.observe(this, Observer { it ->
            it.data()?.media?.let {
                if (it.trailer?.thumbnail.isNullOrEmpty() || it.trailer?.site.isNullOrEmpty()) {
                    anime_detail_trailer_play.isGone = true
                    Glide.with(this).load(it.coverImage?.large).into(expanded_image)
                    expanded_image.setOnClickListener(null)
                } else {
                    anime_detail_trailer_play.isVisible = true
                    Glide.with(this).load(it.trailer?.thumbnail).into(expanded_image)
                    val trailerUrl = it.trailer?.site
                    expanded_image.setOnClickListener {0
                        val playTrailer = Intent()
                        playTrailer.data = Uri.parse(trailerUrl)
                        this@AnimeActivity.startActivity(playTrailer)
                    }
                }
                animeDetailPagerAdapter.setData(convertTagsToArrayList(it.tags), convertGenresToArrayList(it.genres))
                anime_detail_title.text = it.title?.romaji
            }
        })

        animeSharedViewModel.animeError.observe(this, Observer { onError(it) })
    }

    private fun convertTagsToArrayList(items: List<SearchAnimeByIdQuery.Tag?>?): ArrayList<String?> {
        val tags = ArrayList<String?>()
        if (items?.isNotEmpty() == true) {
            items.let { tags.add(it[0]?.name) }
        }
        return tags
    }

    private fun convertGenresToArrayList(items: List<String?>?): ArrayList<String?> {
        val genres = ArrayList<String?>()
        if (items?.isNotEmpty() == true) {
            items.let { genres.add(it[0]) }
        }
        return genres
    }

    companion object {
        @JvmField
        val TAG: String = AnimeActivity::class.java.simpleName

        const val ID = "ID"
        const val TAGS = "TAGS"
        const val GENRES = "GENRES"

    }
}