package com.example.contacts.data.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contacts.data.model.contacts
import com.example.contacts.data.repository.ContactsRepository


class HomeViewModel(protected val repository: ContactsRepository) : ViewModel() {




    fun addContact(contact: contacts) {
         repository.addContact(contact)
    }


   fun getAllContacts(): List<contacts> {
        return repository.getAllContacts()
    }

    fun deleteContact(contact: contacts) {
        repository.deleteContact(contact)
    }

    fun deleteAllContacts() {
        repository.deleteAllContacts()
    }

    fun getAllContactsFromPhone(ctx : Context):ArrayList<contacts> {
        return repository.getAllContactsFromPhone(ctx)
    }

}