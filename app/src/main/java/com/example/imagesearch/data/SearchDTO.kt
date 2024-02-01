package com.example.imagesearch.data

import android.os.Parcelable

/**
 * 이미지 검색 응답을 위한 모델 클래스.
 * Documents는 검색 결과의 각 항목을 나타내며, Meta는 검색 응답 메타 정보를 나타냅니다.
 */
data class SearchResponse(
    val meta: Meta, //이미지 메타데이터
    val documents: ArrayList<Document> //검색결과
)

data class Meta(
    val total_count:Int, //검색어에 검색된 문서 수
    val pageable_count:Int, // total_count 중 노출 가능 문서 수
    val is_end: Boolean // 현재 페이지가 마지막 페이지인지 여부
)

data class Document(
    val collection:String, // 컬렉션
    val thumbnail_url: String, // 미리보기 이미지 URL
    val image_url: String, // 이미지 URL
    val width: Int, // 이미지의 가로 길이
    val height: Int, // 이미지의 세로 길이
    val display_sitename: String, //출처
    val doc_url: String, // 문서 URL
    val datetime: String // 문서 작성 시간 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
)



