package com.capstone.knowy.ui.forum.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.knowy.data.repository.Repository

class DetailDiscussionViewModel(private val repository: Repository) : ViewModel() {
    fun getDetailForum(id: String) = repository.getDetailForumDiscussion(id)
    fun addComment(id: String, comment: String) = repository.createComment(id, comment).asLiveData()
    fun getComment(id: String) = repository.getComment(id)
}