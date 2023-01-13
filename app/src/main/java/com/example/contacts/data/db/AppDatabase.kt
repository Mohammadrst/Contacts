package com.example.contacts.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contacts.data.model.contacts

@Database(entities = [contacts::class], version = 1, exportSchema = true)
abstract class AppDatabase: RoomDatabase(){
    abstract fun appDAO() : AppDAO
}