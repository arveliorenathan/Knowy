package com.capstone.knowy.ui.forum.discussion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.knowy.adapter.ForumAdapter
import com.capstone.knowy.data.di.Injection
import com.capstone.knowy.data.response.ForumsItem
import com.capstone.knowy.data.result.Result
import com.capstone.knowy.databinding.FragmentForumDiscussionBinding
import com.capstone.knowy.ui.factory.ViewModelFactory
import com.capstone.knowy.ui.forum.detail.DetailDiscussionActivity
import com.capstone.knowy.ui.forum.upload.UploadDiscussionActivity

class ForumDiscussionFragment : Fragment() {
    private var _binding: FragmentForumDiscussionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ForumDiscussionViewModel by viewModels {
        ViewModelFactory.useViewModelFactory {
            ForumDiscussionViewModel(Injection.provideRepository(requireActivity()))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForumDiscussionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvDiscussion.layoutManager = layoutManager

        viewModel.getForumDiscussion().observe(viewLifecycleOwner) {
            attachData(it)
        }

        binding.btnCreate.setOnClickListener {
            val intent = Intent(activity, UploadDiscussionActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    private fun attachData(result: Result<List<ForumsItem>>) {
        showLoading(result is Result.Loading)
        when (result) {
            is Result.Success -> {
                val adapter = ForumAdapter()
                adapter.submitList(result.data)
                adapter.setOnItemClickCallBack(object : ForumAdapter.CallBack {
                    override fun onItemClickListener(data: ForumsItem) {
                        val intent = Intent(requireActivity(), DetailDiscussionActivity::class.java)
                        intent.putExtra(DetailDiscussionActivity.EXTRA_FORUM_ID, data.forumId)
                        startActivity(intent)
                    }
                })
                binding.rvDiscussion.adapter = adapter
            }

            is Result.Error -> {

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