package com.hyperswitch.hyperswitchAndroidDemoApp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel.reset
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Handler
import com.hyperswitch.hyperswitchdemoapp.R
import io.hyperswitch.JuspayPayment
import io.hyperswitch.PaymentConfiguration
import io.hyperswitch.model.ConfirmPaymentIntentParams
import io.hyperswitch.model.PaymentMethodCreateParams
import io.hyperswitch.payments.paymentlauncher.PaymentLauncher
import io.hyperswitch.payments.paymentlauncher.PaymentResult
import io.hyperswitch.paymentsheet.PaymentSheet
import io.hyperswitch.paymentsheet.PaymentSheetResult
import io.hyperswitch.view.CardInputWidget
import org.json.JSONException
import org.json.JSONObject
import kotlin.text.Charsets.UTF_8


class CheckoutActivity : AppCompatActivity(), JuspayPayment {

    companion object {
        private const val BACKEND_URL = "http://10.0.2.2:4444"
    }


    private lateinit var paymentIntentClientSecret: String
    private lateinit var paymentSheet: PaymentSheet
    private lateinit var paymentLauncher: PaymentLauncher
    private lateinit var paymentSheetPayNow: Button
    private lateinit var widgetPayNow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)


        // replace the test publishable key with your hyperswitch publishable key
        PaymentConfiguration.init(
            applicationContext,
            "pk_snd_1e5425f5dea94ee793cf34ea326294d8"
        )


        paymentSheetPayNow = findViewById(R.id.button)
        paymentSheetPayNow.isEnabled = false

        widgetPayNow = findViewById(R.id.button1)
        widgetPayNow.isEnabled = false



        /***
         *
         * fetch client secret form server using order details
         *
         */
        val shoppingCartContent = """
            {
                "items": [
                    {"id":"xl-tshirt"}
                ]
            }
        """
        fetchingClientSecret(shoppingCartContent)



        /***
         *
         * customize payment sheet UI
         *
         */
        val appearance = PaymentSheet.Appearance(themes = PaymentSheet.Appearance.Themes.Dark)



        /***
         *
         * Proceeding to pay using payment sheet btn
         *
         */
        paymentSheet = PaymentSheet(
            this
        ) { paymentSheetResult: PaymentSheetResult? ->
            this.onPaymentSheetResult(
                paymentSheetResult
            )
        }
        paymentSheetPayNow.setOnClickListener  {
            paymentSheetPayNow.isEnabled = false
            val configuration = PaymentSheet.Configuration.Builder("Example, Inc.")
                .appearance(appearance)
                .build()

            paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret, configuration)
        }



        /***
         *
         * Proceeding to pay using card widget btn
         *
         */
        val paymentConfiguration: PaymentConfiguration = PaymentConfiguration.getInstance(
            applicationContext
        )
        paymentLauncher = PaymentLauncher.Companion.create(
            this,
            paymentConfiguration.publishableKey,
            paymentConfiguration.stripeAccountId,
            ::onPaymentResult
        )

        widgetPayNow.setOnClickListener  {
            widgetPayNow.isEnabled = false
            val cardInputWidget: CardInputWidget = findViewById(R.id.cardElement)
            val params: PaymentMethodCreateParams = cardInputWidget.paymentMethodCreateParams
            val confirmParams = ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(
                params,
                paymentIntentClientSecret
            )
            paymentLauncher.confirm(confirmParams)
        }


    }



    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult?) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Completed -> {
                runOnUiThread {
                    Toast.makeText(this@CheckoutActivity, "Payment complete!", Toast.LENGTH_LONG).show()
                }
                startActivity(Intent(this@CheckoutActivity, MainActivity::class.java))
            }
            is PaymentSheetResult.Canceled -> {
                Log.i("TAG", "Payment canceled!")
                runOnUiThread{
                    paymentSheetPayNow.isEnabled = true
                }
            }
            is PaymentSheetResult.Failed -> {
                runOnUiThread {
                    AlertDialog.Builder(this@CheckoutActivity)
                        .setTitle("Payment failed")
                        .setMessage(paymentSheetResult.error.localizedMessage)
                        .setPositiveButton("Ok", null)
                        .show()
                }
                startActivity(Intent(this@CheckoutActivity, MainActivity::class.java))
            }
            else -> {
                runOnUiThread {
                    AlertDialog.Builder(this@CheckoutActivity)
                        .setTitle("Something went wrong")
                        .setPositiveButton("Ok", null)
                        .show()
                }
                startActivity(Intent(this@CheckoutActivity, MainActivity::class.java))
            }
        }

    }


    fun onPaymentResult(paymentResult: PaymentResult) {
        when (paymentResult) {
            is PaymentResult.Completed -> {
                Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show()
            }
            is PaymentResult.Canceled -> {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
            }
            is PaymentResult.Failed -> {
                Toast.makeText(this, "Failed: " + (paymentResult.throwable.message ?: ""), Toast.LENGTH_SHORT).show()
            }
        }
        startActivity(Intent(this@CheckoutActivity, MainActivity::class.java))
    }

    private fun fetchingClientSecret(shoppingCartContent: String) {
        reset().post("$BACKEND_URL/create-payment-intent", null)
            .header(Pair("content-Type","application/json"))
            .body(
                shoppingCartContent,
                UTF_8
            )
            .responseString(object : Handler<String?> {
                override fun success(s: String?) {
                    Log.d("Backend Response", s!!)
                    try {
                        val result = JSONObject(s)
                        /* customerConfig2 = new com.stripe.android.paymentsheet.PaymentSheet.CustomerConfiguration(
                                    result.getString("customer"),
                                    result.getString("ephemeralKey")
                            ); */Log.d("This is merchant Response", result.toString())
                        paymentIntentClientSecret = result.getString("clientSecret")
                        runOnUiThread {
                            paymentSheetPayNow.isEnabled = true
                            widgetPayNow.isEnabled = true
                        }
                        Log.i("", "Retrieved PaymentIntent")
                    } catch (e: JSONException) {
                        runOnUiThread {
                            AlertDialog.Builder(this@CheckoutActivity)
                                .setTitle("Failed to load page")
                                .setMessage("Error: ${e.message}")
                                .setPositiveButton("Ok", null)
                                .show()
                        }
                    }
                }

                override fun failure(e: FuelError) {
                    runOnUiThread {
                        AlertDialog.Builder(this@CheckoutActivity)
                            .setTitle("Failed to load page")
                            .setMessage("Error: ${e.message}")
                            .setPositiveButton("Ok", null)
                            .show()
                    }
                }
            })
    }


    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
        if (fragments.size == 0) {
            super.onBackPressed()
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            supportFragmentManager
                .beginTransaction()
                .remove(supportFragmentManager.fragments[0])
                .commit()
        }
    }


}