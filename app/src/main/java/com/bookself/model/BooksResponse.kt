package com.bookself.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BooksResponse {
    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("isbn")
    @Expose
    var isbn: String? = null

    @SerializedName("pageCount")
    @Expose
    var pageCount: Int? = null

    @SerializedName("publishedDate")
    @Expose
    var publishedDate: PublishedDate? = null

    @SerializedName("thumbnailUrl")
    @Expose
    var thumbnailUrl: String? = null

    @SerializedName("shortDescription")
    @Expose
    var shortDescription: String? = null

    @SerializedName("longDescription")
    @Expose
    var longDescription: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("authors")
    @Expose
    var authors: ArrayList<String> = ArrayList()

    @SerializedName("categories")
    @Expose
    var categories: ArrayList<String> = ArrayList()

    inner class PublishedDate {
        @SerializedName("\$date")
        @Expose
        var date: String? = null
    }
}