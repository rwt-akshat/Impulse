package com.akrwt.impulse.Club

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.akrwt.impulse.R
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


class ClubMembersAdapter(
    var mContext: Context,
    private var mUploads: ArrayList<ClubModel>
) :
    RecyclerView.Adapter<ClubMembersAdapter.MyViewHolder>() {


    var url: String? = null
    var FileName: String? = null


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var memberImage:ImageView?=null
        internal var memberName:TextView?=null
        internal var memberPhoneNumber:TextView?=null


        init {
            this.memberImage = itemView.findViewById(R.id.LImageView)
            this.memberName = itemView.findViewById(R.id.LmemberName)
            this.memberPhoneNumber=itemView.findViewById(R.id.LPhoneNumber)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.club_members_layout, parent, false)
        return MyViewHolder(v)

    }

    override fun getItemCount(): Int {
        return mUploads.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val uploadCurrent = mUploads.get(position)

        url = uploadCurrent.getImage()
        FileName = uploadCurrent.getName()

        holder.memberName!!.text = "Name: $FileName"

        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .into(holder.memberImage)

        holder.memberPhoneNumber!!.text="Contact: ${uploadCurrent.getNumber()}"
    }


}