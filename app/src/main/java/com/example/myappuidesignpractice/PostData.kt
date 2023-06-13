package com.example.myappuidesignpractice

data class PostData(
    var accountID : String,
    var postID : Int, //position
    var postTitle : String,
    var postContent : String,
    var postTargetDate : String,
    var postCategory : String
) : java.io.Serializable {

}
