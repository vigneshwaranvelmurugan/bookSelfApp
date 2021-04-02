package com.bookself.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bookself.Retrofit.RetrofitClient
import com.bookself.model.BooksResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object BookApiRepo {

    val serviceSetterGetter = MutableLiveData<List<BooksResponse>>()

    fun getServicesApiCall(): MutableLiveData<List<BooksResponse>> {

        val call = RetrofitClient.apiInterface.getServices()

        call.enqueue(object: Callback<List<BooksResponse>> {
            override fun onFailure(call: Call<List<BooksResponse>>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<BooksResponse>>,
                response: Response<List<BooksResponse>>
            ) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())
                serviceSetterGetter.value = response.body()
            }
        })

        return serviceSetterGetter
    }
}