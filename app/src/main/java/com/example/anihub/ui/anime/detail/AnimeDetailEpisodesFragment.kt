package com.example.anihub.ui.anime.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.anihub.BaseFragment
import com.example.anihub.R

class AnimeDetailEpisodesFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_anime_detail_episodes, container, false)
    }

    companion object {

        val TAG = AnimeDetailEpisodesFragment::class.java.simpleName

        fun newInstance(): AnimeDetailEpisodesFragment {
            return AnimeDetailEpisodesFragment()
        }
    }
}