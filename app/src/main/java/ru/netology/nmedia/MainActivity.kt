package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.AndroidUtils
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
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
        binding.list?.adapter = adapter
        this.viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        changeInvisibility()
        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.content) {
                this?.requestFocus()
                this?.setText(post.content)
            }
        }

        // Почему дабл клик нужно делать?
        binding.content?.setOnClickListener {
            changeVisibility()
        }
        binding.cancel?.setOnClickListener {
            with(binding.content) {
                viewModel.cancel()
                this?.setText("")
                this?.clearFocus()
                this?.let { it1 -> AndroidUtils.hideKeyboard(it1) }
            }
            changeInvisibility()
        }

        binding.save?.setOnClickListener {
            with(binding.content) {
                if (this?.text?.isNullOrBlank() == true) {
                    Toast.makeText(
                        this@MainActivity,
                        "Поле не может быть пустым!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(this!!.text.toString())
                viewModel.save()
                changeInvisibility()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
    }
    private fun changeVisibility() {
        if (!binding.cancel!!.isShown) {
            binding.save?.visibility = VISIBLE
            binding.cancel?.visibility = VISIBLE
        }
    }
    private fun changeInvisibility() {
            binding.save?.visibility = INVISIBLE
            binding.cancel?.visibility = INVISIBLE
    }
}














