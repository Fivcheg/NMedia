package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post


class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий",
        published = "21 июня в 23:00",
        content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
        likedByMe = false,
        sharedByMe = false,
        likes = 10,
        shares = 20,
        views = 735
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = if (post.likedByMe) post.likes + 1 else post.likes - 1
        )
        data.value = post
    }

    override fun share() {
        post = post.copy(sharedByMe = !post.sharedByMe, shares = post.shares + 1)
        data.value = post
    }
}