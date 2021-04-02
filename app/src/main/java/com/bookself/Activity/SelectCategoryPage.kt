package com.bookself.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bookself.Adapter.BookCategoryAdapter
import com.bookself.Adapter.SelectCategoryAdapter
import com.bookself.R
import com.bookself.ViewModel.RoomDbViewModel
import com.bookself.roomDatabase.RoomCategory

class SelectCategoryPage : AppCompatActivity() {
    lateinit var headerButton: TextView
    lateinit var categoryListRecycle: RecyclerView
    lateinit var editSearch: EditText
    lateinit var clearText: ImageView
    lateinit var roomDBViewModel: RoomDbViewModel
    lateinit var catAdapter: SelectCategoryAdapter
    var allCategoryList: ArrayList<RoomCategory?>? = ArrayList()
    var updatedCatList: ArrayList<RoomCategory?>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category_page)
        roomDBViewModel = ViewModelProvider(this).get(RoomDbViewModel::class.java)

        bindHeaderUI()
        bindUI()
    }

    fun bindHeaderUI() {
        val backIcon: ImageView = findViewById(R.id.backIcon)
        backIcon.setOnClickListener {
            finish()
        }
        val headerTitle: TextView = findViewById(R.id.headerTitle)
        headerTitle.text = resources.getString(R.string.select_category)

        headerButton = findViewById(R.id.headerButton)
        headerButton.text = resources.getString(R.string.done)
        headerButton.visibility = View.VISIBLE
        headerButton.setOnClickListener {
            if (updatedCatList!!.isNotEmpty()){
                for (i in 0 until updatedCatList!!.size){
                    val catData=updatedCatList!![i]
                    roomDBViewModel.updateCatData(applicationContext,catData!!.Id!!,catData!!.isSelected)
                }
            }
            Toast.makeText(applicationContext,"Category Update Success!!",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun bindUI() {
        editSearch = findViewById(R.id.editSearch)
        categoryListRecycle = findViewById(R.id.categoryListRecycle)
        clearText = findViewById(R.id.clearText)
        roomDBViewModel = ViewModelProvider(this).get(RoomDbViewModel::class.java)
        clearText.setOnClickListener {
            editSearch.setText("")
        }
        roomDBViewModel.getAllCategory(applicationContext!!)!!
            .observe(this, androidx.lifecycle.Observer { observer ->
                if (observer != null) {
                    allCategoryList!!.addAll(observer)
                    setAdapter()

                }
            })

    }

    fun setAdapter() {
        categoryListRecycle.setHasFixedSize(true)
        categoryListRecycle.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        catAdapter = SelectCategoryAdapter(
            applicationContext!!,
            allCategoryList,
            object : SelectCategoryAdapter.SelectCategoryAdapterLis {
                override fun checkCategoryStatus(catData: RoomCategory, position: Int) {
                    updateCategory(catData)
                    catAdapter.notifyDataSetChanged()
                }
            })
        categoryListRecycle.adapter = catAdapter
        editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                filterCategory(s.toString())
                clearText.visibility = if (s.toString().isNotEmpty()) View.VISIBLE else View.GONE
            }
        })

    }

    fun updateCategory(catData: RoomCategory) {
        for (i in 0 until allCategoryList!!.size) {
            if (allCategoryList!![i]!!.Id == catData.Id) {
                allCategoryList!![i]!!.isSelected = catData.isSelected
            }
        }

        if (updatedCatList!!.isEmpty()){
            updatedCatList!!.add(catData)
        }
        else{
            var alreadyAdded:Boolean=false
            for (i in 0 until updatedCatList!!.size){
                if (updatedCatList!![i]!!.Id==catData.Id){
                    alreadyAdded=true
                    updatedCatList!![i]!!.isSelected = catData.isSelected
                }
            }
            if (!alreadyAdded){
                updatedCatList!!.add(catData)
            }
        }
    }

    fun filterCategory(text: String) {
        val temp = ArrayList<RoomCategory?>()
        for (d in allCategoryList!!) {
            if (d != null) {
                if (d.catName!!.toLowerCase().contains(text.toLowerCase())) {
                    temp.add(d)
                }
            } else {
                temp.add(null)
            }
        }
        try {
            catAdapter.updateList(temp)
        } catch (e: Exception) {
        }
    }

}