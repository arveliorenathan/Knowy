package com.capstone.knowy.ui.test.ocean.testview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.knowy.R
import com.capstone.knowy.data.di.Injection
import com.capstone.knowy.data.result.Result
import com.capstone.knowy.databinding.ActivityOceanTestBinding
import com.capstone.knowy.databinding.ScoreOceanBinding
import com.capstone.knowy.ui.factory.ViewModelFactory
import com.capstone.knowy.ui.test.ocean.home.OceanHomeActivity

class OceanTestActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOceanTestBinding

    private val viewModel: OceanTestViewModel by viewModels {
        ViewModelFactory.useViewModelFactory {
            OceanTestViewModel(Injection.provideRepository(this))
        }
    }

    private var testName: String? = null
    private var selectedAnswer: String = ""
    private var currentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOceanTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        testName = intent.getStringExtra(EXTRA_TEST_NAME)
        binding.topAppBar.title = testName

        binding.apply {
            btnAnswer1.setOnClickListener(this@OceanTestActivity)
            btnAnswer2.setOnClickListener(this@OceanTestActivity)
            btnAnswer3.setOnClickListener(this@OceanTestActivity)
            btnAnswer4.setOnClickListener(this@OceanTestActivity)
            btnAnswer5.setOnClickListener(this@OceanTestActivity)
            btnNext.setOnClickListener(this@OceanTestActivity)
        }
        loadQuestion()
    }

    override fun onClick(view: View?) {
        binding.apply {
            btnAnswer1.setBackgroundColor(getColor(R.color.gray))
            btnAnswer2.setBackgroundColor(getColor(R.color.gray))
            btnAnswer3.setBackgroundColor(getColor(R.color.gray))
            btnAnswer4.setBackgroundColor(getColor(R.color.gray))
            btnAnswer5.setBackgroundColor(getColor(R.color.gray))
        }

        val clickedBtn = view as Button

        if (clickedBtn.id == binding.btnNext.id) {
            if (selectedAnswer.isEmpty()) {
                Toast.makeText(this, getString(R.string.select_answer), Toast.LENGTH_SHORT).show()
                return
            }
            checkAnswer()
            Log.d("Score", viewModel.getScore().toString())
            currentIndex++
            loadQuestion()
        } else {
            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.blue))

        }
    }

    private fun loadQuestion() {
        selectedAnswer = ""
        viewModel.getQuestions(testName!!).observe(this) {
            when (it) {
                is Result.Success -> {
                    val question = it.data
                    viewModel.setDataSize(question.questions.size)
                    if (currentIndex == viewModel.getDataSize()) {
                        finishQuiz()
                        return@observe
                    } else {
                        if (question.questions.isNotEmpty()) {
                            binding.tvQuestion.text = question.questions[currentIndex]
                        } else {
                            Log.e("Data Ocean", "Question Array is empty!")
                        }
                        binding.progressBar.progress =
                            ((currentIndex.toFloat()) / viewModel.getDataSize()
                                .toFloat() * 100).toInt()
                    }
                }

                is Result.Error -> {
                    Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
        Log.d("Score Load", viewModel.getScore().toString())
    }

    private fun checkAnswer() {
        when (selectedAnswer) {
            getString(R.string.strongly_agree) -> {
                viewModel.setScore(5)
            }

            getString(R.string.agree) -> {
                viewModel.setScore(4)
            }

            getString(R.string.neutral) -> {
                viewModel.setScore(3)
            }

            getString(R.string.disagree) -> {
                viewModel.setScore(2)
            }

            getString(R.string.strongly_disagree) -> {
                viewModel.setScore(1)
            }
        }
    }

    private fun finishQuiz() {
        val dialogBinding = ScoreOceanBinding.inflate(layoutInflater)
        val score = (viewModel.getScore().toFloat() / 5.0)
        viewModel.saveScore(testName!!, score.toString()).observe(this) {
            when (it) {
                is Result.Success -> {
                    Toast.makeText(this, getString(R.string.save_success), Toast.LENGTH_SHORT).show()
                }

                is Result.Error -> {
                    Toast.makeText(this,
                        getString(R.string.save_failed, it.error), Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
        dialogBinding.apply {
            tvScore.text = getString(R.string.ocean_score, testName, score)
            btnFinish.setOnClickListener {
                val intent = Intent(this@OceanTestActivity, OceanHomeActivity::class.java)
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
        const val EXTRA_TEST_NAME = "extra_text_name"
    }
}