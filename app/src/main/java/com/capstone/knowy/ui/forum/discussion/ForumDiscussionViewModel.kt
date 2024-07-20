package com.capstone.knowy.ui.forum.discussion

import androidx.lifecycle.ViewModel
import com.capstone.knowy.data.repository.Repository

class ForumDiscussionViewModel(private val repository: Repository) : ViewModel() {
    fun getForumDiscussion() = repository.getForumDiscussion()
}