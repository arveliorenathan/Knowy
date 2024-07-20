package com.capstone.knowy.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.knowy.data.api.ApiService
import com.capstone.knowy.data.api.MachineService
import com.capstone.knowy.data.preference.Preference
import com.capstone.knowy.data.preference.UserModel
import com.capstone.knowy.data.response.AptitudeAnswerResponse
import com.capstone.knowy.data.response.AptitudeQuestionResponse
import com.capstone.knowy.data.response.CommentsItem
import com.capstone.knowy.data.response.Forum
import com.capstone.knowy.data.response.ForumsItem
import com.capstone.knowy.data.response.OceanResponse
import com.capstone.knowy.data.response.ScoresItem
import com.capstone.knowy.data.response.User
import com.capstone.knowy.data.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class Repository private constructor(
    private val apiService: ApiService,
    private val machineService: MachineService,
    private val pref: Preference
) {
    fun registerUser(email: String, username: String, password: String, confirmPassword: String) =
        flow<Result<String>> {
            emit(Result.Loading)
            try {
                val response = apiService.register(email, username, password, confirmPassword)
                emit(Result.Success(response.message.toString()))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))

            }
        }

    fun loginUser(email: String, password: String) = flow<Result<String>> {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            pref.saveAccessToken(response.loginResult.token)
            pref.saveSession(
                user = UserModel(
                    response.loginResult.userId,
                    response.loginResult.token
                )
            )
            Log.d("User Id", response.loginResult.userId)
            emit(Result.Success(response.status))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Login", "${e.message}", e)
        }
    }

    fun editProfile(fullname: String, username: String) = flow<Result<String>> {
        emit(Result.Loading)
        try {
            val token = "Bearer ${pref.getAccessToken()}"
            Log.d("Token", token)
            val response = apiService.editProfile(token, fullname, username)
            emit(Result.Success(response.message.toString()))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Edit Profile", "${e.message}", e)
        }
    }

    fun getUserDetail(): LiveData<Result<User>> = liveData {
        emit(Result.Loading)
        try {
            val token = "Bearer ${pref.getAccessToken()}"
            Log.d("Token", token)
            val response = apiService.getDetail(token)
            emit(Result.Success(response.user))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Get User Detail", "${e.message}", e)
        }
    }

    fun createForumDiscussion(title: String, content: String) = flow<Result<String>> {
        emit(Result.Loading)
        try {
            val token = "Bearer ${pref.getAccessToken()}"
            val response = apiService.addDiscussion(token, title, content)
            emit(Result.Success(response.message.toString()))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Create Discussion", "${e.message}", e)
        }
    }

    fun getForumDiscussion(): LiveData<Result<List<ForumsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val token = "Bearer ${pref.getAccessToken()}"
            val response = apiService.getForumDiscussion(token)
            emit(Result.Success(response.forums))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Get Forum Discussion", "${e.message}", e)
        }
    }

    fun getDetailForumDiscussion(id: String): LiveData<Result<Forum>> = liveData {
        emit(Result.Loading)
        try {
            val token = "Bearer ${pref.getAccessToken()}"
            val response = apiService.getDetailForumDiscussion(id, token)
            emit(Result.Success(response.forum))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Get Detail Forum Discussion", "${e.message}", e)
        }
    }

    fun createComment(id: String, comment: String) = flow<Result<String>> {
        emit(Result.Loading)
        try {
            val token = "Bearer ${pref.getAccessToken()}"
            val response = apiService.addComment(token, id, comment)
            emit(Result.Success(response.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Create Comment", "${e.message}", e)
        }
    }

    fun getComment(id: String): LiveData<Result<List<CommentsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val token = "Bearer ${pref.getAccessToken()}"
            val response = apiService.getComment(id, token)
            emit(Result.Success(response.comments))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Get Comment", "${e.message}", e)
        }
    }

    fun getAptitudeQuestion(name: String): LiveData<Result<AptitudeQuestionResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAptitudeQuestion(name)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Get Aptitude Question", "${e.message}", e)
        }
    }

    fun getAptitudeAnswer(name: String): LiveData<Result<AptitudeAnswerResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAptitudeAnswer(name)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Get Answer", "${e.message}", e)
        }
    }

    fun getOceanQuestion(name: String): LiveData<Result<OceanResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getOceanQuestions(name)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Get Ocean Question", "${e.message}", e)
        }
    }

    fun saveScore(name: String, score: String) = flow<Result<String>> {
        emit(Result.Loading)
        try {
            val token = "Bearer ${pref.getAccessToken()}"
            val response = apiService.saveScore(token, name, score)
            emit(Result.Success(response.message))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Save Score", "${e.message}", e)
        }
    }

    fun getScore(name: String): LiveData<Result<ScoresItem>> = liveData {
        emit(Result.Loading)
        try {
            val token = "Bearer ${pref.getAccessToken()}"
            val response = apiService.showScore(name, token)
            val score =
                response.scores.firstOrNull { it.userId == pref.getSession().first().userId }
            emit(Result.Success(score ?: ScoresItem("0", "", "")))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Get Score", "${e.message}", e)
        }
    }

    fun getUserScore(): LiveData<Result<List<Float>>> = liveData {
        emit(Result.Loading)
        try {
            val token = "Bearer ${pref.getAccessToken()}"
            val response = apiService.scoreShow(pref.getSession().first().userId, token)
            val scores = response.scores.map { it.score.toFloat() }
            emit(Result.Success(scores))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Get User Score", "${e.message}", e)
        }
    }

    fun predictCareer(data: String) = flow<Result<String>> {
        emit(Result.Loading)
        try {
            val response = machineService.predictCareer(data)
            emit(Result.Success(response.predictedCareer))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("Error Predict Career", "${e.message}", e)
        }
    }

    fun getSession(): Flow<UserModel> {
        return pref.getSession()
    }

    fun logOut() = runBlocking {
        pref.saveAccessToken("")
        pref.logout()
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            machineService: MachineService,
            pref: Preference
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, machineService, pref)
            }.also { instance = it }
    }
}