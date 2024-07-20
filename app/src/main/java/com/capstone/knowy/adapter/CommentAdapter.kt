package com.capstone.knowy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.knowy.data.response.CommentsItem
import com.capstone.knowy.databinding.ItemCommentBinding

class CommentAdapter : ListAdapter<CommentsItem, CommentAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentsItem) {
            binding.tvComment.text = comment.commentContent
            binding.tvUsername.text = comment.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommentsItem>() {
            override fun areItemsTheSame(
                oldItem: CommentsItem,
                newItem: CommentsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CommentsItem,
                newItem: CommentsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}