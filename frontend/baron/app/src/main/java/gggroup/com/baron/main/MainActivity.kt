package gggroup.com.baron.main

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import gggroup.com.baron.R
import gggroup.com.baron.main.home.HomeFragment
import gggroup.com.baron.main.profile.ProfileFragment
import gggroup.com.baron.main.saved.SavedFragment
import gggroup.com.baron.utils.OnPagerNumberChangeListener
import kotlinx.android.synthetic.main.activity_main.*
import gggroup.com.baron.filter.FilterActivity


class MainActivity: AppCompatActivity(), OnPagerNumberChangeListener {
    private var revealX: Int = 0
    private var revealY: Int = 0
    private var mGoogleSignInClient: GoogleSignInClient? = null

    private lateinit var homeFragment: HomeFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var savedFragment: SavedFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()

        startFragment(homeFragment)

        initGoogle()

        initBottomNavigation()

        revealX = intent.getIntExtra("REVEAL_X", 0)
        revealY = intent.getIntExtra("REVEAL_Y", 0)
        val viewTreeObserver = layout_home.viewTreeObserver
        if (viewTreeObserver.isAlive && revealX != 0 && revealY!=0) {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    enterReveal() //reveal animation of FloatingActionButton in timeline
                    layout_home.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    private fun initBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> startFragment(homeFragment)
                R.id.item_saved -> startFragment(savedFragment)
                R.id.item_profile -> startFragment(profileFragment)
                else -> true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_filter -> {
                startActivity(Intent(this,FilterActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun initFragment() {
        homeFragment = HomeFragment.newInstance()
        profileFragment = ProfileFragment()
        savedFragment = SavedFragment()
    }

    private fun initGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken("459954702328-gfmo89cq7j1ig2ijrkn4tu8i30hjl5u8.apps.googleusercontent.com")
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
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


    private fun enterReveal() {
        val finalRadius = Math.max(layout_home.width, layout_home.height).toFloat()
//        val x = (reveal_position_normal.x + reveal_position_normal.measuredWidth / 2).toInt()
//        val y = (reveal_position_normal.y + reveal_position_normal.measuredHeight / 2).toInt()
        val circularReveal = ViewAnimationUtils.createCircularReveal(layout_home, revealX, revealY, 0f, finalRadius)
        circularReveal.duration = 500
        circularReveal.interpolator = AccelerateInterpolator()
        layout_home.visibility = View.VISIBLE
        circularReveal.start()
    }
//
    override fun onBackPressed() {
        //exitReveal()
        //signOut()
        finish()
    }

//    private fun exitReveal() {
////        val x = (reveal_position_normal.x + reveal_position_normal.measuredWidth / 2).toInt()
////        val y = (reveal_position_normal.y + reveal_position_normal.measuredHeight / 2).toInt()
//        val startRadius = Math.max(layout_home.width, layout_home.height)
//        val circularReveal = ViewAnimationUtils.createCircularReveal(layout_home, revealX, revealY, startRadius.toFloat(), 0f)//fab.getMeasuredWidth() / 2);
//        circularReveal.duration = 500
//        circularReveal.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationStart(animation: Animator) {}
//            override fun onAnimationEnd(animation: Animator) {
//                layout_home.visibility = View.INVISIBLE
//                finish()
//                overridePendingTransition(0, 0)
//            }
//
//            override fun onAnimationCancel(animation: Animator) {}
//            override fun onAnimationRepeat(animation: Animator) {}
//        })
//        circularReveal.start()
//    }

    override fun onPagerNumberChanged() {
        (homeFragment as OnPagerNumberChangeListener).onPagerNumberChanged()
    }
}
