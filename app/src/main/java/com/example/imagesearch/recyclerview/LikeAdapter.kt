package com.example.imagesearch.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.R
import com.example.imagesearch.data.Like
import com.example.imagesearch.databinding.ItemBinding

class LikeAdapter(val like: MutableList<Like>) : RecyclerView.Adapter<LikeAdapter.Holder>() {

    //var onFavoriteChangeListener: OnFavoriteChangeListener? = null
    interface ItemClick{
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeAdapter.Holder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }



        if (like[position].isLike) {
            holder.ivLike.setImageResource(R.drawable.blueheart)
        } else {
            holder.ivLike.setImageResource(R.drawable.heartline)

        }
    }

    override fun getItemCount(): Int {
        return like.size
    }

    inner class Holder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root){
        val ivLike = binding.imgHeart
    }


}