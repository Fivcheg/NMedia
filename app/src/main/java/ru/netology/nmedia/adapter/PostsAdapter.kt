package ru.netology.nmedia.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.roundToInt

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onPlay(post: Post) {}
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    private lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        if (!::layoutInflater.isInitialized)
            layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardPostBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            groupCover.visibility =
                if (post.video != null) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

            author.text = post.author
            published.text = post.published
            content.text = post.content
            liked.text = countTransform(post.likes)
            shared.text = countTransform(post.shares)
            viewsCount.text = countTransform(post.views)
            shared.setOnClickListener {
                onInteractionListener.onShare(post)
            }

            play.setOnClickListener {
                onInteractionListener.onPlay(post)
            }

            cover.setOnClickListener {
                onInteractionListener.onPlay(post)
            }

            content.setOnClickListener {
                findNavController(it).navigate(
                    R.id.action_feedFragment_to_postFragment,
                    Bundle().apply { textArg = post.id.toString() })
            }

            liked.isChecked = post.likedByMe
            liked.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
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

