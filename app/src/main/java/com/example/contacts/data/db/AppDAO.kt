package com.example.contacts.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.contacts.data.model.contacts


@Dao
interface AppDAO {

    @Insert
    fun addContact(contact : contacts)

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): List<contacts>

    @Delete
    fun deleteContact(contact : contacts)

    @Query("DELETE FROM contacts")
    fun deleteAllContacts()
}