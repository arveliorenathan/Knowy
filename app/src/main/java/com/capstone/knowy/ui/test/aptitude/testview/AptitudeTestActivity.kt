package com.capstone.knowy.ui.test.aptitude.testview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.knowy.R
import com.capstone.knowy.data.di.Injection
import com.capstone.knowy.data.result.Result
import com.capstone.knowy.databinding.ActivityAptitudeTestBinding
import com.capstone.knowy.databinding.ScoreAptitudeBinding
import com.capstone.knowy.ui.factory.ViewModelFactory
import com.capstone.knowy.ui.test.aptitude.home.AptitudeHomeActivity

class AptitudeTestActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAptitudeTestBinding

    private val viewModel: AptitudeTestViewModel by viewModels {
        ViewModelFactory.useViewModelFactory {
            AptitudeTestViewModel(Injection.provideRepository(this))
        }
    }

    private var testName: String? = null
    private var selectedAnswer: String = ""
    private var currentIndex: Int = 0
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAptitudeTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        testName = intent.getStringExtra(TEST_NAME)
        binding.topAppBar.title = testName
        binding.apply {
            btnAnswer1.setOnClickListener(this@AptitudeTestActivity)
            btnAnswer2.setOnClickListener(this@AptitudeTestActivity)
            btnAnswer3.setOnClickListener(this@AptitudeTestActivity)
            btnAnswer4.setOnClickListener(this@AptitudeTestActivity)
            btnNext.setOnClickListener(this@AptitudeTestActivity)
        }
        loadQuestion()
    }

    override fun onClick(view: View?) {
        binding.apply {
            btnAnswer1.setBackgroundColor(getColor(R.color.gray))
            btnAnswer2.setBackgroundColor(getColor(R.color.gray))
            btnAnswer3.setBackgroundColor(getColor(R.color.gray))
            btnAnswer4.setBackgroundColor(getColor(R.color.gray))
        }

        val clickedBtn = view as Button
        if (clickedBtn.id == binding.btnNext.id) {
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(this, getString(R.string.select_answer), Toast.LENGTH_SHORT).show()
                return
            }
            viewModel.getAnswer(testName!!).observe(this) {
                when (it) {
                    is Result.Success -> {
                        val answer = it.data
                        if (answer.answers[currentIndex] == selectedAnswer) {
                            score++
                            currentIndex++
                            loadQuestion()
                        } else {
                            currentIndex++
                            loadQuestion()
                        }
                    }

                    is Result.Error -> {
                        Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                    }

                    else -> {

                    }
                }
            }
        } else {
            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.blue))
        }
    }

    private fun loadQuestion() {
        selectedAnswer = ""
        viewModel.getQuestion(testName!!).observe(this) {
            when (it) {
                is Result.Success -> {
                    val question = it.data
                    viewModel.setDataSize(question.imageUrls.size)
                    if (currentIndex == viewModel.getDataSize()) {
                        viewModel.saveScore(testName!!.toString(), score.toFloat().toString()).observe(this) {
                            when (it) {
                                is Result.Success -> {
                                    Toast.makeText(this, getString(R.string.save_success), Toast.LENGTH_SHORT).show()
                                }

                                is Result.Error -> {
                                    Toast.makeText(
                                        this,
                                        "Save Failed : ${it.error})",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                else -> {}
                            }
                        }
                        finishQuiz()
                        return@observe
                    } else {
                        if (question.imageUrls.isNotEmpty()) {
                            Glide.with(this).load(question.imageUrls.getOrNull(currentIndex))
                                .into(binding.imgQuestion)
                        } else {
                            Log.e("Image Test", "Image URLs array is empty!")
                        }
                        binding.progressBar.progress =
                            ((currentIndex.toFloat()) / viewModel.getDataSize()
                                .toFloat() * 100).toInt()
                    }

                }

                is Result.Error -> {
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                }

                else -> {

                }
            }
        }
    }

    private fun finishQuiz() {
        val percentage = ((score.toFloat() / viewModel.getDataSize().toFloat()) * 100).toInt()
        val dialogBinding = ScoreAptitudeBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            tvScoreProgress.text = getString(R.string.percentage, percentage)
            tvAnswer.text = getString(
                R.string.title_score,
                score.toString(),
                viewModel.getDataSize().toString()
            )
            tvScore.text = getString(R.string.aptitude_score, score.toFloat())
            btnFinish.setOnClickListener {
                val intent = Intent(this@AptitudeTestActivity, AptitudeHomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
        AlertDialog.Builder(this).setView(dialogBinding.root).setCancelable(false).show()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            // Disable back navigation if the user is on the first screen
        }

    }

    companion object {
        const val TEST_NAME = "test_name"
    }


}