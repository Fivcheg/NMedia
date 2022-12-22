package ru.netology.nmedia.dto

data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val sharedByMe: Boolean,
    val likes: Int = 0,
    val shares: Int = 0,
    val views: Int = 0
)