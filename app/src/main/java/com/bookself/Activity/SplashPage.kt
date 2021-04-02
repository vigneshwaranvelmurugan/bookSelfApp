package com.bookself.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bookself.R
import com.bookself.ViewModel.ApiViewModel
import com.bookself.ViewModel.RoomDbViewModel
import java.util.*
import kotlin.collections.ArrayList


class SplashPage : AppCompatActivity() {
    lateinit var apiViewModel: ApiViewModel
    lateinit var roomDBViewModel: RoomDbViewModel
    lateinit var loaderProgress: ProgressBar
    lateinit var categoryArray: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh_page)
        apiViewModel = ViewModelProvider(this).get(ApiViewModel::class.java)
        roomDBViewModel = ViewModelProvider(this).get(RoomDbViewModel::class.java)
        loaderProgress = findViewById(R.id.loaderProgress)
        showLoader()
        roomDBViewModel.getAllCategory(applicationContext)!!
            .observe(this, androidx.lifecycle.Observer { observer ->
                if (observer.isNullOrEmpty()) {
                    apiViewModel.booksList!!.observe(this, androidx.lifecycle.Observer { apiResponce ->
                        categoryArray = ArrayList()
                        for (i in apiResponce.indices) {
                            categoryArray.addAll(apiResponce[i].categories!!)
                            roomDBViewModel.getAllBookList(applicationContext)!!.observe(this, androidx.lifecycle.Observer { observer ->
                                if (observer.isNullOrEmpty()){
                                    roomDBViewModel.insertBookData(applicationContext,apiResponce!![i],false)
                                }
                            })
                        }
                        val counterMap: MutableMap<String, Int?> = HashMap()
                        for (j in categoryArray.indices) {
                            if (counterMap.containsKey(categoryArray[j])) {
                                counterMap[categoryArray[j]] = counterMap[categoryArray[j]]!! + 1
                            } else {
                                counterMap[categoryArray[j]] = 1
                            }
                        }


                        for ((key, value) in counterMap.entries) {
                            roomDBViewModel.insertCatData(
                                applicationContext,
                                key.toString(),
                                value.toString(),
                                false
                            )
                        }
                        moveNextPage()
                    })
                } else {
                    moveNextPage()
                }
            })

    }

    fun moveNextPage() {
        hideLoader()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun showLoader() {
        loaderProgress.visibility = View.VISIBLE
    }

    fun hideLoader() {
        loaderProgress.visibility = View.GONE
    }
}
