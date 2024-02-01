package com.example.imagesearch.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.imagesearch.FavoriteFragment
import com.example.imagesearch.MainFragment
import com.example.imagesearch.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

}