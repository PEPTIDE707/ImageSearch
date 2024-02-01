package com.example.imagesearch.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.databinding.ItemBinding
import com.example.imagesearch.data.Document


class MyAdapter(val mItems: MutableList<Document>) : RecyclerView.Adapter<MyAdapter.Holder>() {

   //아이템 클릭 이벤트
    interface ItemClick{
        fun onClick(view: View, position:Int)
    }
    var itemClick : ItemClick?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }
    override fun onBindViewHolder(holder: Holder, position: Int){
        //아이템 클릭 이벤트
        holder.itemView.setOnClickListener{
            itemClick?.onClick(it, position)
        }
        val item = mItems[position]
        holder.bind(item)

    }
    override fun getItemId(position: Int): Long{
        return position.toLong()
    }
    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class Holder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root){
        val iconImage = binding.imgImage
        val itemName = binding.tvTitleName
        val times = binding.tvTitleTime

        val heart = binding.imgHeart

        fun bind(data: Document){
            Glide.with(iconImage.context).load(data.thumbnail_url).into(iconImage)
            itemName.text = data.display_sitename
            times.text = data.datetime

        }
    }
}