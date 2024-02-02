package com.example.imagesearch.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.model.SearchItemModel
import com.example.imagesearch.databinding.ItemBinding
import com.example.imagesearch.utils.Utils.getDateFromTimestampWithFormat

class LikeAdapter(val like: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 북마크된 아이템들을 저장하는 리스트
    var items = mutableListOf<SearchItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LikeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //이미지 로딩 라이브러리(Gride)를 사용해 썸내일 이미지를 로드한다.
        Glide.with(like)
            .load(items[position].url)
            .into((holder as LikeViewHolder).iv_thum_image)

        holder.tv_title.text = items[position].title
        holder.ivLike.visibility = View.GONE // 좋아요 아이템을 숨김
//        holder.tv_datetime.text =
//            getDateFromTimestampWithFormat(
//                items[position].dateTime,
//                "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
//                "yyyy-MM-dd' HH:mm:ss"
//            )
        holder.tv_datetime.text = getDateFromTimestampWithFormat(
            items[position].dateTime,
            "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
            "yyyy-MM-dd HH:mm:ss"
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class LikeViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root){
        val iv_thum_image: ImageView = binding.imgImage
        val tv_title: TextView = binding.tvTitleName
        val tv_datetime: TextView = binding.tvTitleTime
        val ivLike = binding.imgHeart
        val lyitem = binding.lyItem

        init {
            //북마크 페이짓에서는 '좋아요' 아이콘을 숨긴다.
            ivLike.visibility =View.GONE

            //아이템 클릭 리스너 설정
            lyitem.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }


}