package com.hyperswitch.hyperswitchAndroidDemoApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.hyperswitch.hyperswitchdemoapp.R

import com.stripe.android.PaymentConfiguration

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51BTUDGJAJfZb9HEBwDg86TN1KNprHjkfipXmEDMb0gSCassK5T3ZfxsAbcgKVmAIXF7oZ6ItlZZbXO6idTHE67IM007EwQ4uN3"
        )

        findViewById<Button>(R.id.checkout).setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }



    }
}