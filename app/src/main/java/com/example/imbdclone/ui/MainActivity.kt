package com.example.imbdclone.ui

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.imbdclone.R
import com.example.imbdclone.ui.favorites.FavoriteMoviesFragment
import com.example.imbdclone.ui.latest.LatestMoviesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val latestFragment = LatestMoviesFragment()
    private val favoritesFragment = FavoriteMoviesFragment()
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        theme.applyStyle(R.style.OptOutEdgeToEdgeEnforcement, false)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottom_nav_bar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment, latestFragment, "LATEST")
                .add(R.id.main_fragment, favoritesFragment, "FAVORITES")
                .hide(favoritesFragment)
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.popular_tab -> {
                    switchFragments(latestFragment, favoritesFragment)
                    true
                }

                R.id.favorites_tab -> {
                    switchFragments(favoritesFragment, latestFragment)
                    true
                }

                else -> true
            }
        }

        onBackPressedDispatcher.addCallback {
            val currentFragment = supportFragmentManager.fragments.find { it.isVisible }

            when (currentFragment) {
                favoritesFragment -> {
                    switchFragments(latestFragment, favoritesFragment)
                }

                latestFragment -> {
                    finish()
                }
            }
        }
    }

    private fun switchFragments(showFragment: Fragment, hideFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .show(showFragment)
            .hide(hideFragment)
            .commit()
    }
}