package com.example.anihub

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.*
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import api.BrowseAnimeQuery
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.anihub.di.DaggerAppComponent
import com.example.anihub.ui.anime.detail.AnimeActivity
import com.example.anihub.ui.anime.detail.AnimeDetailFragment
import com.example.anihub.ui.anime.list.AnimeListFragment
import com.example.anihub.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    AnimeListFragment.AnimeListInterface {

    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var apolloClient: ApolloClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerAppComponent.create().inject(this)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        val tabLayout: TabLayout = findViewById(R.id.tabs)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        tabLayout.setupWithViewPager(viewPager)

        val tabsPager = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = tabsPager
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onAnimeItemSelected(
        id: Int,
        transitionName: String,
        sharedView: View
    ) {
        val intent = Intent(this, AnimeActivity::class.java)
        intent.putExtra(ID, id)
        startActivity(intent)
//        ViewCompat.getTransitionName(sharedView)?.let { replaceFragment(R.id.content,
//            AnimeDetailFragment.newInstance(id, it), AnimeDetailFragment.TAG, true, sharedView) }
    }

    //    fun animateSearchToolbar(numberOfMenuIcon: Int, containsOverflow: Boolean, show: Boolean) {
//
//        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
//        window.statusBarColor = (ContextCompat.getColor(this, R.color.quantum_grey_600))
//
//        if (show) {
//            val width = toolbar.width -
//                    (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.action_button_overflow_material_width) else 0) -
//                    resources.getDimensionPixelSize(R.dimen.action_button_material_width) * numberOfMenuIcon / 2
//            val createCircularReveal = ViewAnimationUtils.createCircularReveal(
//                toolbar,
//                width,
//                toolbar.height / 2,
//                0.0f,
//                width.toFloat()
//            )
//            createCircularReveal.duration = CIRCULAR_REVEAL_ANIMATION_DURATION
//            createCircularReveal.start()
//        } else {
//            val width = toolbar.width -
//                    (if (containsOverflow) resources.getDimensionPixelSize(R.dimen.action_button_overflow_material_width) else 0) -
//                    resources.getDimensionPixelSize(R.dimen.action_button_material_width) * numberOfMenuIcon / 2
//            val createCircularReveal = ViewAnimationUtils.createCircularReveal(
//                toolbar,
//                width,
//                toolbar.height / 2,
//                width.toFloat(),
//                0.0f
//            )
//            createCircularReveal.duration = CIRCULAR_REVEAL_ANIMATION_DURATION
//            createCircularReveal.addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationEnd(animation: Animator) {
//                    super.onAnimationEnd(animation)
//                    toolbar.setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
//                    window.statusBarColor = (ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark))
//                }
//            })
//            createCircularReveal.start()
//            window.statusBarColor = (ContextCompat.getColor(this, R.color.colorPrimaryDark))
//        }
//    }

    companion object {
        @JvmField
        val TAG = MainActivity::class.java

        const val ID = "ID"
    }
}
