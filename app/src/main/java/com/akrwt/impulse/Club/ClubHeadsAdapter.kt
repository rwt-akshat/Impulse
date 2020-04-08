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


class ClubHeadsAdapter(
    var mContext: Context,
    private var mUploads: ArrayList<ClubHeadModel>
) :
    RecyclerView.Adapter<ClubHeadsAdapter.MyViewHolder>() {


    var url: String? = null
    var FileName: String? = null


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var headImage:ImageView?=null
        internal var headName:TextView?=null
        internal var headPhoneNumber:TextView?=null
        internal var headPosition:TextView?=null


        init {
            this.headImage = itemView.findViewById(R.id.LheadImageView)
            this.headName = itemView.findViewById(R.id.LheadName)
            this.headPhoneNumber=itemView.findViewById(R.id.LheadPhoneNumber)
            this.headPosition=itemView.findViewById(R.id.LheadPosition)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.club_layout, parent, false)
        return MyViewHolder(v)

    }

    override fun getItemCount(): Int {
        return mUploads.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val uploadCurrent = mUploads.get(position)

        url = uploadCurrent.getImage()
        FileName = uploadCurrent.getName()

        holder.headName!!.text = "Name: $FileName"

        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .into(holder.headImage)


        holder.headPhoneNumber!!.text="Contact: ${uploadCurrent.getNumber()}"
        holder.headPosition!!.text= "Position: ${uploadCurrent.getPosition()}"
    }


}