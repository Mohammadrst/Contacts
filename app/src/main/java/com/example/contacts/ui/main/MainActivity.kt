package com.example.contacts.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var main_binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main_binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main_binding.root)

    }
}