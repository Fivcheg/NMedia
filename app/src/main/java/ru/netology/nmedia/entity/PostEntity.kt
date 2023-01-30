package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val sharedByMe: Boolean,
    val likes: Int = 0,
    val shares: Int = 0,
    val views: Int = 0,
    val video: String?
) {
    fun toDto() =
        Post(
            id = id,
            author = author,
            content = content,
            published = published,
            likedByMe = likedByMe,
            sharedByMe = sharedByMe,
            likes = likes,
            shares = shares,
            views = views,
            video = video
        )

    companion object {
        fun fromDto(post: Post) = PostEntity(
            id = post.id,
            author = post.author,
            content = post.content,
            published = post.published,
            likedByMe = post.likedByMe,
            sharedByMe = post.sharedByMe,
            likes = post.likes,
            shares = post.shares,
            views = post.views,
            video = post.video
        )
    }
}