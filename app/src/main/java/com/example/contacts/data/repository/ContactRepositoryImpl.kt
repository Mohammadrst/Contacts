package com.example.contacts.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import com.example.contacts.data.db.AppDAO
import com.example.contacts.data.model.contacts

class ContactRepositoryImpl(val dao: AppDAO) : ContactsRepository {


    @SuppressLint("Range")
    override fun getAllContactsFromPhone(ctx : Context): ArrayList<contacts> {
            val contactsList = ArrayList<contacts>()
            val contentResolver = ctx.contentResolver
            val cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
            if (cursor!!.count > 0) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    contactsList.add(contacts(name, number))
                }
            }
            return contactsList
    }

    override fun addContact(contact: contacts) {
        return dao.addContact(contact)
    }

    override fun getAllContacts(): List<contacts> {
        return dao.getAllContacts()
    }

    override fun deleteContact(contact: contacts) {
        dao.deleteContact(contact)
    }

    override fun deleteAllContacts() {
        dao.deleteAllContacts()
    }
}