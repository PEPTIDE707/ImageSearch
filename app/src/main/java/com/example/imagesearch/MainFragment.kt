package com.example.imagesearch

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearch.databinding.FragmentMainBinding
import com.example.imagesearch.data.SearchResponse
import com.example.imagesearch.model.SearchItemModel
import com.example.imagesearch.recyclerview.MyAdapter
import com.example.imagesearch.retrofit.NetWorkClient.searchNetWork
import com.example.imagesearch.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment() {

//    interface OnFavoriteChangeListener{
//        fun onFavoriteChanged()
//    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var mItems: Context
    private lateinit var adapter: MyAdapter
    private lateinit var gridmanager: StaggeredGridLayoutManager

    private var resItems: ArrayList<SearchItemModel> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mItems = context
    }

//    private var _binding: FragmentMainBinding? = null
//    private val binding get() = _binding!!
//
//    private var _binding2: ItemBinding? = null
//    private val binding2 get() = _binding2!!
//
//    private lateinit var adapter: MyAdapter
//    private val dataList = mutableListOf<Document>()
//
//    private lateinit var likeadapter: LikeAdapter
//    private val dataList2 = mutableListOf<Like>()

    //var onFavoriteChangeListener: OnFavoriteChangeListener? = null

//    private var isLike = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        setupViews()//뷰 초기 설정
        setupListeners()//리스너 설정

        return binding.root
    }

    private fun setupViews(){
        //RecyclerView 설정
        gridmanager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.reMain.layoutManager = gridmanager

        adapter = MyAdapter(mItems)
        binding.reMain.adapter = adapter
        binding.reMain.itemAnimator = null

        //최근 검색어를 가져와 EditText에 설정
        val lastSearch = Utils.getLastSearch(requireContext())
        binding.etSearch.setText(lastSearch)

    }

    private fun setupListeners(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.btnFragmentSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            if (query.isNotEmpty()) {
                Utils.saveLastSearch(requireContext(), query)
                adapter.clearItem()
                fetchImageResults(query)
            } else {
                Toast.makeText(mItems, "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }

            saveData()

            //키보드 숨기기
            imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        }

        loadData()
    }

    private fun fetchImageResults(query: String) {
        searchNetWork.getSearch(query, "recency",1, 80)
            ?.enqueue(object : Callback<SearchResponse?>{
                override fun onResponse(call: Call<SearchResponse?>, response: Response<SearchResponse?>) {
                    response.body()?.meta?.let { meta ->
                        if (meta.total_count > 0){
                            response.body()!!.documents.forEach{ document ->
                                val title = document.display_sitename
                                val datetiem = document.datetime
                                val url = document.thumbnail_url
                                resItems.add(SearchItemModel(title, datetiem, url))
                            }
                        }
                    }
                    adapter.items = resItems
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<SearchResponse?>, t: Throwable) {
                    Log.e("ImageSearch","onFailure: ${t.message}")
                }
            })

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

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        //어댑터 연결 GridLayout
//        adapter = MyAdapter(dataList)
//        binding.reMain.adapter = adapter
//        binding.reMain.layoutManager = GridLayoutManager(context, 2)
//
//        likeadapter = LikeAdapter(dataList2)
//
//
//        binding2.imgHeart.setImageResource(if (isLike){R.drawable.blueheart}else{R.drawable.heartline})
//
//        binding2.imgHeart.setOnClickListener {
//            if (!isLike){
//                binding2.imgHeart.setImageResource(R.drawable.blueheart)
//                isLike = true
//            }else{
//                binding2.imgHeart.setImageResource(R.drawable.heartline)
//                isLike = false
//            }
//            //onFavoriteChangeListener?.onFavoriteChanged()
//        }
//
//
//        //검색 버튼 누르면 작동
//        binding.btnFragmentSearch.setOnClickListener {
//            val search = binding.etSearch.text.toString()
//            getSearchImg(search)
//            hideKeyboard()
//            saveData()
//        }
//        loadData()
//    }

//    private fun saveData(){
//        //프래그먼트에서는 앞에 requireActivity()를 붙여줘야 사용 가능
//        val pref = requireActivity().getSharedPreferences("pref", 0)
//        val edit = pref.edit()
//        edit.putString("name", binding.etSearch.text.toString())
//        edit.apply()
//    }
//
//    private fun loadData(){
//        val pref = requireActivity().getSharedPreferences("pref", 0)
//        binding.etSearch.setText(pref.getString("name", ""))
//    }

//    private fun getSearchImg(search: String){
//        NetWorkClient.searchNetWork.getSearch(query = search,1,80).enqueue(object : Callback<SearchResponse>{
//            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
//                val body = response.body()
//
//                body?.let {
//                    dataList.addAll(it.documents)
//                }
//                adapter.notifyDataSetChanged()
//            }
//            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
//                Log.d("api검사", "네트워크 오류/ 데이터 변환 오류")
//            }
//        })
//    }

}