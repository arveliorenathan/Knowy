package com.capstone.knowy.ui.test.aptitude.home

import androidx.lifecycle.ViewModel
import com.capstone.knowy.data.repository.Repository

class AptitudeHomeViewModel(private val repository: Repository) : ViewModel() {
    fun getScore(name: String) = repository.getScore(name)
}