package com.capstone.knowy.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ViewModelFactory {
    @Suppress("UNCHECKED_CAST")
    fun useViewModelFactory(create: () -> ViewModel) = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return create.invoke() as T
        }
    }
}
