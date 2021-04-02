package com.bookself.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [(RoomCategory::class),(RoomBooks::class)],version = 1)
@TypeConverters(ArrayConverter::class)
abstract class AppDb : RoomDatabase() {

    abstract fun bookDao(): RoomCategoryDao
}