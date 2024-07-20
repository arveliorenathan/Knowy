package com.capstone.knowy.ui.forum.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.knowy.data.repository.Repository

class UploadDiscussionViewModel(private val repository: Repository) : ViewModel() {
    fun createDiscussion(title: String, content: String) =
        repository.createForumDiscussion(title, content).asLiveData()
}