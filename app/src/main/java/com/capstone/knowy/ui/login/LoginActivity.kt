package com.capstone.knowy.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.knowy.R
import com.capstone.knowy.data.di.Injection
import com.capstone.knowy.data.result.Result
import com.capstone.knowy.databinding.ActivityLoginBinding
import com.capstone.knowy.ui.factory.ViewModelFactory
import com.capstone.knowy.ui.home.MainActivity
import com.capstone.knowy.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.useViewModelFactory {
            LoginViewModel(Injection.provideRepository(this))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString().trim()
            val password = binding.etLoginPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                if (email.isEmpty()) {
                    binding.etLoginEmail.error = getString(R.string.error_empty_email)
                }
                if (password.isEmpty()) {
                    binding.etLoginPassword.error = getString(R.string.error_empty_password)
                }
                binding.etLoginEmail.error = getString(R.string.error_empty_email)
                binding.etLoginPassword.error = getString(R.string.error_empty_password)
            } else {
                loginUser()
            }

        }
        playAnimation()
    }

    private fun loginUser() {
        viewModel.loginUser(
            binding.etLoginEmail.text.toString(),
            binding.etLoginPassword.text.toString()
        )
            .observe(this) {
                if (it is Result.Loading) {
                    showLoading(true)
                } else {
                    showLoading(false)
                    when (it) {
                        is Result.Success -> {
                            Toast.makeText(this,
                                getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }

                        is Result.Error -> {
                            Toast.makeText(
                                this,
                                getString(R.string.login_error),
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

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imgLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val welcome = ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f).setDuration(350)
        val login = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(350)
        val email = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(350)
        val emailEdit = ObjectAnimator.ofFloat(binding.tilEmail, View.ALPHA, 1f).setDuration(350)
        val password = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(350)
        val passwordEdit =
            ObjectAnimator.ofFloat(binding.tilPassword, View.ALPHA, 1f).setDuration(350)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(350)
        val notAccount =
            ObjectAnimator.ofFloat(binding.tvNotHaveAccount, View.ALPHA, 1f).setDuration(350)
        val register = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(350)
        AnimatorSet().apply {
            playSequentially(
                welcome,
                login,
                email,
                emailEdit,
                password,
                passwordEdit,
                btnLogin,
                notAccount,
                register
            )
        }.start()
    }
}