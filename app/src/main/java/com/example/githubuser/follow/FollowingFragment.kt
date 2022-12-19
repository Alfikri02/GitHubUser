package com.example.githubuser.follow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.core.data.Resource
import com.example.githubuser.core.ui.UserAdapter
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val followingViewModel: FollowViewModel by viewModel()
    private lateinit var adapterFollowing: UserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()

        adapterFollowing = UserAdapter(arrayListOf()) { user ->
            val intent = Intent(activity, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                it.putExtra(DetailActivity.EXTRA_ID, user.id)
            }
            startActivity(intent)
        }

        binding.rvFollowing.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterFollowing
        }

        showLoading(true)

        followingViewModel.setFollow(username)

        followingViewModel.userFollowing.observe(viewLifecycleOwner) {
            it.let {
                when (it) {
                    is Resource.Success ->
                        if (!it.data.isNullOrEmpty()) {
                            showLoading(false)
                            adapterFollowing.run { setListUser(it.data) }
                        } else {
                            showLoading(false)
                        }
                    is Resource.Loading -> showLoading(true)
                    is Resource.Error -> showLoading(false)
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}