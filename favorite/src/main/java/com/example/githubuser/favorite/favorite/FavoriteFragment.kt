package com.example.githubuser.favorite.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.core.domain.model.User
import com.example.githubuser.core.ui.UserAdapter
import com.example.githubuser.detail.DetailActivity
import com.example.githubuser.favorite.databinding.FragmentFavoriteBinding
import com.example.githubuser.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var favoriteAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.favorite)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

        loadKoinModules(favoriteModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = UserAdapter(arrayListOf()) { user ->
            val intent = Intent(activity, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                it.putExtra(DetailActivity.EXTRA_ID, user.id)
            }

            startActivity(intent)
        }

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoriteAdapter
        }

        favoriteViewModel.favoritesUsers.observe(viewLifecycleOwner) {
            handleFavoritesUsers(it)
        }
    }

    private fun handleFavoritesUsers(it: List<User>?) {
        it.let {
            if (!it.isNullOrEmpty()) {
                favoriteAdapter.setListUser(it)
            } else {
                binding.rvFavorite.visibility = View.GONE
            }
        }
    }

}