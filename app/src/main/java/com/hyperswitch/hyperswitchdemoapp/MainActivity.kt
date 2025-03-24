package com.hyperswitch.hyperswitchdemoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        findViewById<Button>(R.id.checkoutButton).setOnClickListener {
            startActivity(Intent(this, CheckoutActivity::class.java))
        }
    }

}