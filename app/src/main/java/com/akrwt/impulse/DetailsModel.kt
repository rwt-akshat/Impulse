package com.akrwt.impulse


class DetailsModel(
    mEvent:String,
    mName: String,
    mSapId: String,
    mBranch: String,
    mPhone:String,
    mYear: String
) {

    constructor() : this("","", "", "", "","")

    private var event=mEvent
    private var name = mName
    private var sapId = mSapId
    private var branch = mBranch
    private var year = mYear
    private var phone=mPhone


    fun getEvent():String{
        return event
    }
    fun setEvent(r:String){
        event=r
    }

    fun getName(): String {
        return name
    }

    fun setName(n: String) {
        name = n
    }

    fun getSapId(): String {
        return sapId
    }

    fun setSapId(s: String) {
        sapId = s
    }

    fun getBranch(): String {
        return branch
    }

    fun setBranch(b: String) {
        branch = b
    }

    fun getYear(): String {
        return year
    }

    fun setYear(y: String) {
        year = y
    }
    fun getPhone(): String {
        return phone
    }

    fun setPhone(p: String) {
        phone = p
    }

}
