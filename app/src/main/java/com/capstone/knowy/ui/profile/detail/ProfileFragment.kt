package com.capstone.knowy.ui.profile.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.knowy.data.di.Injection
import com.capstone.knowy.data.response.User
import com.capstone.knowy.data.result.Result
import com.capstone.knowy.databinding.FragmentProfileBinding
import com.capstone.knowy.databinding.LogoutDialogBinding
import com.capstone.knowy.ui.factory.ViewModelFactory
import com.capstone.knowy.ui.profile.edit.EditProfileActivity
import com.capstone.knowy.ui.welcome.WelcomeActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory.useViewModelFactory {
            ProfileViewModel(Injection.provideRepository(requireActivity()))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getUserDetail().observe(viewLifecycleOwner){
            getDetail(it)
        }

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }
        return root
    }

    private fun getDetail(result: Result<User>){
        when (result) {
            is Result.Success -> {
                val story = result.data
                binding.tvFullName.text = story.fullname
                binding.tvUsername.text = story.username
                Log.d("Result Data", story.username)
            }
            else -> {}
        }
    }

    private fun logout(){
        val dialogBinding = LogoutDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()

        dialogBinding.apply {
            btnDialogLogout.setOnClickListener {
                viewModel.logoutUser()
                val intent = Intent(activity, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                dialog.dismiss()
            }
            btnDialogCancel.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}