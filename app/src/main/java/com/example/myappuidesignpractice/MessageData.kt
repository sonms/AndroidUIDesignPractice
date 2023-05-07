package com.example.myappuidesignpractice

data class MessageData(
    var message : String?,
    var sendId : String?
) {
    constructor():this("","")
}
