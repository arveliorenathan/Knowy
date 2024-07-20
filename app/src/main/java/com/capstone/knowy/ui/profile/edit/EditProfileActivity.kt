package com.capstone.knowy.ui.profile.edit

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
import com.capstone.knowy.databinding.ActivityEditProfileBinding
import com.capstone.knowy.ui.factory.ViewModelFactory
import com.capstone.knowy.ui.profile.detail.ProfileFragment

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    private val viewModel: EditProfileViewModel by viewModels {
        ViewModelFactory.useViewModelFactory {
            EditProfileViewModel(Injection.provideRepository(this))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSaveProfile.setOnClickListener {
            editProfile()
        }

        playAnimation()
    }

    private fun editProfile() {
        val fullName = binding.etFullName.text.toString()
        val username = binding.etUsername.text.toString()

        if (fullName.isBlank() || username.isBlank()) {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.editProfileUser(fullName, username).observe(this) {
            if (it is Result.Loading) {
                showLoading(true)
            } else {
                showLoading(false)
                when (it) {
                    is Result.Success -> {
                        Toast.makeText(this, getString(R.string.profile_updated), Toast.LENGTH_SHORT).show()
                        loadFragment(ProfileFragment())
                    }

                    is Result.Error -> {
                        Toast.makeText(this, "Edit Failed : ${it.error})", Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.forum_discussion_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun playAnimation() {
        val img = ObjectAnimator.ofFloat(binding.civAvatar, View.ALPHA, 1f).setDuration(350)
        val fullName = ObjectAnimator.ofFloat(binding.tvFullName, View.ALPHA, 1f).setDuration(350)
        val tilFulName =
            ObjectAnimator.ofFloat(binding.tilFullName, View.ALPHA, 1f).setDuration(350)
        val username = ObjectAnimator.ofFloat(binding.tvUsername, View.ALPHA, 1f).setDuration(350)
        val tilUsername =
            ObjectAnimator.ofFloat(binding.tilUsername, View.ALPHA, 1f).setDuration(350)
        val btnSave =
            ObjectAnimator.ofFloat(binding.btnSaveProfile, View.ALPHA, 1f).setDuration(350)

        AnimatorSet().apply {
            playSequentially(img, fullName, tilFulName, username, tilUsername, btnSave)
        }.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}