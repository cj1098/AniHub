package com.example.anihub.ui.anime.detail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import api.SearchAnimeByGenresTagsQuery
import com.bumptech.glide.Glide
import com.example.anihub.R
import kotlinx.android.synthetic.main.adapter_row_anime_list.view.*

class AnimeDetailSeeMoreAdapter(private val context: Context) : RecyclerView.Adapter<AnimeDetailSeeMoreAdapter.AnimeDetailsSeeMoreViewHolder>() {

    private var data: List<SearchAnimeByGenresTagsQuery.Medium>? = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeDetailsSeeMoreViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.adapter_row_anime_list, parent, false)
        return AnimeDetailsSeeMoreViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: AnimeDetailsSeeMoreViewHolder, position: Int) {
        holder.bind(data?.get(position))
    }

    class AnimeDetailsSeeMoreViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var animeId: Int? = 0
        fun bind(item: SearchAnimeByGenresTagsQuery.Medium?) {
            animeId = item?.id
            Glide.with(context).load(item?.coverImage?.large).into(itemView.anime_image)
            itemView.anime_title.text = item?.title?.romaji
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // Takes them back to another instance of AnimeActivity
            val animeActivityIntent = Intent(context, AnimeActivity::class.java)
            animeActivityIntent.putExtra(AnimeActivity.ID, animeId)
            context.startActivity(animeActivityIntent)
        }
    }

    fun setItems(items: List<SearchAnimeByGenresTagsQuery.Medium>?) {
        data = items
        notifyDataSetChanged()
    }

}