package com.capstone.knowy.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.knowy.data.repository.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun loginUser(email: String, password: String) =
        repository.loginUser(email, password).asLiveData()
}