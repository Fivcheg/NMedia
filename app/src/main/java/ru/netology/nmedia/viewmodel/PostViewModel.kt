package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    sharedByMe = false,
    published = "",
    likes = 0,
    shares = 0,
    views = 0
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun edit(post: Post) {
        edited.value = post
    }

    fun save() {
        edited.value.let {
            if (it != null) {
                repository.save(it)
            }
        }
        edited.value = empty
    }

    fun cancel() {
        edited.value = empty
    }

    fun changeContent(content: String) {
        edited.value.let {
            val text = content.trim()
            if (edited.value?.content == text) {
                return
            }
            edited.value = edited.value?.copy(content = text)
        }
    }
}

