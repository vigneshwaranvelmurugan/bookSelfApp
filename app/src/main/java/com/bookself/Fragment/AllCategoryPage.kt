package com.bookself.Fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bookself.Activity.BookListCommonActivity
import com.bookself.Activity.MainActivity
import com.bookself.Activity.SelectCategoryPage
import com.bookself.Adapter.BookCategoryAdapter
import com.bookself.R
import com.bookself.ViewModel.ApiViewModel
import com.bookself.ViewModel.RoomDbViewModel
import com.bookself.roomDatabase.RoomCategory
import com.google.gson.Gson
import java.util.HashMap

class AllCategoryPage : Fragment() {
    private lateinit var rootView:View
    lateinit var editSearch:EditText
    lateinit var categoryListRecycle:RecyclerView
    lateinit var roomDBViewModel: RoomDbViewModel
    var selectedCatList: ArrayList<RoomCategory?>? = ArrayList()
    lateinit var catAdapter:BookCategoryAdapter
    lateinit var clearText:ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView= inflater.inflate(R.layout.all_category_layout, container, false)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        bindUI()
    }
    fun bindUI(){
        editSearch=rootView.findViewById(R.id.editSearch)
        categoryListRecycle=rootView.findViewById(R.id.categoryListRecycle)
        clearText=rootView.findViewById(R.id.clearText)
        roomDBViewModel = ViewModelProvider(this).get(RoomDbViewModel::class.java)
        clearText.setOnClickListener {
            editSearch.setText("")
        }
        roomDBViewModel.getSelectedCategory(activity!!)!!.observe(this, androidx.lifecycle.Observer { observer ->
            selectedCatList!!.clear()
            if (observer!=null){
                selectedCatList!!.addAll(observer)
                selectedCatList!!.add(null)
            }
            else{
                selectedCatList!!.add(null)
            }
            setAdapter()
        })

    }
    fun setAdapter(){
        categoryListRecycle.setHasFixedSize(true)
        categoryListRecycle.layoutManager=
            GridLayoutManager(
                activity,
                2
            )
        catAdapter=BookCategoryAdapter(activity!!,selectedCatList,object : BookCategoryAdapter.BookCategoryAdapterLis{
            override fun viewBook(position: Int, categoryName: String) {
                val intent=Intent(activity, BookListCommonActivity::class.java)
                intent.putExtra("categoryName",categoryName)
                startActivity(intent)
            }

            override fun addCategory() {
                val intent=Intent(activity,SelectCategoryPage::class.java)
                startActivity(intent)
            }

        })
        categoryListRecycle.adapter=catAdapter
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
                clearText.visibility=if (s.toString().isNotEmpty()) View.VISIBLE else View.GONE
            }
        })

    }

    fun filterCategory(text: String) {
        val temp = ArrayList<RoomCategory?>()
        for (d in selectedCatList!!) {
            if (d!=null){
                if (d.catName!!.toLowerCase().contains(text.toLowerCase())) {
                    temp.add(d)
                }
            }
            else{
                temp.add(null)
            }
        }
        try {
            catAdapter.updateList(temp)
        } catch (e: Exception) {
        }
    }

}