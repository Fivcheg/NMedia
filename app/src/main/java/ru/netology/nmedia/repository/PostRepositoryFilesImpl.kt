package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post


class PostRepositoryFilesImpl(val context: Context) : PostRepository {
    private val gson = Gson()
    private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var newId: Long = 1
    private var posts = emptyList<Post>()
        set(value) {
            field = value
            sync()
        }
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, typeToken)
                newId = (posts.maxOfOrNull { it.id } ?: 0) + 1
            }
        } else {
            posts = listOf(
                Post(
                    id = autoId(),
                    author = "Нетология. Университет интернет-профессий",
                    published = "21 июня в 23:00",
                    content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
                    likedByMe = false,
                    sharedByMe = false,
                    likes = 10,
                    shares = 20,
                    views = 735,
                    video = "https://www.youtube.com/watch?v=xz-vkzhvKc4"
                ),
                Post(
                    id = autoId(),
                    author = "Нетология. Университет интернет-профессий",
                    published = "21 июня в 23:00",
                    content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
                    likedByMe = false,
                    sharedByMe = false,
                    likes = 55,
                    shares = 777,
                    views = 7665,
                    video = null
                ),
                Post(
                    id = autoId(),
                    author = "Нетология. Университет интернет-профессий",
                    published = "21 июня в 23:00",
                    content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
                    likedByMe = false,
                    sharedByMe = false,
                    likes = 1220,
                    shares = 34220,
                    views = 73425,
                    video = "https://www.youtube.com/watch?v=xz-vkzhvKc4"
                ),
                Post(
                    id = autoId(),
                    author = "Нетология. Университет интернет-профессий",
                    published = "21 июня в 23:00",
                    content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
                    likedByMe = false,
                    sharedByMe = false,
                    likes = 1220,
                    shares = 34220,
                    views = 73425,
                    video = null
                ),
                Post(
                    id = autoId(),
                    author = "Нетология. Университет интернет-профессий",
                    published = "21 июня в 23:00",
                    content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
                    likedByMe = false,
                    sharedByMe = false,
                    likes = 1220,
                    shares = 34220,
                    views = 73425,
                    video = null
                ),
                Post(
                    id = autoId(),
                    author = "Нетология. Университет интернет-профессий",
                    published = "21 июня в 23:00",
                    content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
                    likedByMe = false,
                    sharedByMe = false,
                    likes = 1220,
                    shares = 34220,
                    views = 73425,
                    video = null
                ),
                Post(
                    id = autoId(),
                    author = "Нетология. Университет интернет-профессий",
                    published = "21 июня в 23:00",
                    content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
                    likedByMe = false,
                    sharedByMe = false,
                    likes = 1220,
                    shares = 34220,
                    views = 73425,
                    video = null
                ),
                Post(
                    id = autoId(),
                    author = "Нетология. Университет интернет-профессий",
                    published = "21 июня в 23:00",
                    content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
                    likedByMe = false,
                    sharedByMe = false,
                    likes = 1220,
                    shares = 34220,
                    views = 73425,
                    video = null
                ),
                Post(
                    id = autoId(),
                    author = "Нетология. Университет интернет-профессий",
                    published = "21 июня в 23:00",
                    content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
                    likedByMe = false,
                    sharedByMe = false,
                    likes = 1220,
                    shares = 34220,
                    views = 73425,
                    video = null
                ),
                Post(
                    id = autoId(),
                    author = "Нетология. Университет интернет-профессий",
                    published = "21 июня в 23:00",
                    content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
                    likedByMe = false,
                    sharedByMe = false,
                    likes = 1220,
                    shares = 34220,
                    views = 73425,
                    video = null
                ),
                Post(
                    id = autoId(),
                    author = "Нетология. Университет интернет-профессий",
                    published = "21 июня в 23:00",
                    content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
                    likedByMe = false,
                    sharedByMe = false,
                    likes = 1220,
                    shares = 34220,
                    views = 73425,
                    video = null
                ),
                Post(
                    id = autoId(),
                    author = "Нетология. Университет интернет-профессий",
                    published = "21 июня в 23:00",
                    content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
                    likedByMe = false,
                    sharedByMe = false,
                    likes = 1220,
                    shares = 34220,
                    views = 73425,
                    video = "https://www.youtube.com/watch?v=xz-vkzhvKc4"
                )
            ).reversed()
        }
        data.value = posts
    }

    private fun autoId(): Long = newId++

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe, likes = if (it.likedByMe) {
                    it.likes - 1
                } else {
                    it.likes + 1
                }
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                sharedByMe = true,
                shares = it.shares + 1
            )
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = autoId(),
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            return
        }
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }
}



