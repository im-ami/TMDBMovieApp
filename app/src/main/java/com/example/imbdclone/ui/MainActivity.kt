package com.example.imbdclone.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.imbdclone.R
import com.example.imbdclone.ui.favorites.FavoriteMoviesFragment
import com.example.imbdclone.ui.latest.LatestMoviesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var latestMoviesFragment: LatestMoviesFragment
    private lateinit var favoriteMoviesFragment: FavoriteMoviesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        theme.applyStyle(R.style.OptOutEdgeToEdgeEnforcement, false)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        latestMoviesFragment = LatestMoviesFragment()
        favoriteMoviesFragment = FavoriteMoviesFragment()
        bottomNavigationView = findViewById(R.id.bottom_nav_bar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, latestMoviesFragment)
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.popular_tab -> {
                    switchFragments(latestMoviesFragment)
                    true
                }

                R.id.favorites_tab -> {
                    switchFragments(favoriteMoviesFragment)
                    true
                }

                else -> true
            }
        }
    }

    private fun switchFragments(fragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_fragment)

        if (currentFragment == fragment) return

        val transaction = supportFragmentManager.beginTransaction()
        if (fragment == latestMoviesFragment) {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            transaction.addToBackStack(null)
        }

        transaction.replace(R.id.main_fragment, fragment)
        transaction.commit()
    }
}