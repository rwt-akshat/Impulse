package com.akrwt.impulse

import android.app.DownloadManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.File
import java.lang.Exception
import kotlin.collections.ArrayList


class EventAdapter(
    var mContext: Context,
    private var mUploads: ArrayList<EventModel>
) :
    RecyclerView.Adapter<EventAdapter.MyViewHolder>() {


    var url: String? = null
    var FileName: String? = null


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var textViewName: TextView? = null
        internal var regBtn: Button? = null
        internal var eventImage:ImageView?=null
        internal var progressbar:ProgressBar?=null


        init {
            this.textViewName = itemView.findViewById(R.id.event_txt)
            this.regBtn = itemView.findViewById(R.id.regBtn)
            this.eventImage=itemView.findViewById(R.id.eventImage)
            this.progressbar=itemView.findViewById(R.id.imgLoadingProgress)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.items, parent, false)


        return MyViewHolder(v)

    }

    override fun getItemCount(): Int {
        return mUploads.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val uploadCurrent = mUploads.get(position)

        url = uploadCurrent.getUrl()
        FileName = uploadCurrent.getName()

        holder.textViewName!!.text = FileName

        Picasso.get()
            .load(uploadCurrent.getUrl())
            .into(holder.eventImage,object:com.squareup.picasso.Callback{
                override fun onSuccess() {
                    holder.progressbar!!.visibility=View.GONE
                }

                override fun onError(e: Exception?) {
                    Toast.makeText(mContext,e!!.message,Toast.LENGTH_LONG).show()
                }

            })


        holder.regBtn!!.setOnClickListener {

            val f=uploadCurrent.getFees()
            val fName=uploadCurrent.getName()
            val intent=Intent(mContext,PaymentActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("event",fName)
            intent.putExtra("fees",f)

            mContext.startActivity(intent)

        }
    }


}