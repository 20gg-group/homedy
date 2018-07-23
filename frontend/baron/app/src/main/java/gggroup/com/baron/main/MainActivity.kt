package gggroup.com.baron.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import gggroup.com.baron.R
import gggroup.com.baron.main.home.HomeFragment
import gggroup.com.baron.main.profile.ProfileFragment
import gggroup.com.baron.main.saved.SavedFragment
import gggroup.com.baron.main.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFragment(HomeFragment())

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> startFragment(HomeFragment())
                R.id.item_saved -> startFragment(SavedFragment())
                R.id.item_search -> startFragment(SearchFragment())
                R.id.item_profile -> startFragment(ProfileFragment())
                else -> startFragment(HomeFragment())
            }
        }
    }

    private fun startFragment(fragment: Fragment?) : Boolean {
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            return true
        }
        return false
    }
}
