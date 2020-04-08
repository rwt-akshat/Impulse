package com.akrwt.impulse.Club


class ClubModel(
    mName: String,
    mNumber: String,
    mImage: String
) {

    constructor() : this("", "", "")

    private var name = mName
    private var number = mNumber
    private var image = mImage


    fun getName(): String {
        return name
    }

    fun setName(n: String) {
        name = n
    }

    fun getNumber(): String {
        return number
    }

    fun setNumber(n: String) {
        number = n
    }

    fun getImage(): String {
        return image
    }

    fun setImage(i: String) {
        image = i
    }

}
