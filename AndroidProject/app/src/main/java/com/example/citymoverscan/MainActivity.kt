package com.example.citymoverscan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var buttonScan = findViewById<Button>(R.id.buttonScan)

        buttonScan.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }
    }
}