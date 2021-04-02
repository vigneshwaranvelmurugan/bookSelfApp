package com.bookself.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class RoomCategory(
    @ColumnInfo(name = "cat_name") var catName: String?,
    @ColumnInfo(name = "book_count") var bookCount: String?,
    @ColumnInfo(name = "is_selected") var isSelected: Boolean
)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}