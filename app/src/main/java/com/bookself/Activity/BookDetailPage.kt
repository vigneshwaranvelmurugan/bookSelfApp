package com.bookself.Activity

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bookself.R
import com.bookself.ViewModel.RoomDbViewModel
import com.bumptech.glide.Glide
import org.w3c.dom.Text
import java.lang.Exception

class BookDetailPage : AppCompatActivity() {
    lateinit var roomDBViewModel: RoomDbViewModel
    lateinit var bookImage:ImageView
    lateinit var bookTitle:TextView
    lateinit var authorTitle:TextView
    lateinit var bookAuthor:TextView
    lateinit var bookISBN:TextView
    lateinit var bookShortDescription:TextView
    lateinit var bookFullDescription:TextView
    lateinit var favIcon:ImageView
    lateinit var favIconLayout:LinearLayout
    lateinit var backIcon:ImageView
    lateinit var publisherDate:TextView
    var isbnStr:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail_page)
        bindUI()
    }
    fun bindUI(){
        bookTitle=findViewById(R.id.bookTitle)
        bookImage=findViewById(R.id.bookImage)
        authorTitle=findViewById(R.id.authorTitle)
        bookAuthor=findViewById(R.id.bookAuthor)
        bookISBN=findViewById(R.id.bookISBN)
        bookShortDescription=findViewById(R.id.bookShortDescription)
        bookFullDescription=findViewById(R.id.bookFullDescription)
        backIcon=findViewById(R.id.backIcon)
        favIcon=findViewById(R.id.favIcon)
        favIconLayout=findViewById(R.id.favIconLayout)
        publisherDate=findViewById(R.id.publisherDate)
        backIcon.setOnClickListener {
            finish()
        }
        isbnStr=intent.getStringExtra("isbn")!!
        roomDBViewModel = ViewModelProvider(this).get(RoomDbViewModel::class.java)
        roomDBViewModel.isFavEnabled.observe(this, Observer { isFav->
            favIcon.setTag(R.id.facIconTag, isFav)
            favIcon.setImageResource(if (isFav) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
        })
        roomDBViewModel.getBookDetail(applicationContext!!,isbnStr)!!.observe(this, androidx.lifecycle.Observer { categoryBookbserver ->
            if (categoryBookbserver!=null){
                try {
                    if (categoryBookbserver.isNotEmpty()){
                        val bookData=categoryBookbserver[0]
                        bookTitle.setText(bookData.title)
                        if (bookData.thumbnailUrl!!.isNotEmpty()){
                            Glide.with(applicationContext).load(bookData.thumbnailUrl).into(bookImage);
                        }
                        authorTitle.text=if (bookData.authors.size>1) "Authors" else "Author"
                        bookAuthor.text = TextUtils.join(", ", bookData.authors)
                        bookISBN.text=bookData.isbn
                        bookShortDescription.text=bookData.shortDescription
                        bookFullDescription.text=bookData.longDescription
                        var publishedDate:String=bookData.publishedDate!!
                        try {
                            val separated= publishedDate.split("T")
                            publishedDate=separated[0]
                        }catch (e:java.lang.Exception){

                        }
                        publisherDate.text=publishedDate
                        roomDBViewModel.isFavEnabled.value=bookData.isInterested
                    }
                }catch (e:Exception){
                    finish()
                }
            }
        })

        favIcon.setOnClickListener {

            if (favIcon.getTag(R.id.facIconTag)==true){
                roomDBViewModel.isFavEnabled.value=false
                roomDBViewModel.updateBookData(applicationContext,isbnStr,false)
                Toast.makeText(applicationContext,"Removed to My Interested",Toast.LENGTH_SHORT).show()
            }
            else{
                roomDBViewModel.isFavEnabled.value=true
                roomDBViewModel.updateBookData(applicationContext,isbnStr,true)
                Toast.makeText(applicationContext,"Added to My Interested",Toast.LENGTH_SHORT).show()
            }
        }
    }
}