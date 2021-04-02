package com.bookself.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "books")
data class RoomBooks(
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "isbn") var isbn: String?,
    @ColumnInfo(name = "pageCount") var pageCount: Int?,
    @ColumnInfo(name = "publishedDate") var publishedDate: String?,
    @ColumnInfo(name = "thumbnailUrl") var thumbnailUrl: String?,
    @ColumnInfo(name = "shortDescription") var shortDescription: String?,
    @ColumnInfo(name = "longDescription") var longDescription: String?,
    @ColumnInfo(name = "status") var status: String?,
    @ColumnInfo(name = "authors") var authors: ArrayList<String>,
    @ColumnInfo(name = "categories") var categories: ArrayList<String>,
    @ColumnInfo(name = "is_interested") var isInterested: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}