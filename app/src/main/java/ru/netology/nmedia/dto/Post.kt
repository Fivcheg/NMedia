package ru.netology.nmedia.dto

data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean,
    var sharedByMe: Boolean,
    var likes: Int = 0,
    var shares: Int = 0,
    var views: Int = 0
)