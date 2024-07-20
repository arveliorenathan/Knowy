package com.capstone.knowy.ui.test.ocean.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.knowy.R
import com.capstone.knowy.data.di.Injection
import com.capstone.knowy.data.response.ScoresItem
import com.capstone.knowy.data.result.Result
import com.capstone.knowy.databinding.ActivityOceanHomeBinding
import com.capstone.knowy.ui.factory.ViewModelFactory
import com.capstone.knowy.ui.home.MainActivity
import com.capstone.knowy.ui.test.ocean.testview.OceanTestActivity

class OceanHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOceanHomeBinding

    private val viewModel: OceanHomeViewModel by viewModels {
        ViewModelFactory.useViewModelFactory {
            OceanHomeViewModel(Injection.provideRepository(this))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOceanHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenness.setOnClickListener {
            val intent = Intent(this@OceanHomeActivity, OceanTestActivity::class.java)
            intent.putExtra(OceanTestActivity.EXTRA_TEST_NAME, getString(R.string.openness))
            startActivity(intent)
        }
        binding.btnConscientiousness.setOnClickListener {
            val intent = Intent(this@OceanHomeActivity, OceanTestActivity::class.java)
            intent.putExtra(
                OceanTestActivity.EXTRA_TEST_NAME,
                getString(R.string.conscientiousness)
            )
            startActivity(intent)
        }
        binding.btnExtraVersion.setOnClickListener {
            val intent = Intent(this@OceanHomeActivity, OceanTestActivity::class.java)
            intent.putExtra(OceanTestActivity.EXTRA_TEST_NAME, getString(R.string.extra_version))
            startActivity(intent)
        }
        binding.btnAgreeableness.setOnClickListener {
            val intent = Intent(this@OceanHomeActivity, OceanTestActivity::class.java)
            intent.putExtra(OceanTestActivity.EXTRA_TEST_NAME, getString(R.string.agreeableness))
            startActivity(intent)
        }
        binding.btnNeuroticism.setOnClickListener {
            val intent = Intent(this@OceanHomeActivity, OceanTestActivity::class.java)
            intent.putExtra(OceanTestActivity.EXTRA_TEST_NAME, getString(R.string.neuroticism))
            startActivity(intent)
        }
        binding.imgBack.setOnClickListener {
            val intent = Intent(this@OceanHomeActivity, MainActivity::class.java)
            startActivity(intent)
        }

        val testNames = listOf(
            getString(R.string.openness),
            getString(R.string.conscientiousness),
            getString(R.string.extra_version),
            getString(R.string.agreeableness),
            getString(R.string.neuroticism)
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
                Log.d("Data Score", data.score)
                when (name) {
                    getString(R.string.openness) -> {
                        binding.tvOpennessScore.text = data.score
                    }

                    getString(R.string.conscientiousness) -> {
                        binding.tvConscientiousnessScore.text = data.score
                    }

                    getString(R.string.extra_version) -> {
                        binding.tvExtraVersionScore.text = data.score
                    }

                    getString(R.string.agreeableness) -> {
                        binding.tvAgreeablenessScore.text = data.score
                    }

                    getString(R.string.neuroticism) -> {
                        binding.tvNeuroticismScore.text = data.score
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