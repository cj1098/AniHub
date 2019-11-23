package com.example.anihub.ui.anime.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import api.SearchAnimeByIdQuery
import com.bumptech.glide.Glide
import com.example.anihub.R
import kotlinx.android.synthetic.main.adapter_row_anime_details_episodes.view.*

class AnimeDetailEpisodesAdapter(private val context: Context) : RecyclerView.Adapter<AnimeDetailEpisodesAdapter.AnimeDetailsEpisodeViewHolder>() {

    private var data: List<SearchAnimeByIdQuery.StreamingEpisode?>? = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeDetailsEpisodeViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.adapter_row_anime_details_episodes, parent, false)
        return AnimeDetailsEpisodeViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0 // throw an error or don't allow this to happen somehow
    }

    override fun onBindViewHolder(holder: AnimeDetailsEpisodeViewHolder, position: Int) {
        holder.bind(data?.get(position), position)
    }

    class AnimeDetailsEpisodeViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        fun bind(item: SearchAnimeByIdQuery.StreamingEpisode?, position: Int) {
            Glide.with(context).load(item?.thumbnail).into(itemView.anime_details_episode_thumbnail)
            itemView.anime_detail_episode_title.text = context.getString(R.string.anime_details_episode_naming, position + 1)
        }

        override fun onClick(v: View?) {
            // take them to the player in the app or out of app with an explicit intent
        }
    }

    fun setItems(items: List<SearchAnimeByIdQuery.StreamingEpisode?>?) {
        data = items
        notifyDataSetChanged()
    }

}