package com.capstone.knowy.ui.test.ocean.testview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.knowy.data.repository.Repository

class OceanTestViewModel(private val repository: Repository) : ViewModel() {
    private var dataSize: Int = 0
    private var score: Int = 0

    fun getQuestions(name: String) = repository.getOceanQuestion(name)

    fun saveScore(name: String, score: String) = repository.saveScore(name, score).asLiveData()

    fun getDataSize(): Int {
        return dataSize
    }

    fun setDataSize(value: Int) {
        dataSize = value
    }

    fun getScore(): Int {
        return score
    }

    fun setScore(value: Int) {
        score += value
    }
}