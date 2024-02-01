package com.example.imagesearch.viewpager2

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(fragmentActvity: MainActivity) : FragmentStateAdapter(fragmentActvity) {
    private var fragments: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment){
        fragments.add(fragment)
        notifyItemInserted(fragments.size -1)
    }

    fun removeFragement(){
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
    }
}