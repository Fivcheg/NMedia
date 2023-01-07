package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.adapter.focusAndShowKeyboard
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()
    private val interaction = object : OnInteractionListener {
        override fun onEdit(post: Post) {
            viewModel.edit(post)
        }

        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onShare(post: Post) {
            viewModel.shareById(post.id)
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PostsAdapter(interaction)
        binding.list.adapter = adapter
        binding.cancel.visibility = INVISIBLE
        binding.save.visibility = VISIBLE
        viewModel.data.observe(this) { posts ->
            val newPost = adapter.itemCount < posts.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.content) {
                binding.groupBtn.visibility = VISIBLE
                setText(post.content)
            }
        }

        binding.cancel.setOnClickListener {
            with(binding.content) {
                viewModel.cancel()
                setText("")
                clearFocus()
                this.focusAndShowKeyboard()
                binding.groupBtn.visibility = INVISIBLE
                binding.save.visibility = VISIBLE
            }
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.error_empty_content,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text.toString())
                viewModel.save()
                setText("")
                clearFocus()
                binding.groupBtn.visibility = INVISIBLE
                binding.save.visibility = VISIBLE
            }
        }

    }
}















