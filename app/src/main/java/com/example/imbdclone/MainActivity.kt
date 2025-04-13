package com.example.imbdclone

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.imbdclone.favorites.FavoriteMoviesFragment
import com.example.imbdclone.popular.LatestMoviesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var latestMoviesFragment: LatestMoviesFragment
    private lateinit var favoriteMoviesFragment: FavoriteMoviesFragment
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        val textViewTitle = findViewById<TextView>(R.id.app_logo)
        latestMoviesFragment = LatestMoviesFragment()
        favoriteMoviesFragment = FavoriteMoviesFragment()
        bottomNavigationView = findViewById(R.id.bottom_nav_bar)

        sharedViewModel.title.observe(this) { newTitle ->
            textViewTitle.text = newTitle
        }

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