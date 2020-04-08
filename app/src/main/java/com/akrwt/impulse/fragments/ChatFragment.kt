package com.akrwt.impulse.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akrwt.impulse.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChatFragment : Fragment() {

    lateinit var currentUserId: String
    lateinit var mAuth: FirebaseAuth
    lateinit var GroupNameRef: DatabaseReference
    lateinit var groupMsgKeyRef: DatabaseReference
    lateinit var v: View
    private var sharedPrefsName = "sharedPrefsName"
    lateinit var sharedPName: SharedPreferences
    lateinit var name: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_chat, container, false)

        sharedPName = activity!!.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
        name = sharedPName.getString("name", "Default")!!

        mAuth = FirebaseAuth.getInstance()
        currentUserId = mAuth.currentUser!!.uid
        GroupNameRef = FirebaseDatabase.getInstance().getReference().child("Messages")

        activity!!.title = "Ask Queries"

        v.send_message_btn.setOnClickListener {
           // saveMessageInfoToDatabase()
            v.input_group_message.setText("")
            //v.myScrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }
        return v
    }


     private fun saveMessageInfoToDatabase() {
         val message = input_group_message.text.toString()
         val messageKey = GroupNameRef.push().key
         if (TextUtils.isEmpty(message))
             input_group_message.error = "Please write a message first"
         else {
             val ccalForDate = Calendar.getInstance()
             val currentDate =
                 SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).format(ccalForDate.time)

             val ccalForTime = Calendar.getInstance()
             val currentTime = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(ccalForTime.time)

             val grpMessageKey = HashMap<String, Any>()
             GroupNameRef.updateChildren(grpMessageKey)

             groupMsgKeyRef = GroupNameRef.child(messageKey!!)

             val msgInfoMap = HashMap<String, Any>()
             msgInfoMap.put("name", name)
             msgInfoMap.put("message", message)
             msgInfoMap.put("date", currentDate)
             msgInfoMap.put("time", currentTime)

             groupMsgKeyRef.updateChildren(msgInfoMap)

         }
     }

    override fun onStart() {
        super.onStart()
        GroupNameRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                if (p0.exists())
                    displayMessage(p0)
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                if (p0.exists())
                    displayMessage(p0)

            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        })
    }

    private fun displayMessage(dataSnapshot: DataSnapshot) {

        val iterator = dataSnapshot.children.iterator()
        while (iterator.hasNext()) {
            val chatDate = iterator.next().value.toString()
            val chatMessage = iterator.next().value.toString()
            val chatName = iterator.next().value.toString()
            val chatTime = iterator.next().value.toString()

            if (context != null) {
                if (chatName != name) {
                    v.receiver_chat_text_display.append("$chatName :\n$chatMessage\n--------------\n")
                } else if (chatName == name) {
                    v.sender_chat_text_display.append("$chatName :\n$chatMessage\n-----------------\n")
                }
            }
           val scrollView = v.findViewById<ScrollView>(R.id.myScrollView)
            scrollView.post {
                scrollView.fullScroll(View.FOCUS_DOWN)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        v.sender_chat_text_display.text = ""
        v.receiver_chat_text_display.text = ""
    }
}
