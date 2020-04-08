package com.akrwt.impulse

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.core.app.ShareCompat
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_feed_back.*

class Contact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        btnSend.setOnClickListener {

            when {
                etSub.text.toString().trim() == "" -> {
                    val toast = Toast.makeText(
                        applicationContext,
                        "Subject field is empty",
                        Toast.LENGTH_LONG
                    )
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                etMessage.text.toString().trim().length < 20 -> {
                    val toast = Toast.makeText(
                        applicationContext, "Message field should consists of 20 characters",
                        Toast.LENGTH_LONG
                    )
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                else -> {
                    ShareCompat.IntentBuilder.from(this)
                        .setType("message/rfc822")
                        .addEmailTo("akshatrwt00@gmail.com")
                        .setSubject(etSub.text.toString())
                        .setText("${etMessage.text}")
                        .setChooserTitle("Choose")
                        .startChooser()
                }
            }
        }
    }
}
