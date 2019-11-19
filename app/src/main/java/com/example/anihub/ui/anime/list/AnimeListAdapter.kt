package com.example.anihub.ui.anime.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import api.BrowseAnimeQuery
import com.bumptech.glide.Glide
import com.example.anihub.R
import kotlinx.android.synthetic.main.adapter_row_anime_list.view.*

class AnimeListAdapter(val context: Context) : RecyclerView.Adapter<AnimeListAdapter.ViewHolder>() {

    private var items: List<BrowseAnimeQuery.Medium> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_row_anime_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: BrowseAnimeQuery.Medium?) {
            item?.let {
                Glide.with(itemView.context).load(item.coverImage?.large)
                    //.apply(RequestOptions().override(1100, 1100))
                    .placeholder(R.drawable.code_geass) // change later
                    .error(R.drawable.code_geass) // change later
                    .into(itemView.anime_image)
                itemView.anime_title.text = item.title?.romaji
                itemView.anime_rating.rating = item.averageScore?.toFloat()?.times(5)?.div(100f) ?: 0f
            }
        }
    }

    fun setItems(items: List<BrowseAnimeQuery.Medium>?) {
        // Do something here to protect against if the list is empty..
        // We have no control over nullability because it comes from the api and Kotlin requires us to b
        // null-conscious. So Check if empty here and then post in some interface that we need to display
        // the empty results screen.
        this.items = items ?: emptyList()
        notifyDataSetChanged()
    }
}