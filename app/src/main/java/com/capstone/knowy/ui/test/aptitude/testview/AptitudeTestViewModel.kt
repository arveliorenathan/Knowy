package com.capstone.knowy.ui.test.aptitude.testview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.knowy.data.repository.Repository

class AptitudeTestViewModel(private val repository: Repository) : ViewModel() {
    private var dataSize: Int = 0
    fun getQuestion(name: String) = repository.getAptitudeQuestion(name)
    fun getAnswer(name: String) = repository.getAptitudeAnswer(name)
    fun saveScore(name: String, score: String) = repository.saveScore(name, score).asLiveData()

    fun getDataSize(): Int {
        return dataSize
    }

    fun setDataSize(value: Int) {
        dataSize = value
    }
}