package com.example.anihub.ui.search

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewAnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.anihub.BaseActivity
import com.example.anihub.R
import com.example.anihub.ui.anime.ViewModelFactory
import com.example.anihub.ui.anime.detail.AnimeActivity
import com.example.anihub.ui.anime.shared.AnimeSharedViewModel
import com.jakewharton.rxbinding3.appcompat.queryTextChangeEvents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchActivity : BaseActivity(), SearchAnimeFragment.AnimeSearchListInterface {

    //private lateinit var animeListAdapter: AnimeListAdapter
    private lateinit var animeSharedViewModel: AnimeSharedViewModel
    private lateinit var searchPagerAdapter: SearchPagerAdapter
    private lateinit var searchItem: MenuItem

    private lateinit var layoutManager: RecyclerView.LayoutManager
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var page = 1
    private lateinit var searchDisposable: Disposable

    @Inject
    lateinit var modelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        searchPagerAdapter = SearchPagerAdapter(supportFragmentManager)
        val tabsPager = searchPagerAdapter
        view_pager.adapter = tabsPager
        tab_layout.setupWithViewPager(view_pager)

        //animateSearchToolbar(1, false, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                // Can't use the manifest solution of setting this Activites parent to MainActivity because
                // we create multiple instances of this activity and the back button needs to go back to each
                // of them.
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        if (!searchDisposable.isDisposed) {
            searchDisposable.dispose()
        }
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_search_menu, menu)
        searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchDisposable = searchView.queryTextChangeEvents()
            .debounce(250, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val fragment = view_pager.adapter?.instantiateItem(view_pager, 0)
                if (fragment != null && (it.queryText.length >= 2) || it.isSubmitted) {
                    (fragment as SearchAnimeFragment).loadData(it.queryText.toString())
                }
            }

//        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
//            override fun onMenuItemActionExpand(searchView: MenuItem?): Boolean {
//                // go to search activity and then expand immediately
//                //animateSearchToolbar(1, true, true)
//                return true
//            }
//
//            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
//                if (searchItem.isActionViewExpanded) {
//                    //animateSearchToolbar(1, false, false)
//                }
//                return true
//            }
//        })


        return true
    }

    override fun onAnimeItemSelected(id: Int) {
        val intent = Intent(this, AnimeActivity::class.java)
        intent.putExtra(ID, id)
        startActivity(intent)
    }

    fun animateSearchToolbar(numberOfMenuIcon: Int, containsOverflow: Boolean, show: Boolean) {

        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        //window.statusBarColor = (ContextCompat.getColor(this, R.color.quantum_grey_600))

        if (show) {
            toolbar.popupTheme = R.style.AppTheme_AppBarOverlay
            val width = toolbar.width -
                    (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.action_button_overflow_material_width) else 0) -
                    resources.getDimensionPixelSize(R.dimen.action_button_material_width) * numberOfMenuIcon / 2
            val createCircularReveal = ViewAnimationUtils.createCircularReveal(
                toolbar,
                width,
                toolbar.height / 2,
                0.0f,
                width.toFloat()
            )
            createCircularReveal.duration = 500
            createCircularReveal.start()
        } else {
            val width = toolbar.width -
                    (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.action_button_overflow_material_width) else 0) -
                    resources.getDimensionPixelSize(R.dimen.action_button_material_width) * numberOfMenuIcon / 2
            val createCircularReveal = ViewAnimationUtils.createCircularReveal(
                toolbar,
                width,
                toolbar.height / 2,
                width.toFloat(),
                0.0f
            )
            createCircularReveal.duration = 500
            createCircularReveal.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    toolbar.popupTheme = R.style.AppTheme_PopupOverlay
                    toolbar.setBackgroundColor(ContextCompat.getColor(this@SearchActivity, R.color.colorPrimary))
                    window.statusBarColor = (ContextCompat.getColor(this@SearchActivity, R.color.colorPrimaryDark))
                }
            })
            createCircularReveal.start()
            window.statusBarColor = (ContextCompat.getColor(this, R.color.colorPrimaryDark))
        }
    }

    companion object {
        @JvmField
        val TAG = SearchActivity::class.java

        const val ID = "ID"
    }
}