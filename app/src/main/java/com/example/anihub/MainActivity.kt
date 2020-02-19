package com.example.anihub

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.anihub.friends.FriendsFragment
import com.example.anihub.ui.anime.detail.AnimeActivity
import com.example.anihub.ui.anime.list.AnimeListFragment
import com.example.anihub.ui.main.SectionsPagerAdapter
import com.example.anihub.ui.search.SearchActivity
import com.google.android.material.navigation.NavigationView
import com.jakewharton.rxbinding3.view.globalLayouts
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    AnimeListFragment.AnimeListInterface, FriendsFragment.FriendsTouchInterface {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var disposable: Disposable

    lateinit var displayMetrics: DisplayMetrics
    private var screenWidth = 0
    private var collapseThreshhold = 0F
    private var friendsLayoutInitialX = 0F
    private var friendsLayoutWidth = 0F
    private var usersInitialXTouch = 0F
    private var dX: Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AniHubApplication.graph.inject(this)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        val tabsPager = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = tabsPager
        tab_layout.setupWithViewPager(view_pager)
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

        supportFragmentManager.beginTransaction()
            .add(R.id.friends_layout, FriendsFragment.newInstance(), FriendsFragment.TAG).commit()

        disposable = friends_layout.globalLayouts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                displayMetrics = resources.displayMetrics
                screenWidth = displayMetrics.widthPixels
                collapseThreshhold = screenWidth * .60F
                friendsLayoutInitialX = screenWidth - (screenWidth * .75).toFloat()
                friendsLayoutWidth = screenWidth - friendsLayoutInitialX
                friends_layout.x = screenWidth.toFloat()
            }

        fab.setOnClickListener {
            friends_layout.isVisible = true

            friends_layout.animate()
                .x(friendsLayoutInitialX)
                .setDuration(FRIENDS_LAYOUT_APPEAR_ANIMATION_TIME)
                .start()
        }

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onPause() {
        super.onPause()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onAnimeItemSelected(id: Int) {
        val intent = Intent(this, AnimeActivity::class.java)
        intent.putExtra(ID, id)
        startActivity(intent)
    }

    override fun onMotionEventReceived(event: MotionEvent) {
        val rawX = event.rawX

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                usersInitialXTouch = event.rawX
                dX = friends_layout.x - event.rawX
            }
            MotionEvent.ACTION_MOVE -> {
                // If we're not scrolling out of our bounds animate the view.
                if (rawX > usersInitialXTouch || friends_layout.x < friendsLayoutInitialX) {
                    friends_layout.animate()
                        .x(event.rawX + dX)
                        .setDuration(0)
                        .start()
                }
            }
            MotionEvent.ACTION_UP -> {
                // If we pass the collapse threshhold, collapse.
                if (friends_layout.x > collapseThreshhold) {
                    friends_layout.animate()
                        .x(screenWidth.toFloat())
                        .setDuration(FRIENDS_LAYOUT_COLLAPSE_ANIMATION_TIME)
                        .start()
                } else if (friends_layout.x < collapseThreshhold) { // If we don't, just revert back the original position
                    friends_layout.animate()
                        .x(friendsLayoutInitialX)
                        .setDuration(FRIENDS_LAYOUT_COLLAPSE_ANIMATION_TIME)
                        .start()
                }
            }
        }
    }

    override fun close() {
        friends_layout.animate()
            .x(screenWidth.toFloat())
            .setDuration(200)
            .start()
    }

    override fun showFAB() {
        fab.show()
    }

    override fun hideFAB() {
        if (fab.isShown) {
            fab.hide()
        }
    }

    companion object {
        @JvmField
        val TAG = MainActivity::class.java

        const val ID = "ID"
        const val FRIENDS_LAYOUT_COLLAPSE_ANIMATION_TIME = 200L
        const val FRIENDS_LAYOUT_APPEAR_ANIMATION_TIME = 500L
    }
}
