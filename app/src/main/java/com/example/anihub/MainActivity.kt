package com.example.anihub

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.navigation.NavigationView
import com.example.anihub.ui.anime.detail.AnimeActivity
import com.example.anihub.ui.anime.list.AnimeListFragment
import com.example.anihub.ui.main.SectionsPagerAdapter
import com.example.anihub.ui.search.SearchActivity
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    AnimeListFragment.AnimeListInterface {

    private lateinit var appBarConfiguration: AppBarConfiguration

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
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
//        ViewCompat.getTransitionName(sharedView)?.let { replaceFragment(R.id.content,
//            AnimeDetailFragment.newInstance(id, it), AnimeDetailFragment.TAG, true, sharedView) }
    }

    companion object {
        @JvmField
        val TAG = MainActivity::class.java

        const val ID = "ID"
    }
}
