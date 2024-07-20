package com.capstone.knowy.ui.test.result

import androidx.lifecycle.ViewModel
import com.capstone.knowy.data.repository.Repository

class ResultViewModel(private val repository: Repository) : ViewModel() {
    fun getUserDetail() = repository.getUserDetail()
}