package com.example.anihub.ui.anime.detail

import android.os.Bundle
import com.example.anihub.BaseActivity
import com.example.anihub.R
import com.example.anihub.addFragment
import com.example.anihub.di.DaggerAppComponent
import kotlinx.android.synthetic.main.activity_anime.*

class AnimeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime)
        DaggerAppComponent.create().inject(this)

        // CHRIS: REVISIT THIS. NOT GOOD. Need to check if arguments are null for whatever reason and fix this shit appropriately. MMKAY?
        setSupportActionBar(toolbar)
        val id: Int = intent.extras?.getInt(ID) ?: intent.extras!!.getInt(ID)
        addFragment(R.id.anime_content, AnimeDetailFragment.newInstance(id), AnimeDetailFragment.TAG)
    }

    companion object {
        @JvmField
        val TAG: String = AnimeActivity::class.java.simpleName

        const val ID = "ID"

    }
}