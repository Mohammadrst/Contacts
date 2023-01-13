package com.example.contacts.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.contacts.data.model.contacts

interface ContactsRepository {


    fun getAllContactsFromPhone(ctx : Context) : ArrayList<contacts>

    fun addContact(contact: contacts)

    fun getAllContacts() : List<contacts>

    fun deleteContact(contact: contacts)

    fun deleteAllContacts()

}