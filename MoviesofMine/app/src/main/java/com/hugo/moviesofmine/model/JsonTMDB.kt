package com.hugo.moviesofmine.model

import com.google.gson.annotations.SerializedName

data class JsonTMDB(
    @SerializedName("page") var page: String,
    @SerializedName("total_pages") var total_pages: String,
    @SerializedName("results") var results: List<JsonMovie>,
    @SerializedName("total_results") var total_results: String) {

}