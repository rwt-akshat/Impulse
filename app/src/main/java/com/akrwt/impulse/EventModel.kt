package com.akrwt.impulse


class EventModel(mName:String,
                mFees:String,
                 mFileUrl:String) {

    constructor():this("","","")

    private var name=mName
    private var fileUrl=mFileUrl
    private var fees=mFees


    fun getName():String{
        return name
    }
    fun setName(nName:String){
        name=nName
    }
    fun getUrl():String{
        return fileUrl
    }
    fun setUrl(iUrl:String){
        fileUrl=iUrl
    }
    fun getFees():String{
        return fees
    }
    fun setFees(f:String){
        fees=f
    }


}