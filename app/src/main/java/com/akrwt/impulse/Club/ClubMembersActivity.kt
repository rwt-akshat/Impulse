package com.akrwt.impulse.Club

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrwt.impulse.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_club_members.*

class ClubMembersActivity : AppCompatActivity() {

    lateinit var mRecyclerHeadsView: RecyclerView
    lateinit var mRecyclerMembersView: RecyclerView
    var mAdapter: ClubHeadsAdapter? = null
    var mMembersAdapter: ClubMembersAdapter? = null

    private var mDatabaseRef: DatabaseReference? = null
    var mClubHeads: ArrayList<ClubHeadModel>? = null
    var mClubMembers: ArrayList<ClubModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_members)
        myScrollView1.isSmoothScrollingEnabled=true
        clubViewHeads()

        clubViewMembers()


    }


    private fun clubViewHeads() {

        mRecyclerHeadsView = findViewById(R.id.headsList)
        mRecyclerHeadsView.isNestedScrollingEnabled =false
        mRecyclerHeadsView.setHasFixedSize(true)
        mRecyclerHeadsView.layoutManager = GridLayoutManager(this, 2)

        mClubHeads = ArrayList()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ClubHeads")


        mDatabaseRef!!.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                mClubHeads!!.clear()
                var testArray: ArrayList<ClubHeadModel>? = null

                for (postSnapshot in dataSnapshot.children) {
                    val sample = postSnapshot.getValue(ClubHeadModel::class.java)
                    mClubHeads!!.add(sample!!)

                    testArray = mClubHeads

                }

                if (testArray != null) {
                    if (clubprogressBar != null) {
                        clubprogressBar .visibility = View.GONE
                        mAdapter = ClubHeadsAdapter(
                            applicationContext,
                            mClubHeads!!
                        )
                        mRecyclerHeadsView.adapter = mAdapter
                        mAdapter!!.notifyDataSetChanged()
                    }
                } else {
                    if (clubprogressBar  != null) {
                        clubprogressBar .visibility = View.GONE
                    }

                }
            }
        })
    }

    private fun clubViewMembers() {
        mRecyclerMembersView = findViewById(R.id.membersList)
        mRecyclerMembersView.isNestedScrollingEnabled=false
        mRecyclerMembersView.setHasFixedSize(true)
        mRecyclerMembersView.layoutManager = GridLayoutManager(this, 2)

        mClubMembers = ArrayList()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ClubMembers")

        mDatabaseRef!!.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                mClubMembers!!.clear()
                var testArray: ArrayList<ClubModel>? = null

                for (postSnapshot in dataSnapshot.children) {
                    val sample = postSnapshot.getValue(ClubModel::class.java)
                    mClubMembers!!.add(sample!!)

                    testArray = mClubMembers

                }

                if (testArray != null) {
                    if (clubprogressBar  != null) {
                        clubprogressBar .visibility = View.GONE
                        mMembersAdapter =
                            ClubMembersAdapter(
                                applicationContext,
                                mClubMembers!!
                            )
                        mRecyclerMembersView.adapter = mMembersAdapter
                        mMembersAdapter!!.notifyDataSetChanged()
                    }
                } else {
                    if (clubprogressBar  != null) {
                        clubprogressBar .visibility = View.GONE
                    }

                }
            }
        })

    }


}
