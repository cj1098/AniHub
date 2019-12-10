package com.example.anihub.ui.anime.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import api.BrowseAnimeQuery
import com.bumptech.glide.Glide
import com.example.anihub.R
import com.example.anihub.ui.anime.AnimeModel
import kotlinx.android.synthetic.main.adapter_row_anime_list.view.*

class AnimeListAdapter(val context: Context, val listener: AnimeListFragment.AnimeListInterface) : RecyclerView.Adapter<AnimeListAdapter.ViewHolder>() {

    private var items: MutableList<AnimeModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_row_anime_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener{ listener.onAnimeItemSelected(items[position].id)}
        holder.bind(items[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        fun bind(item: AnimeModel) {
            item.let {
                Glide.with(itemView.context).load(item.media.coverImage)
                    //.apply(RequestOptions().override(1100, 1100))
                    .placeholder(R.drawable.code_geass) // change later
                    .error(R.drawable.code_geass) // change later
                    .into(itemView.anime_image)
                itemView.anime_title.text = item.media.title
                itemView.anime_rating.rating = item.media.averageScore.toFloat().times(5).div(100f)
            }

        }

        override fun onClick(v: View?) {
        }
    }

    fun setItems(items: List<AnimeModel>) {
        var size = this.items.size
        this.items.addAll(items)
        var sizeNew = this.items.size
        notifyItemRangeChanged(size, sizeNew)
    }
}