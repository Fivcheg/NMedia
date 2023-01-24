package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel


class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)

        val viewHolder = PostViewHolder(binding.post, object : onInteractionListener {
//            override fun onEdit(post: Post) {
//                viewModel.edit(post)
//            }
//
//            override fun onLike(post: Post) {
//                viewModel.likeById(post.id)
//            }
//
//            override fun onShare(post: Post) {
//                viewModel.shareById(post.id)
//                val intent = Intent().apply {
//                    action = Intent.ACTION_SEND
//                    putExtra(Intent.EXTRA_TEXT, post.content)
//                    type = "text/plain"
//                }
//                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
//                startActivity(shareIntent)
//            }
//
//            override fun onRemove(post: Post) {
//                viewModel.removeById(post.id)
//            }
//
//            override fun onPlay(post: Post) {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
//                val playIntent = Intent.createChooser(intent, getString(R.string.url))
//                startActivity(playIntent)
//            }
        })
        viewModel.data.observe(viewLifecycleOwner){ posts ->
            val post = posts.find { it.id == 1L } ?: run {
                findNavController().navigateUp()
                return@observe
            }
        }
        viewHolder.bind(
            Post(
                id = 1,
                author = "ololo",
                content = "sadasdasdsakjnd sanas" +
                        "asd" +
                        "sad sad" +
                        " sad" +
                        " sad" +
                        " asd" +
                        "d" +
                        " as" +
                        " as" +
                        " " +
                        "as" +
                        " dsa" +
                        " das" +
                        "d " +
                        "a" +
                        " da" +
                        "s " +
                        "dsa" +
                        " a" +
                        " a" +
                        "as  as" +
                        " a",
                published = "Today",
                likedByMe = false,
                sharedByMe = false,
                likes = 10,
                shares = 15,
                views = 555,
                video = null
            )
        )
        return binding.root
    }

    companion object {
        var Bundle.textArg by StringArg
    }
}