package com.akrwt.impulse


import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akrwt.cr.validation.CheckConnection
import com.akrwt.impulse.validation.OtpVerActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.verify_phone.*


class PhnVerActivity : AppCompatActivity() {

    private var editTextMobile: EditText? = null
    private lateinit var auth: FirebaseAuth
    private var checkConnection = CheckConnection()


    public override fun onStart() {
        super.onStart()

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI()
            finishAffinity()
        }

    }

    private fun updateUI() {
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verify_phone)



        editTextMobile = findViewById(R.id.editTextMobile)

        buttonContinue.setOnClickListener {

            val mobile = editTextMobile!!.text.toString()

            if (checkConnection.checkConnection(this) == 0) {
                Toast.makeText(
                    applicationContext,
                    "Check your internet connectivity..",
                    Toast.LENGTH_LONG
                ).show()
            } else {

                if (mobile.isEmpty() || mobile.length < 10) {
                    editTextMobile!!.error = "Enter a valid mobile number"
                } else {

                    val intent = Intent(applicationContext, OtpVerActivity::class.java)
                    intent.putExtra("mobile", mobile)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}




