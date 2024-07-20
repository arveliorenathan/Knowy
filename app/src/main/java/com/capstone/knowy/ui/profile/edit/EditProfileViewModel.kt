package com.capstone.knowy.ui.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.knowy.data.repository.Repository

class EditProfileViewModel(private val repository: Repository) : ViewModel() {
    fun editProfileUser(fullname: String, username: String) =
        repository.editProfile(fullname, username).asLiveData()
}