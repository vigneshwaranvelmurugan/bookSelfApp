package com.bookself.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.bookself.model.BooksResponse
import com.bookself.roomDatabase.AppDb
import com.bookself.roomDatabase.RoomBooks
import com.bookself.roomDatabase.RoomCategory

class RoomDbViewModel : ViewModel() {

    lateinit var db:AppDb
    lateinit var roomCatObjResponce: LiveData<List<RoomCategory>>
    lateinit var roomBookObjResponce: LiveData<List<RoomBooks>>

    fun insertCatData(context: Context, catName: String, bookCount: String,isSelected:Boolean) {
        db = Room.databaseBuilder(context, AppDb::class.java, "BookDB").allowMainThreadQueries().build()
        db.bookDao().insertCategory(RoomCategory(catName = catName, bookCount = bookCount,isSelected = isSelected))
    }

    fun updateCatData(context: Context, catId: Int,isSelected:Boolean) {
        db = Room.databaseBuilder(context, AppDb::class.java, "BookDB").allowMainThreadQueries().build()
        db.bookDao().updateCategory(isSelected,catId)
    }

    fun getAllCategory(context: Context) : LiveData<List<RoomCategory>>? {
        db = Room.databaseBuilder(context, AppDb::class.java, "BookDB").build()
        roomCatObjResponce = db.bookDao().getAllCategory()
        return roomCatObjResponce
    }

    fun getSelectedCategory(context: Context) : LiveData<List<RoomCategory>>? {
        db = Room.databaseBuilder(context, AppDb::class.java, "BookDB").build()
        roomCatObjResponce = db.bookDao().getSelectedCategory(true)
        return roomCatObjResponce
    }

    fun insertBookData(context: Context, bookResponse: BooksResponse, is_interested:Boolean) {
        db = Room.databaseBuilder(context, AppDb::class.java, "BookDB").allowMainThreadQueries().build()
        db.bookDao().insertBook(RoomBooks(title = bookResponse.title, isbn = bookResponse.isbn,pageCount = bookResponse.pageCount,publishedDate = bookResponse.publishedDate!!.date,thumbnailUrl = bookResponse.thumbnailUrl,shortDescription = bookResponse.shortDescription,longDescription = bookResponse.longDescription,status = bookResponse.status,authors = bookResponse.authors,categories = bookResponse.categories,isInterested = is_interested))
    }

    fun getAllBookList(context: Context) : LiveData<List<RoomBooks>>? {
        db = Room.databaseBuilder(context, AppDb::class.java, "BookDB").build()
        roomBookObjResponce = db.bookDao().getAllBooks()
        return roomBookObjResponce
    }

    fun getInterestedBookList(context: Context) : LiveData<List<RoomBooks>>? {
        db = Room.databaseBuilder(context, AppDb::class.java, "BookDB").build()
        roomBookObjResponce = db.bookDao().getInterestedBooks(true)
        return roomBookObjResponce
    }

    fun getBookDetail(context: Context,isbn:String) : LiveData<List<RoomBooks>>? {
        db = Room.databaseBuilder(context, AppDb::class.java, "BookDB").build()
        roomBookObjResponce = db.bookDao().getBookDetail(isbn)
        return roomBookObjResponce
    }

    val isFavEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun updateBookData(context: Context, isbn: String,is_interested: Boolean) {
        db = Room.databaseBuilder(context, AppDb::class.java, "BookDB").allowMainThreadQueries().build()
        db.bookDao().updateBookFav(is_interested,isbn)
    }
}