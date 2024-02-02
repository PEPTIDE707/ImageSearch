package com.example.imagesearch.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.imagesearch.FavoriteFragment
import com.example.imagesearch.MainFragment
import com.example.imagesearch.model.SearchItemModel
import com.example.imagesearch.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    //ActivityMainBinding은 mainActivity의 레이아웃 바인딩 객체
    private lateinit var binding: ActivityMainBinding

    //좋아요를 눌러 선택된 아이템을 저장하는 리스트
    var likedItem: ArrayList<SearchItemModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()

    }

    private fun initViewPager(){
        val tabTextList = listOf("검색", "좋아요")

        var viewPager2Adapter = ViewPager2Adapter(this)
        viewPager2Adapter.addFragment(MainFragment())
        viewPager2Adapter.addFragment(FavoriteFragment())

        binding.mainViewPager.apply {
            adapter = viewPager2Adapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){})
        }

        TabLayoutMediator(binding.mainTab, binding.mainViewPager){tab, position ->
            tab.text = tabTextList[position]
        }.attach()

    }

    fun addLikedItem(item: SearchItemModel){
        if(!likedItem.contains(item)){
            likedItem.add(item)
        }
    }
//좋아요가 취소된 아이템을 likedItem 리스트에서 제거하는 함수
    fun removeLikedItem(item: SearchItemModel){
        likedItem.remove(item)
    }
}