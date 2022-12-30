package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { posts ->
            binding.container.removeAllViews()
            posts.map { post ->
                CardPostBinding.inflate(layoutInflater, binding.container, true).apply {
                    author.text = post.author
                    published.text = post.published
                    content.text = post.content
                    likedCount.text = countTransform(post.likes)
                    sharedCount.text = countTransform(post.shares)
                    viewsCount.text = countTransform(post.views)
                    liked.setImageResource(
                        if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                    )
                    liked.setOnClickListener {
                        viewModel.likeById(post.id)
                    }
                    shared.setImageResource(
                        if (post.sharedByMe) R.drawable.ic_baseline_share_ok_24 else R.drawable.ic_baseline_share_24
                    )
                    shared.setOnClickListener {
                        viewModel.shareById(post.id)
                    }
                }.root
            }
        }
    }

    private fun countTransform(x: Int): String {
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











