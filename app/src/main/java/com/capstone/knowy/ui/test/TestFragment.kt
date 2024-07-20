package com.capstone.knowy.ui.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.knowy.R
import com.capstone.knowy.data.di.Injection
import com.capstone.knowy.data.response.User
import com.capstone.knowy.data.result.Result
import com.capstone.knowy.databinding.FragmentTestBinding
import com.capstone.knowy.ui.factory.ViewModelFactory
import com.capstone.knowy.ui.test.aptitude.home.AptitudeHomeActivity
import com.capstone.knowy.ui.test.ocean.home.OceanHomeActivity
import com.capstone.knowy.ui.test.result.ResultTestActivity

class TestFragment : Fragment() {
    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TestViewModel by viewModels {
        ViewModelFactory.useViewModelFactory {
            TestViewModel(Injection.provideRepository(requireActivity()))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.getDetail().observe(viewLifecycleOwner) {
            getUsername(it)
        }

        _binding = FragmentTestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnGoToOcean.setOnClickListener {
            val intent = Intent(activity, OceanHomeActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoToAptitude.setOnClickListener {
            val intent = Intent(activity, AptitudeHomeActivity::class.java)
            startActivity(intent)
        }

        binding.btnAnalyze.setOnClickListener {
            viewModel.getUserScore().observe(viewLifecycleOwner) { result ->
                showLoading(result is Result.Loading)
                when (result) {
                    is Result.Success -> {
                        val data = result.data
                        if (data.isNotEmpty()) {
                            if (data.size == 10) {
                                viewModel.predictCareer(data.toString()).observe(viewLifecycleOwner) { predict ->
                                    when (predict) {
                                        is Result.Loading -> showLoading(true)
                                        is Result.Success -> {
                                            showLoading(false)
                                            val intent = Intent(activity, ResultTestActivity::class.java).apply {
                                                putExtra(ResultTestActivity.EXTRA_RESULT, predict.data)
                                            }
                                            startActivity(intent)
                                        }
                                        is Result.Error -> {
                                            showLoading(false)
                                            Log.e("Predict Career Error", predict.error)
                                            Toast.makeText(activity, predict.error, Toast.LENGTH_LONG).show()
                                        }
                                        else -> {}
                                    }
                                }
                            } else {
                                Toast.makeText(requireActivity(), getString(R.string.complete_test), Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(requireActivity(), getString(R.string.complete_test), Toast.LENGTH_LONG).show()
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(activity, getString(R.string.never_test), Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }

        return root
    }

    private fun getUsername(result: Result<User>) {
        showLoading(result is Result.Loading)
        when (result) {
            is Result.Success -> {
                val user = result.data
                binding.tvUsername.text = user.username
                Log.d("Result Data", user.username)
            }

            else -> {}
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}