package com.akrwt.impulse.validation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import com.akrwt.impulse.MainActivity
import com.akrwt.impulse.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_details.*
import java.io.ByteArrayOutputStream

class UserDetailsActivity : AppCompatActivity() {

    private val shared_pref = "sharedPrefs"
    private var mobile:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        val sharedPref = getSharedPreferences(shared_pref,Context.MODE_PRIVATE)

        mobile=sharedPref.getString("mobile","default")

        phoneNumber.setText(mobile)
        phoneNumber.isEnabled = false

        image_view_user.setOnClickListener{
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(i, 123)
        }

        submitBtn.setOnClickListener {
            submitDetails()
        }
    }

    private fun submitDetails() {

        if(userName.text.isEmpty()) {
            userName.error = "This field is empty"
            userName.requestFocus()
        }
        else {
            val name = userName.text.toString().trim()
            val sharedPrefsName = "sharedPrefsName"

            val sharedRef = getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
            val editor = sharedRef!!.edit()
            val img = bitmapToString(image_view_user.drawable.toBitmap(200, 200))
            editor.putString("user_image", img)
            editor.putString("name", name)

            editor.apply()

            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun bitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val arr = baos.toByteArray()
        return Base64.encodeToString(arr, Base64.DEFAULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == Activity.RESULT_OK
            && data != null && data.data != null
        ) {
            val mImageUri = data.data!!

            Picasso.get()
                .load(mImageUri)
                .into(image_view_user)
        }
    }
}
