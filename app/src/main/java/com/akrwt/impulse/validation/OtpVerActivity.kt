package com.akrwt.impulse.validation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akrwt.impulse.MainActivity
import com.akrwt.impulse.R
import com.google.firebase.FirebaseException
import java.util.concurrent.TimeUnit
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.otp_activity.*


class OtpVerActivity : AppCompatActivity() {

    private var mVerificationId: String? = null
    private var mobile: String? = null

    private var mAuth: FirebaseAuth? = null

    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

            val code: String? = phoneAuthCredential.smsCode

            if (code != null) {
                editTextCode.setText(code)
                buttonSignIn.visibility = View.GONE
                verifyVerificationCode(code)
            } else {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@OtpVerActivity, e.message, Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(
            s: String,
            forceResendingToken: PhoneAuthProvider.ForceResendingToken
        ) {
            mVerificationId = s
            progressbar.visibility = View.GONE
            textView.text = "Code Sent .."
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_activity)

        mAuth = FirebaseAuth.getInstance()

        if (intent.hasExtra("mobile")) {
            mobile = intent.getStringExtra("mobile")
            sendVerificationCode(mobile)
        }


        buttonSignIn.setOnClickListener {
            val code = editTextCode.text.toString()
            if (code.isEmpty() || code.length < 6) {
                Toast.makeText(applicationContext, "Code is not valid", Toast.LENGTH_LONG).show()
            } else {

                verifyVerificationCode(code)
            }
        }
    }

    private fun sendVerificationCode(mobile: String?) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91" + mobile!!,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallbacks
        )
    }


    private fun verifyVerificationCode(code: String?) {
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code!!)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this@OtpVerActivity) { task ->
                if (task.isSuccessful) {
                    if (intent.hasExtra("mobile")) {
                        val SHARED_PREFS = "sharedPrefs"

                        val sharedRef = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

                        val editor = sharedRef!!.edit()
                        editor.putString("mobile", mobile)
                        editor.apply()

                        val intent = Intent(this@OtpVerActivity, UserDetailsActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                } else {

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(
                            applicationContext,
                            "Invalid Code Entered ...",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
    }
}