package com.example.githubuser.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.follow.Follow
import com.example.githubuser.core.data.Resource
import com.example.githubuser.core.domain.model.User
import com.example.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModel()
    private var isFavorite = false
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)

        supportActionBar?.title = username

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        if (username != null) {
            detailViewModel.getDetailUser(username).observe(this) {
                when (it) {
                    is Resource.Success -> {
                        user = it.data!!
                        binding.apply {
                            Glide.with(this@DetailActivity)
                                .load(user.avatarUrl)
                                .apply(RequestOptions.circleCropTransform())
                                .into(ivDetailAvatar)

                            tvDetailName.text = user.name ?: getString(R.string.name)
                            tvDetailLocation.text = user.location ?: "-"
                            tvDetailCompany.text = user.company ?: "-"
                            tvDetailRepository.text = user.publicRepos.toString()
                            tvDetailFollowers.text = user.followers.toString()
                            tvDetailFollowing.text = user.following.toString()
                        }

                        detailViewModel.checkUser(id)?.observe(this@DetailActivity) { userLocal ->
                            isFavorite = userLocal.isFavorite == true
                            setFavorite(isFavorite)
                        }

                        binding.ivFavorite.visibility = View.VISIBLE
                    }
                    is Resource.Error -> binding.ivFavorite.visibility = View.INVISIBLE
                    is Resource.Loading -> binding.ivFavorite.visibility = View.INVISIBLE
                }
                setFavorite(isFavorite)
                binding.ivFavorite.setOnClickListener {
                    if (!isFavorite) {
                        user.isFavorite = !isFavorite
                        detailViewModel.addFavorite(
                            user.username,
                            user.id,
                            user.avatarUrl,
                            user.isFavorite
                        )
                        Toast.makeText(
                            this@DetailActivity,
                            getString(R.string.toast_add_favorite, user.username),
                            Toast.LENGTH_SHORT
                        ).show()
                        isFavorite = !isFavorite
                    } else {
                        user.isFavorite = !isFavorite
                        detailViewModel.removeFavorite(user)
                        Toast.makeText(
                            this@DetailActivity,
                            getString(R.string.toast_remove_favorite, user.username),
                            Toast.LENGTH_SHORT
                        ).show()
                        isFavorite = !isFavorite
                    }
                    setFavorite(isFavorite)
                }
            }
        }

        val pageAdapter = Follow(this, bundle)
        binding.apply {
            viewPager.adapter = pageAdapter
            TabLayoutMediator(tabs, viewPager) { tabs, position ->
                tabs.text = resources.getString(TABS_TITLE[position])
            }.attach()
        }
    }

    private fun setFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.ivFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        private val TABS_TITLE = intArrayOf(R.string.followers, R.string.following)
    }
}