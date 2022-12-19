package com.example.githubuser.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.core.data.Resource
import com.example.githubuser.core.ui.UserAdapter
import com.example.githubuser.databinding.FragmentHomeBinding
import com.example.githubuser.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var userAdapter: UserAdapter
    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.appbar)
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAdapter = UserAdapter(arrayListOf()) { user ->
            val intent = Intent(activity, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                it.putExtra(DetailActivity.EXTRA_ID, user.id)
            }
            startActivity(intent)
        }

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
        }

        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchViewModel.setSearch(query)
                    binding.searchView.clearFocus()
                    return true
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }

        searchViewModel.users.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
                        showLoading(false)
                        it.data?.let { data -> userAdapter.setListUser(data) }
                        binding.rvUser.visibility = View.VISIBLE
                        resources
                    }
                    is Resource.Loading -> {
                        showLoading(true)
                        binding.rvUser.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                tvMessage.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                view?.findNavController()?.navigate(R.id.action_homeFragment_to_favoriteFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}