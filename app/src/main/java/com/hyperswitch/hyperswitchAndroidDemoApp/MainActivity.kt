package com.hyperswitch.hyperswitchAndroidDemoApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.hyperswitch.hyperswitchdemoapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.button2).setOnClickListener {
            startActivity(Intent(this@MainActivity, CheckoutActivity::class.java))
        }
    }
}