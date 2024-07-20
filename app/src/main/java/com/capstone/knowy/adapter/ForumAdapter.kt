package com.capstone.knowy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.knowy.data.response.ForumsItem
import com.capstone.knowy.databinding.ItemForumDiscussionBinding

class ForumAdapter : ListAdapter<ForumsItem, ForumAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallBack: CallBack

    interface CallBack {
        fun onItemClickListener(data: ForumsItem)
    }

    class MyViewHolder(private val binding: ItemForumDiscussionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(forum: ForumsItem) {
            binding.tvUsername.text = forum.username
            binding.tvHeadTopic.text = forum.forumTitle
            binding.tvDiscussion.text = forum.forumContent
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemForumDiscussionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val forum = getItem(position)
        holder.bind(forum)
        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClickListener(forum)
        }
    }

    fun setOnItemClickCallBack(onItemClickCallBack: CallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ForumsItem>() {
            override fun areItemsTheSame(
                oldItem: ForumsItem,
                newItem: ForumsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ForumsItem,
                newItem: ForumsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}