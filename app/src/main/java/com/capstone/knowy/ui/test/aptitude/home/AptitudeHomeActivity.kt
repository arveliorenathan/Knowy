package com.capstone.knowy.ui.test.aptitude.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.knowy.R
import com.capstone.knowy.data.di.Injection
import com.capstone.knowy.data.response.ScoresItem
import com.capstone.knowy.data.result.Result
import com.capstone.knowy.databinding.ActivityAptitudeHomeBinding
import com.capstone.knowy.ui.factory.ViewModelFactory
import com.capstone.knowy.ui.home.MainActivity
import com.capstone.knowy.ui.test.aptitude.testview.AptitudeTestActivity

class AptitudeHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAptitudeHomeBinding
    private val viewModel: AptitudeHomeViewModel by viewModels {
        ViewModelFactory.useViewModelFactory {
            AptitudeHomeViewModel(Injection.provideRepository(this))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAptitudeHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNumerical.setOnClickListener {
            val intent = Intent(this@AptitudeHomeActivity, AptitudeTestActivity::class.java)
            intent.putExtra(AptitudeTestActivity.TEST_NAME, getString(R.string.numerical_aptitude))
            startActivity(intent)
        }
        binding.btnSpatial.setOnClickListener {
            val intent = Intent(this@AptitudeHomeActivity, AptitudeTestActivity::class.java)
            intent.putExtra(AptitudeTestActivity.TEST_NAME, getString(R.string.spatial_aptitude))
            startActivity(intent)
        }
        binding.btnPerceptual.setOnClickListener {
            val intent = Intent(this@AptitudeHomeActivity, AptitudeTestActivity::class.java)
            intent.putExtra(AptitudeTestActivity.TEST_NAME, getString(R.string.perceptual_aptitude))
            startActivity(intent)
        }
        binding.btnAbstrac.setOnClickListener {
            val intent = Intent(this@AptitudeHomeActivity, AptitudeTestActivity::class.java)
            intent.putExtra(AptitudeTestActivity.TEST_NAME, getString(R.string.abstract_reasoning))
            startActivity(intent)
        }
        binding.btnVerbal.setOnClickListener {
            val intent = Intent(this@AptitudeHomeActivity, AptitudeTestActivity::class.java)
            intent.putExtra(AptitudeTestActivity.TEST_NAME, getString(R.string.verbal_reasoning))
            startActivity(intent)
        }
        binding.imgBack.setOnClickListener {
            val intent = Intent(this@AptitudeHomeActivity, MainActivity::class.java)
            startActivity(intent)
        }

        val testNames = listOf(
            getString(R.string.numerical_aptitude),
            getString(R.string.spatial_aptitude),
            getString(R.string.perceptual_aptitude),
            getString(R.string.abstract_reasoning),
            getString(R.string.verbal_reasoning)
        )

        testNames.forEach { testName ->
            viewModel.getScore(testName).observe(this) {
                getScore(it, testName)
            }
        }
    }

    private fun getScore(result: Result<ScoresItem>, name: String) {
        showLoading(result is Result.Loading)
        when (result) {
            is Result.Success -> {
                val data = result.data
                when (name) {
                    getString(R.string.numerical_aptitude) -> {
                        binding.tvNumericalScore.text = data.score
                    }

                    getString(R.string.spatial_aptitude) -> {
                        binding.tvSpatialScore.text = data.score
                    }

                    getString(R.string.perceptual_aptitude) -> {
                        binding.tvPerceptualScore.text = data.score
                    }

                    getString(R.string.abstract_reasoning) -> {
                        binding.tvAbstractReasoningScore.text = data.score
                    }

                    getString(R.string.verbal_reasoning) -> {
                        binding.tvVerbalReasoningScore.text = data.score
                    }
                }
            }

            is Result.Error -> {
                Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}