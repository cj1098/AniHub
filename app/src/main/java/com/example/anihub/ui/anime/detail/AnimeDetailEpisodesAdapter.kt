package com.example.anihub.ui.anime.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import api.SearchAnimeByIdEpisodeQuery
import api.SearchAnimeByIdQuery
import com.bumptech.glide.Glide
import com.example.anihub.R
import com.example.anihub.ui.anime.AnimeModel
import kotlinx.android.synthetic.main.adapter_row_anime_details_episodes.view.*

class AnimeDetailEpisodesAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: List<AnimeModel.StreamingEpisode> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_row_anime_details_episodes, parent, false)
        return AnimeDetailsEpisodeViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AnimeDetailsEpisodeViewHolder).bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class AnimeDetailsEpisodeViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: AnimeModel.StreamingEpisode, position: Int) {
            Glide.with(context).load(item.thumbnail).into(itemView.anime_details_episode_thumbnail)
            itemView.anime_detail_episode_title.text = context.getString(R.string.anime_details_episode_naming, position + 1)
            itemView.setOnClickListener {
                var url = "https://vidstreaming.io/videos/naruto-episode-1"
                //url += item?.title?.
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                context.startActivity(intent)
            }
        }
    }

    fun setItems(items: List<AnimeModel.StreamingEpisode>) {
        data = items
        notifyDataSetChanged()
    }
}