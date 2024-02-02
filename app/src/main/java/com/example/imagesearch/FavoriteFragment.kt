package com.example.imagesearch

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearch.model.SearchItemModel
import com.example.imagesearch.databinding.FragmentFavoriteBinding
import com.example.imagesearch.recyclerview.LikeAdapter
import com.example.imagesearch.viewpager2.MainActivity

class FavoriteFragment : Fragment() {

    private lateinit var mContext: Context

    //바인딩 객체를 null 허용으로 설정 (프래그먼트의 뷰가 파괴될 때 null 처리하기 위함)
    private var binding: FragmentFavoriteBinding? = null
    private lateinit var adapter: LikeAdapter

    //사용자의 좋아요를 받은 항목을 저장하는 리스트
    private var likedItems: List<SearchItemModel> = listOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // MainActivity로 부터 좋아요 받은 항목을 가져옴
        val mainActivity = activity as MainActivity
        likedItems = mainActivity.likedItem

        Log.d("FavoriteFragment", "likeItem size = ${likedItems.size}")

        //어댑터 설정
        adapter = LikeAdapter(mContext).apply {
            items = likedItems.toMutableList()
        }

        binding = FragmentFavoriteBinding.inflate(inflater, container, false).apply {
            reFavorite.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            reFavorite.adapter = adapter
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //메모리 누스를 방지하기 위해 뷰가 파괴될 때 바인딩 객체를 null로 설정
        binding = null
    }
}
