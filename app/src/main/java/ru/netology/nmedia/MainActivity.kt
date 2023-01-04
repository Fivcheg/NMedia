package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.content) {
                requestFocus()
                setText(post.content)
            }
        }

        binding.content.setOnClickListener {
            it.focusAndShowKeys()

        }

        binding.cancel.setOnClickListener {
            with(binding.content) {
                viewModel.cancel()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Поле не может быть пустым!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text.toString())
                viewModel.save()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
    }

    private fun View.focusAndShowKeys() {
        fun View.showTheKeysNow() {
            if (isFocused) {
                with(binding) {
                    //Как повесить VISIBLE и INVISIBLE на group_btn, а не отдельные кнопки??? Не работает!
                    save.visibility = VISIBLE
                    cancel.visibility = VISIBLE
                }
            }
        }
        requestFocus()
        if (hasWindowFocus()) {
            showTheKeysNow()
        } else {
            with(binding) {
                save.visibility = INVISIBLE
                cancel.visibility = INVISIBLE
            }
        }
    }
}















