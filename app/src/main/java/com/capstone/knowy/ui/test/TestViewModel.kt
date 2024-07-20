package com.capstone.knowy.ui.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.knowy.data.repository.Repository

class TestViewModel(private val repository: Repository) : ViewModel() {
    fun getDetail() = repository.getUserDetail()
    fun getUserScore() = repository.getUserScore()
    fun predictCareer(data: String) = repository.predictCareer(data).asLiveData()
}