package com.capstone.knowy.ui.forum.upload

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.capstone.knowy.R
import com.capstone.knowy.data.di.Injection
import com.capstone.knowy.data.result.Result
import com.capstone.knowy.databinding.ActivityUploadDiscussionBinding
import com.capstone.knowy.ui.factory.ViewModelFactory
import com.capstone.knowy.ui.forum.discussion.ForumDiscussionFragment

class UploadDiscussionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadDiscussionBinding

    private val viewModel: UploadDiscussionViewModel by viewModels {
        ViewModelFactory.useViewModelFactory {
            UploadDiscussionViewModel(Injection.provideRepository(this))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadDiscussionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnUpload.setOnClickListener {
            createDiscussion()
        }

        playAnimation()
    }

    private fun createDiscussion() {
        val headTopic = binding.etHeadTopic.text.toString()
        val description = binding.etDescription.text.toString()

        if (headTopic.isBlank() || description.isBlank()) {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.createDiscussion(headTopic, description).observe(this) {
            if (it is Result.Loading) {
                showLoading(true)
            } else {
                showLoading(false)
                when (it) {
                    is Result.Success -> {
                        Toast.makeText(
                            this,
                            getString(R.string.success_create_discussion),
                            Toast.LENGTH_SHORT
                        ).show()
                        loadFragment(ForumDiscussionFragment())
                    }

                    is Result.Error -> {
                        Toast.makeText(
                            this,
                            getString(R.string.failed_create_discussion),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.forum_discussion_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.tvTitleUploadDiscussion, View.ALPHA, 1f)
            .setDuration(1000)
        val img =
            ObjectAnimator.ofFloat(binding.ivUploadDisccussion, View.ALPHA, 1f).setDuration(500)
        val headTopic =
            ObjectAnimator.ofFloat(binding.tvHeadTopic, View.ALPHA, 1f).setDuration(500)
        val tilHeadTopic =
            ObjectAnimator.ofFloat(binding.tilHeadTopic, View.ALPHA, 1f).setDuration(500)
        val description =
            ObjectAnimator.ofFloat(binding.tvDescription, View.ALPHA, 1f).setDuration(500)
        val tilDescription =
            ObjectAnimator.ofFloat(binding.tilDescription, View.ALPHA, 1f).setDuration(500)
        val btnUpload = ObjectAnimator.ofFloat(binding.btnUpload, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title, img, headTopic, tilHeadTopic, description, tilDescription, btnUpload
            )
        }.start()
    }


}