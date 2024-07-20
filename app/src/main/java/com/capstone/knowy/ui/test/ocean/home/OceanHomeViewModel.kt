package com.capstone.knowy.ui.test.ocean.home

import androidx.lifecycle.ViewModel
import com.capstone.knowy.data.repository.Repository

class OceanHomeViewModel(private val repository: Repository) : ViewModel() {
    fun getScore(name: String) = repository.getScore(name)
}