package com.example.anihub.ui.anime.detail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.anihub.R

private val TAB_TITLES = arrayOf(
    R.string.anime_details_tab_text_overview,
    R.string.anime_details_tab_text_episodes,
    R.string.anime_details_tab_text_see_more
)

class AnimeDetailPagerAdapter (private val context: Context, fm: FragmentManager, private val id: Int) :
FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        // Fix this else statement
        return when (position) {
            0 -> {
                return AnimeDetailOverviewFragment.newInstance(id)
            }
            1 -> {
                return AnimeDetailEpisodesFragment.newInstance(id)
            }
            2 -> {
                return AnimeDetailSeeMoreFragment.newInstance()
            }
            else -> AnimeDetailOverviewFragment.newInstance(id)
        }
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

}