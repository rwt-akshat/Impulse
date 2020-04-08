package com.akrwt.impulse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.core.app.ShareCompat
import kotlinx.android.synthetic.main.activity_feed_back.*

class FeedBack : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)

        buttonSend.setOnClickListener {

            when {
                editTextBody.text.toString().trim() == "" -> {
                    val toast = Toast.makeText(applicationContext,"Feedback field is blank",Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER,0,0)
                    toast.show()
                }
                editTextBody.text.toString().trim().length < 20 -> {
                    val toast = Toast.makeText(applicationContext,"Feedback field contains less than 20 characters",Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER,0,0)
                    toast.show()
                }
                else -> {
                    ShareCompat.IntentBuilder.from(this)
                        .setType("message/rfc822")
                        .addEmailTo("akshatrwt00@gmail.com")
                        .setSubject("FeedBack")
                        .setText("${editTextBody.text}")
                        .setChooserTitle("Choose")
                        .startChooser()
                }
            }
        }
    }
}
