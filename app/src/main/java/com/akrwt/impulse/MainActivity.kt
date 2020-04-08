package com.akrwt.impulse

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.akrwt.impulse.Club.ClubMembersActivity
import com.akrwt.impulse.fragments.ChatFragment
import com.akrwt.impulse.fragments.DashboardFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var SHARED_PREFS = "sharedPrefs"
    lateinit var sharedRef: SharedPreferences


    private val mOnNavigationItemSelectedListener =

        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.dashboard -> {
                    replaceFragment(DashboardFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.about -> {
                    replaceFragment(AboutFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.chats -> {

                    replaceFragment(ChatFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedRef = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(DashboardFragment())

    }

    override fun onBackPressed() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val selectedItemId = bottomNavigationView.selectedItemId
        if (R.id.dashboard != selectedItemId) {
            setDashboardItem(this@MainActivity)
        } else {
            super.onBackPressed()
        }
    }


    private fun setDashboardItem(activity: Activity) {
        val bottomNavigationView =
            activity.findViewById<View>(R.id.bottomNavigation) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.dashboard
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.feedback_menu -> startActivity(Intent(this, FeedBack::class.java))
            R.id.contact_menu -> startActivity(Intent(this, Contact::class.java))
            R.id.members_contact_menu -> startActivity(
                Intent(
                    this,
                    ClubMembersActivity::class.java
                )
            )
            R.id.sign_out -> {
                val builder = AlertDialog.Builder(this)

                builder.setMessage("Do you really want to log out?")
                    .setTitle("DIT Impulse")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { _: DialogInterface?, _: Int ->

                        Toast.makeText(applicationContext, "Logged Out", Toast.LENGTH_LONG).show()
                        FirebaseAuth.getInstance().signOut()

                        sharedRef.edit().clear().apply()

                        intent = Intent(applicationContext, PhnVerActivity::class.java)
                        startActivity(intent)
                        finishAffinity()

                    }

                    .setNegativeButton("No") { dialog: DialogInterface?, _: Int ->
                        dialog!!.cancel()
                    }

                val alert = builder.create()
                alert.show()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
