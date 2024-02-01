package com.example.imagesearch.retrofit


import com.example.imagesearch.data.Constants.REST_API_KEY
import com.example.imagesearch.data.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface NetWorkInterface {
    @Headers("Authorization: KakaoAK $REST_API_KEY")
    @GET("v2/search/image")
     fun getSearch(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size")size: Int = 80
   ): Call<SearchResponse>
}
