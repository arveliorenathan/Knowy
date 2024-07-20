package com.capstone.knowy.ui.profile.detail

import androidx.lifecycle.ViewModel
import com.capstone.knowy.data.repository.Repository

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    fun logoutUser() = repository.logOut()
    fun getUserDetail() = repository.getUserDetail()
}