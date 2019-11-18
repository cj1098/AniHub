package com.example.anihub.ui.anime

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import api.BrowseAnimeQuery
import com.bumptech.glide.Glide
import com.example.anihub.R

class AnimeListAdapter(val context: Context, val data: BrowseAnimeQuery.Data?) : RecyclerView.Adapter<AnimeListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_row_anime_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.page?.media?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(data?.page?.media?.get(position)?.coverImage?.medium).into(holder.coverImage)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coverImage = itemView.findViewById<ImageView>(R.id.anime_cover)
    }
}