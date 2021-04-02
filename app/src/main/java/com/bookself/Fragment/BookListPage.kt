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
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bookself.Activity.BookDetailPage
import com.bookself.Adapter.BookListAdapter
import com.bookself.R
import com.bookself.ViewModel.RoomDbViewModel
import com.bookself.roomDatabase.RoomBooks

class BookListPage(categoryName: String?, isInterestedBook: Boolean) : Fragment() {
    private lateinit var rootView: View
    lateinit var editSearch: EditText
    lateinit var bookListRecycle: RecyclerView
    lateinit var roomDBViewModel: RoomDbViewModel
    var selectedBooktList: ArrayList<RoomBooks?>? = ArrayList()
    lateinit var bookListAdapter: BookListAdapter
    lateinit var clearText: ImageView
    lateinit var notFoundLayout:LinearLayout
    var categoryName:String
    var isInterestedBook:Boolean
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView= inflater.inflate(R.layout.booklist_layout, container, false)
        return rootView
    }
    init {
        this.categoryName=categoryName!!
        this.isInterestedBook=isInterestedBook
    }

    override fun onResume() {
        super.onResume()
        bindUI()
    }
    fun bindUI(){
        editSearch=rootView.findViewById(R.id.editSearch)
        bookListRecycle=rootView.findViewById(R.id.bookListRecycle)
        clearText=rootView.findViewById(R.id.clearText)
        notFoundLayout=rootView.findViewById(R.id.notFoundLayout)
        roomDBViewModel = ViewModelProvider(this).get(RoomDbViewModel::class.java)
        clearText.setOnClickListener {
            editSearch.setText("")
        }
        if (isInterestedBook){
            roomDBViewModel.getInterestedBookList(activity!!)!!.observe(this, androidx.lifecycle.Observer { favBookServer ->
                selectedBooktList!!.clear()
                if (favBookServer!=null){
                    selectedBooktList!!.addAll(favBookServer)
                    setAdapter()
                    if (selectedBooktList!!.isNotEmpty()){
                        hideNoItem()
                    }
                    else{
                        enableNoItem()
                    }
                }
                else{
                    enableNoItem()
                }
            })
        }else{
            roomDBViewModel.getAllBookList(activity!!)!!.observe(this, androidx.lifecycle.Observer { categoryBookbserver ->
                selectedBooktList!!.clear()
                if (categoryBookbserver!=null){
                    for (i in 0 until categoryBookbserver.size){
                        if (categoryBookbserver[i].categories.contains(categoryName)){
                            selectedBooktList!!.add(categoryBookbserver[i])
                        }
                    }
                    setAdapter()
                }
            })
        }


    }
    fun hideNoItem(){
        notFoundLayout.visibility=View.GONE
    }

    fun enableNoItem(){
        notFoundLayout.visibility=View.VISIBLE
    }
    fun setAdapter(){
        bookListRecycle.setHasFixedSize(true)
        bookListRecycle.layoutManager=
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        bookListAdapter= BookListAdapter(activity!!,selectedBooktList,object : BookListAdapter.BookListAdapterLis{
            override fun viewBookDetail(bookData: RoomBooks, position: Int) {
                val intent=Intent(activity,BookDetailPage::class.java)
                intent.putExtra("isbn",bookData.isbn)
                startActivity(intent)
            }

        })
        bookListRecycle.adapter=bookListAdapter
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
        val temp = ArrayList<RoomBooks?>()
        for (d in selectedBooktList!!) {
            if (d!=null){
                if (d.title!!.toLowerCase().contains(text.toLowerCase())) {
                    temp.add(d)
                }
            }
            else{
                temp.add(null)
            }
        }
        try {
            bookListAdapter.updateList(temp)
        } catch (e: Exception) {
        }
    }

}