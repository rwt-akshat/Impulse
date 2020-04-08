package com.akrwt.impulse.fragments

import java.util.*

class ChatMessage {
    var messageText: String? = null
    var messageUser: String? = null
    var messageTime: Long = 0

    constructor(messageText: String?, messageUser: String?) {
        this.messageText = messageText
        this.messageUser = messageUser
        messageTime = Date().time
    }

    constructor() {}

}