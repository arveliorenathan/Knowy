package com.capstone.knowy.ui.forum.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.knowy.R
import com.capstone.knowy.adapter.CommentAdapter
import com.capstone.knowy.data.di.Injection
import com.capstone.knowy.data.response.CommentsItem
import com.capstone.knowy.data.response.Forum
import com.capstone.knowy.data.result.Result
import com.capstone.knowy.databinding.ActivityDetailDiscussionBinding
import com.capstone.knowy.ui.factory.ViewModelFactory
import com.capstone.knowy.ui.home.MainActivity

class DetailDiscussionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDiscussionBinding

    private val viewModel: DetailDiscussionViewModel by viewModels {
        ViewModelFactory.useViewModelFactory {
            DetailDiscussionViewModel(Injection.provideRepository(this))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val forumId = intent.getStringExtra(EXTRA_FORUM_ID)

        val layoutManager = LinearLayoutManager(this)
        binding.rvComment.layoutManager = layoutManager

        if (forumId != null) {
            viewModel.getDetailForum(forumId).observe(this) {
                attachForumData(it)
            }
        }

        viewModel.getComment(forumId.toString()).observe(this) {
            attachCommentData(it)
        }

        binding.btnSubmit.setOnClickListener {
            createComment(forumId.toString())
            refreshData(forumId.toString())
        }

        binding.imgBack.setOnClickListener {
            val intent = Intent(this@DetailDiscussionActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private fun attachForumData(result: Result<Forum>) {
        showLoading(result is Result.Loading)
        when (result) {
            is Result.Success -> {
                val forum = result.data
                binding.tvName.text = forum.username
                binding.tvHeadTopic.text = forum.forumTitle
                binding.tvDiscussion.text = forum.forumContent
            }

            is Result.Error -> {
                Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    private fun attachCommentData(result: Result<List<CommentsItem>>) {
        showLoading(result is Result.Loading)
        when (result) {
            is Result.Success -> {
                val adapter = CommentAdapter()
                adapter.submitList(result.data)
                binding.rvComment.adapter = adapter

            }

            is Result.Error -> {
                Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    private fun refreshData(forumId: String) {
        viewModel.getDetailForum(forumId).observe(this) { forumResult ->
            attachForumData(forumResult)
            viewModel.getComment(forumId).observe(this) { commentResult ->
                attachCommentData(commentResult)
            }
        }
    }

    private fun createComment(id: String) {
        viewModel.addComment(id, binding.etComment.text.toString()).observe(this) {
            if (it is Result.Loading) {
                showLoading(true)
            } else {
                showLoading(false)
                when (it) {
                    is Result.Success -> {
                        Toast.makeText(this,
                            getString(R.string.add_comment_success), Toast.LENGTH_SHORT)
                            .show()
                        binding.etComment.setText("")
                    }

                    is Result.Error -> {
                        Toast.makeText(
                            this,
                            getString(R.string.add_comment_failed, it.error),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.etComment.setText("")
                    }

                    else -> {}
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion  object {
        const val EXTRA_FORUM_ID = "extra_forum_id"
    }
}