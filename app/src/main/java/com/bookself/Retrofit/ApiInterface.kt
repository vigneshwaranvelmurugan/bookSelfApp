package com.bookself.Retrofit

import com.bookself.model.BooksResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("books/")
    fun getServices() : Call<List<BooksResponse>>

}