package com.capstone.knowy.ui.test.result

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.knowy.data.di.Injection
import com.capstone.knowy.data.response.User
import com.capstone.knowy.data.result.Result
import com.capstone.knowy.databinding.ActivityResultTestBinding
import com.capstone.knowy.ui.factory.ViewModelFactory
import com.capstone.knowy.ui.home.MainActivity

class ResultTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultTestBinding

    private val viewModel: ResultViewModel by viewModels {
        ViewModelFactory.useViewModelFactory {
            ResultViewModel(Injection.provideRepository(this))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getStringExtra(EXTRA_RESULT)

        viewModel.getUserDetail().observe(this) {
            getUserDetail(it)
        }

        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this@ResultTestActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.imgBack.setOnClickListener {
            val intent = Intent(this@ResultTestActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.tvResult.text = result
    }

    private fun getUserDetail(result: Result<User>) {
        showLoading(result is Result.Loading)
        when (result) {
            is Result.Success -> {
                val user = result.data
                binding.tvUsername.text = user.username
                binding.tvFullName.text = user.fullname
            }

            else -> {}
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val EXTRA_RESULT = "extra_result"
    }
}