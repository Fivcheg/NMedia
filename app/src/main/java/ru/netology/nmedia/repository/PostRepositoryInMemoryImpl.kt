package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.R
import ru.netology.nmedia.dto.Post
import kotlin.math.roundToInt


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
            likes = if (post.likedByMe) post.likes++ else post.likes--
        )
        data.value = post
        // TODO
        liked.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
        likedCount.text = countTransform(post.likes)
    }

    override fun share() {
        post = post.copy(sharedByMe = !post.sharedByMe, shares = post.shares++)
        data.value = post
        // TODO
        binding.shared.setImageResource(R.drawable.ic_baseline_share_ok_24)
        sharedCount.text = countTransform(post.shares)
    }

    override fun countTransform(x: Int): String {
        val numberToString = x.toString()
        val lengthNumber = when (numberToString.length) {
            in 1..3 -> numberToString
            in 4..6 -> {
                val y = x.toDouble() / 1000.0
                if (y > 1.1 && y < 10) {
                    (((y * 10.0).roundToInt()) / 10.0).toString() + "K"
                } else {
                    y.toInt().toString() + "K"
                }
            }
            in 7..9 -> {
                val y = x.toDouble() / 1000000.0
                if (y >= 1.1) {
                    (((y * 10.0).roundToInt()) / 10.0).toString() + "M"
                } else {
                    y.toInt().toString() + "M"
                }
            }
            else -> "не определено"
        }
        return lengthNumber
    }
}