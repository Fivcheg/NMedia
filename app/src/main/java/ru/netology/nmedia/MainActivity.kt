package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Президент Франции Эмманюэль Макрон предложил подумать о том, что будет после завершения военных действий на Украине, говоря о новой архитектуре безопасности и гарантиях для России. Об этом он сказал в интервью TF1 и LCI по итогам визита в США. «Есть одна вещь, к которой мы должны подготовиться, и это то, что мы обсуждали с президентом [США Джо] Байденом, и это архитектура безопасности, в которой мы хотим жить завтра», — заявил Макрон. https://www.yandex.ru/",
            published = "21 мая  в 18:36",
            likedByMe = false,
            sharedByMe = false,
            likes = 7533,
            shares = 1001,
            views = 1500000
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likedCount.text = countTransform(post.likes)
            sharedCount.text = countTransform(post.shares)
            viewsCount.text = countTransform(post.views)
            if (post.likedByMe) {
                liked.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
            if (post.sharedByMe) {
                shared.setImageResource(R.drawable.ic_baseline_share_ok_24)
            }

            liked.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) post.likes++ else post.likes--
                liked.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
                likedCount.text = countTransform(post.likes)
            }

            shared.setOnClickListener {
                post.sharedByMe = !post.sharedByMe
                post.shares++
                shared.setImageResource(R.drawable.ic_baseline_share_ok_24)
                sharedCount.text = countTransform(post.shares)
            }

        }
    }
}

fun countTransform(x: Int): String {
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
