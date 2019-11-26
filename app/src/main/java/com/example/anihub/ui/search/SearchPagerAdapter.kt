package com.example.anihub.ui.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.anihub.ui.anime.list.AnimeListFragment

private val TAB_TITLES = arrayOf(
    "ANIME", "MANGA"
)

class SearchPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                AnimeListFragment.newInstance()
            }
            1 -> {
                AnimeListFragment.newInstance() // This is supposed to be Manga
            }
            else -> {
                AnimeListFragment.newInstance() // Anime by default
            }
        }
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}