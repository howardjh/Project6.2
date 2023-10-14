package com.example.project6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() { // MainActivity class extending AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // call super onCreate
        setContentView(R.layout.activity_main)
    }
}
