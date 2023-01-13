package com.example.contacts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class contacts(var name: String,var phone : String){

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
