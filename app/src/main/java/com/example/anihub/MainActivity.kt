package com.example.anihub

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
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
import com.example.anihub.dagger.DaggerAppComponent
import com.example.anihub.ui.main.SectionsPagerAdapter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
        val viewPager: ViewPager  = findViewById(R.id.view_pager)
        tabLayout.setupWithViewPager(viewPager)

        val tabsPager = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = tabsPager
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        val test = BrowseAnimeQuery(1)

        apolloClient.query(test).enqueue(object: ApolloCall.Callback<BrowseAnimeQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                Snackbar.make(navView, "failed", Snackbar.LENGTH_SHORT).show()
            }

            override fun onResponse(response: Response<BrowseAnimeQuery.Data>) {
                println(response.data())
            }
        })

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
}
