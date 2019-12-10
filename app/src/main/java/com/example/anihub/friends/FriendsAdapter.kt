package com.example.anihub.friends

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anihub.R
import kotlinx.android.synthetic.main.adapter_row_friends.view.*

class FriendsAdapter(val context: Context) : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {

    private var data = emptyList<String>() // list of friend objects

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_row_friends, parent, false)
        return FriendsViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.bind(data[position])
    }


    class FriendsViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        fun bind(item: String) {
            itemView.friend_name.text = item
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // send them to the FriendsActivity to actually do messaging.
            context.startActivity(Intent(context, FriendsActivity::class.java))
        }
    }

    fun setItems(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }

}