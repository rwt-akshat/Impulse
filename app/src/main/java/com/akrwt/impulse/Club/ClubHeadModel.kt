package com.akrwt.impulse.Club


class ClubHeadModel(
    mName: String,
    mPosition: String,
    mNumber: String,
    mImage: String
) {

    constructor() : this("", "", "", "")

    private var name = mName
    private var number = mNumber
    private var image = mImage
    private var position = mPosition


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

    fun getPosition(): String {
        return position
    }

    fun setPosition(p: String) {
        position = p
    }

}
