package com.akrwt.impulse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PaymentSuccessful : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_successfull)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}
