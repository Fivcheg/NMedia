package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribe()
        setupListener()
    }

    private fun subscribe() {
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likedCount.text = countTransform(post.likes)
                sharedCount.text = countTransform(post.shares)
                viewsCount.text = countTransform(post.views)
                liked.setImageResource(if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
                if (post.sharedByMe) shared.setImageResource(R.drawable.ic_baseline_share_ok_24)
            }
        }
    }

    private fun setupListener() {

        binding.liked.setOnClickListener {
            viewModel.like()
        }

        binding.shared.setOnClickListener {
            viewModel.share()
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







