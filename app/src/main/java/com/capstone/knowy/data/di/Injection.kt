package com.capstone.knowy.data.di

import android.content.Context
import com.capstone.knowy.data.api.ApiConfig
import com.capstone.knowy.data.preference.Preference
import com.capstone.knowy.data.preference.dataStore
import com.capstone.knowy.data.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = Preference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiServiceMobile()
        val machineService = ApiConfig.getApiServiceMachine()
        return Repository.getInstance(apiService, machineService, pref)
    }
}