package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.roundToInt


typealias OnShareListener = (post: Post) -> Unit
typealias OnLikeListener = (post: Post) -> Unit

class PostsAdapter(
    private val onShareListener: OnShareListener,
    private val onLikeListener: OnLikeListener
) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    private lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        if (!::layoutInflater.isInitialized)
            layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardPostBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding, onShareListener, onLikeListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onShareListener: OnShareListener,
    private val onLikeListener: OnLikeListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likedCount.text = countTransform(post.likes)
            sharedCount.text = countTransform(post.shares)
            viewsCount.text = countTransform(post.views)
            shared.setImageResource(
                if (post.sharedByMe) R.drawable.ic_baseline_share_ok_24 else R.drawable.ic_baseline_share_24
            )
            shared.setOnClickListener {
                onShareListener(post)
            }
            liked.setImageResource(
                if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
            )
            liked.setOnClickListener {
                onLikeListener(post)
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