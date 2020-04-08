package com.akrwt.impulse.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrwt.impulse.News.NewsAdapter
import com.akrwt.impulse.News.NewsModel
import com.akrwt.impulse.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {
    lateinit var mRecyclerView: RecyclerView
    var mAdapter: NewsAdapter? = null

    private var mDatabaseRef: DatabaseReference? = null
    var mNews: ArrayList<NewsModel>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity!!.title = "Dashboard"
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        if (ContextCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                9
            )
        }

        mRecyclerView = view.findViewById(R.id.rv_events2)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)


        mNews = ArrayList()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("news")
        mDatabaseRef!!.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                mNews!!.clear()
                var testArray: ArrayList<NewsModel>? = null

                for (postSnapshot in dataSnapshot.children) {
                    val upload = postSnapshot.getValue(NewsModel::class.java)
                    mNews!!.add(upload!!)

                    testArray = mNews

                }

                if (testArray != null && context != null) {
                    mAdapter = NewsAdapter(context!!, mNews!!)
                    mRecyclerView.adapter = mAdapter
                    mAdapter!!.notifyDataSetChanged()
                    noNewsText.visibility = View.GONE
                }
            }
        })
        return view
    }
}