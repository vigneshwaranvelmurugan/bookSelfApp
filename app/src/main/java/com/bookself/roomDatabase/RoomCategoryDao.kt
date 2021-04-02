package com.bookself.roomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface RoomCategoryDao {
    @Query("SELECT * FROM category")
    fun getAllCategory(): LiveData<List<RoomCategory>>

    @Query("SELECT * FROM category WHERE is_selected IN (:isSelected)")
    fun getSelectedCategory(isSelected:Boolean): LiveData<List<RoomCategory>>

    @Insert
    fun insertCategory(vararg roomCategory: RoomCategory)

    @Query("UPDATE category SET is_selected = :is_selected WHERE id = :cat_id")
    fun updateCategory(is_selected: Boolean?, cat_id:Int)

    @Insert
    fun insertBook(vararg roomCategory: RoomBooks)

    @Query("SELECT * FROM books")
    fun getAllBooks(): LiveData<List<RoomBooks>>

    @Query("SELECT * FROM books WHERE is_interested IN (:is_interestedBooks)")
    fun getInterestedBooks(is_interestedBooks:Boolean): LiveData<List<RoomBooks>>

    @Query("SELECT * FROM books WHERE isbn IN (:isbn)")
    fun getBookDetail(isbn:String): LiveData<List<RoomBooks>>

    @Query("UPDATE books SET is_interested = :is_interested WHERE isbn = :isbn")
    fun updateBookFav(is_interested: Boolean?, isbn:String)
}
