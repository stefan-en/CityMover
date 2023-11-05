package com.example.citymoverscan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    val currentPageId = 1 // ID-ul primei pagini
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = window.decorView.rootView
        rootView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBackground))

        setContentView(R.layout.activity_main)
        var buttonScan = findViewById<Button>(R.id.buttonScan)

        buttonScan.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }
    }

}