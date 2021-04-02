package com.bookself.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bookself.model.BooksResponse
import com.bookself.repo.BookApiRepo

class ApiViewModel : ViewModel(){
    var servicesLiveData: MutableLiveData<List<BooksResponse>>? = null

    val booksList: MutableLiveData<List<BooksResponse>>? by lazy {
        servicesLiveData = BookApiRepo.getServicesApiCall()
        return@lazy servicesLiveData
    }


}