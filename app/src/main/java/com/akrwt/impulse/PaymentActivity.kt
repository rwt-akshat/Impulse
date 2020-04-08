package com.akrwt.impulse

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_payment.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class PaymentActivity : AppCompatActivity() {

    private val UPI_PAYMENT = 0
    private var SHARED_PREFS = "sharedPrefs"
    private var database: FirebaseDatabase? = null
    private var event:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        FirebaseApp.initializeApp(this)

        database = FirebaseDatabase.getInstance()

        val a = intent.getStringExtra("fees")
        event=intent.getStringExtra("event")
        amount_et.setText("Amount : " + a)
        amount_et.keyListener = null
        amount_et.isEnabled = false

        upi_id.keyListener = null
        upi_id.isEnabled = false


        send.setOnClickListener {
            val amount = a
            val n = name.text.toString()
            when {
                name.text.isEmpty() -> {
                    name.error = "This field is empty"
                    name.requestFocus()
                }
                sapId.text.isEmpty() -> {
                    sapId.error = "This field is empty"
                    sapId.requestFocus()
                }
                branch.text.isEmpty() -> {
                    branch.error = "This field is empty"
                    branch.requestFocus()
                }
                year.text.isEmpty() -> {
                    year.error = "This field is empty"
                    year.requestFocus()
                }
                else -> {
                    payUsingUpi(amount!!, n)
                }
            }
        }
    }


    private fun payUsingUpi(amount: String,name: String) {

        val uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", "akshatrwt00@okaxis")
            .appendQueryParameter("pn", name)
            .appendQueryParameter("tn", "Registration")
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()


        val upiPayIntent = Intent(Intent.ACTION_VIEW)
        upiPayIntent.data = uri

        // will always show a dialog to user to choose an app
        val chooser = Intent.createChooser(upiPayIntent, "Pay with")

        // check if intent resolves
        if (null != chooser.resolveActivity(packageManager)) {
            startActivityForResult(chooser, UPI_PAYMENT)
        } else {
            Toast.makeText(
                this@PaymentActivity,
                "No UPI app found, please install one to continue",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            UPI_PAYMENT -> if (Activity.RESULT_OK == resultCode || resultCode == 11) {
                if (data != null) {
                    val text = data.getStringExtra("response")
                    Log.d("UPI", "onActivityResult: $text")
                    val dataList = ArrayList<String>()
                    dataList.add(text!!)
                    upiPaymentDataOperation(dataList)
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null")
                    val dataList = ArrayList<String>()
                    dataList.add("nothing")
                    upiPaymentDataOperation(dataList)
                }
            } else {
                Log.d(
                    "UPI",
                    "onActivityResult: " + "Return data is null"
                ) //when user simply back without payment
                val dataList = ArrayList<String>()
                dataList.add("nothing")
                upiPaymentDataOperation(dataList)
            }
        }
    }

    private fun upiPaymentDataOperation(data: ArrayList<String>) {
        if (isConnectionAvailable(this)) {
            var str: String? = data[0]
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str!!)
            var paymentCancel = ""
            if (str == null) str = "discard"
            var status = ""
            var approvalRefNo = ""
            val response = str.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in response.indices) {
                val equalStr =
                    response[i].split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (equalStr.size >= 2) {
                    if (equalStr[0].toLowerCase(Locale.ENGLISH) == "Status".toLowerCase(Locale.ENGLISH)) {
                        status = equalStr[1].toLowerCase(Locale.ENGLISH)
                    } else if (equalStr[0].toLowerCase(Locale.ENGLISH) == "ApprovalRefNo".toLowerCase(
                            Locale.ENGLISH
                        ) || equalStr[0].toLowerCase(Locale.ENGLISH) == "txnRef".toLowerCase(Locale.ENGLISH)
                    ) {
                        approvalRefNo = equalStr[1]
                    }
                } else {
                    paymentCancel = "Payment cancelled by user."
                }
            }

            if (status == "success") {
                //Code to handle successful transaction here.
                Toast.makeText(this, "Successfully Registered.", Toast.LENGTH_SHORT).show()
                uploadDetails()
                startActivity(Intent(this, PaymentSuccessful::class.java))
                Log.d("UPI", "responseStr: $approvalRefNo")
            } else if ("Payment cancelled by user." == paymentCancel) {
                Toast.makeText(this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Transaction failed.Please try again", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(
                this,
                "Internet connection is not available. Please check and try again",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {

        fun isConnectionAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val netInfo = connectivityManager.activeNetworkInfo
                if (netInfo != null && netInfo.isConnected
                    && netInfo.isConnectedOrConnecting
                    && netInfo.isAvailable
                ) {
                    return true
                }
            }
            return false
        }
    }

    private fun uploadDetails() {

        val databaseReference = database!!.getReference("details")
        val sharedRef=getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val m=sharedRef.getString("mobile","default")


        val upload = DetailsModel(
            event!!,
            name.text.toString().trim(),
            sapId.text.toString().trim(),
            branch.text.toString().trim(),
            m!!,
            year.text.toString().trim()

        )

        val uploadId = databaseReference.push().key
        databaseReference.child(uploadId!!).setValue(upload)


    }

}