package com.akrwt.impulse

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    lateinit var mRecyclerView: RecyclerView
    var mAdapter: EventAdapter? = null

    private var mDatabaseRef: DatabaseReference? = null
    var mUploads: ArrayList<EventModel>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_home, container, false)
        activity!!.title="Home"

        FirebaseApp.initializeApp(context!!)

        FirebaseMessaging.getInstance().subscribeToTopic("ImpulseClient")


        if(ContextCompat.checkSelfPermission(context!!,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),9)
        }


        mRecyclerView = view!!.findViewById(R.id.rv_events)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)


        mUploads = ArrayList()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("events")
        mDatabaseRef!!.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                mUploads!!.clear()
                var testArray: ArrayList<EventModel>? = null

                for (postSnapshot in dataSnapshot.children) {
                    val upload = postSnapshot.getValue(EventModel::class.java)
                    mUploads!!.add(upload!!)

                    testArray = mUploads

                }

                if (testArray != null && context!=null) {
                    if (progressBarMissing != null) {
                        textViewNothing.visibility= View.INVISIBLE
                        progressBarMissing.visibility = View.GONE
                        mAdapter = EventAdapter(context!!, mUploads!!)
                        mRecyclerView.adapter = mAdapter
                        mAdapter!!.notifyDataSetChanged()
                    }
                }else if(context != null){
                    if (progressBarMissing != null) {
                        progressBarMissing.visibility = View.GONE

                    }

                }
            }
        })
        return view
    }

}