package com.example.imagesearch

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.databinding.FragmentMainBinding
import com.example.imagesearch.data.Document
import com.example.imagesearch.data.Like
import com.example.imagesearch.data.SearchResponse
import com.example.imagesearch.databinding.ItemBinding
import com.example.imagesearch.recyclerview.LikeAdapter
import com.example.imagesearch.recyclerview.MyAdapter
import com.example.imagesearch.retrofit.NetWorkClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

//interface OnFavoriteChangeListener{
//    fun onFavoriteChanged()
//}

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var _binding2: ItemBinding? = null
    private val binding2 get() = _binding2!!

    private lateinit var adapter: MyAdapter
    private val dataList = mutableListOf<Document>()

    private lateinit var likeadapter: LikeAdapter
    private val dataList2 = mutableListOf<Like>()

    //var onFavoriteChangeListener: OnFavoriteChangeListener? = null

    private var isLike = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        _binding2 = ItemBinding.inflate(inflater, container, false)

        return binding.root
        return binding2.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //어댑터 연결 GridLayout
        adapter = MyAdapter(dataList)
        binding.reMain.adapter = adapter
        binding.reMain.layoutManager = GridLayoutManager(context, 2)

        likeadapter = LikeAdapter(dataList2)


        binding2.imgHeart.setImageResource(if (isLike){R.drawable.blueheart}else{R.drawable.heartline})

        binding2.imgHeart.setOnClickListener {
            if (!isLike){
                binding2.imgHeart.setImageResource(R.drawable.blueheart)
                isLike = true
            }else{
                binding2.imgHeart.setImageResource(R.drawable.heartline)
                isLike = false
            }
            //onFavoriteChangeListener?.onFavoriteChanged()
        }


        //검색 버튼 누르면 작동
        binding.btnFragmentSearch.setOnClickListener {
            val search = binding.etSearch.text.toString()
            getSearchImg(search)
            hideKeyboard()
            saveData()
        }
        loadData()
    }

    private fun saveData(){
        //프래그먼트에서는 앞에 requireActivity()를 붙여줘야 사용 가능
        val pref = requireActivity().getSharedPreferences("pref", 0)
        val edit = pref.edit()
        edit.putString("name", binding.etSearch.text.toString())
        edit.apply()
    }

    private fun loadData(){
        val pref = requireActivity().getSharedPreferences("pref", 0)
        binding.etSearch.setText(pref.getString("name", ""))
    }

    private fun getSearchImg(search: String){
        NetWorkClient.searchNetWork.getSearch(query = search,1,80).enqueue(object : Callback<SearchResponse>{
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                val body = response.body()

                body?.let {
                    dataList.addAll(it.documents)
                }
                adapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.d("api검사", "네트워크 오류/ 데이터 변환 오류")
            }
        })
    }


    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken,0)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}