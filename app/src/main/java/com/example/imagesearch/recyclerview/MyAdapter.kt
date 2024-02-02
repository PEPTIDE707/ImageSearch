package com.example.imagesearch.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.databinding.ItemBinding
import com.example.imagesearch.model.SearchItemModel
import com.example.imagesearch.utils.Utils.getDateFromTimestampWithFormat
import com.example.imagesearch.viewpager2.MainActivity


class MyAdapter(private val mItems: Context) : RecyclerView.Adapter<MyAdapter.ItemHolder>() {

    var items = ArrayList<SearchItemModel>()

    fun clearItem(){
        items.clear()
        notifyDataSetChanged()
    }

   //아이템 클릭 이벤트
    interface ItemClick{
        fun onClick(view: View, position:Int)
    }
    var itemClick : ItemClick?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int){

        val currentItem = items[position]

        Glide.with(mItems)
            .load(currentItem.url)
            .into(holder.iconImage)
        //아이템 클릭 이벤트
        holder.itemView.setOnClickListener{
            itemClick?.onClick(it, position)
        }

        holder.heart.visibility = if(currentItem.isLike) View.VISIBLE else View.INVISIBLE
        holder.itemName.text = currentItem.title
        holder.times.text = getDateFromTimestampWithFormat(
            currentItem.dateTime,
            "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
            "yyyy-MM-dd HH:mm:ss"
        )
    }
    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        val iconImage = binding.imgImage
        val itemName = binding.tvTitleName
        val times = binding.tvTitleTime
        val heart = binding.imgHeart
        val ly_item = binding.lyItem

        init {
            heart.visibility = View.GONE
            iconImage.setOnClickListener(this)
            ly_item.setOnClickListener(this)
        }


        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return
            val item = items[position]

            item.isLike = !item.isLike

            if (item.isLike) {
                (mItems as MainActivity).addLikedItem(item)
            } else {
                (mItems as MainActivity).removeLikedItem(item)
            }

            notifyItemChanged(position)
        }
    }
}