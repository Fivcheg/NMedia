package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    private val binding = ActivityMainBinding.inflate(layoutInflater)
    private lateinit var viewModel: PostViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        subscribe()
        setupListener()
    }

    private fun subscribe() {
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likedCount.text = viewModel.countTransform(post.likes)
                sharedCount.text = viewModel.countTransform(post.shares)
                viewsCount.text = viewModel.countTransform(post.views)
                if (post.likedByMe) {
                    liked.setImageResource(R.drawable.ic_baseline_favorite_24)
                }
                if (post.sharedByMe) {
                    shared.setImageResource(R.drawable.ic_baseline_share_ok_24)
                }
            }
        }
    }

    fun setupListener() {

        binding.liked.setOnClickListener {
            viewModel.like()
        }

        binding.shared.setOnClickListener {
            viewModel.share()
        }

    }
}







