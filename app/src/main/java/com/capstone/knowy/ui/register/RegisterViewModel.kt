package com.capstone.knowy.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.knowy.data.repository.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    fun registerUser(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ) = repository.registerUser(email, username, password, confirmPassword).asLiveData()
}