package gggroup.com.baron.main

import android.animation.Animator
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api
import gggroup.com.baron.R
import gggroup.com.baron.main.home.HomeFragment
import gggroup.com.baron.main.post.PostFragment
import gggroup.com.baron.main.profile.ProfileFragment
import gggroup.com.baron.main.saved.SavedFragment
import gggroup.com.baron.utils.OnPagerNumberChangeListener
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity: AppCompatActivity(), OnPagerNumberChangeListener {
    private var revealX: Int = 0
    private var revealY: Int = 0
    private var mGoogleSignInClient: GoogleSignInClient? = null

    private lateinit var homeFragment: HomeFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var savedFragment: SavedFragment
    private lateinit var postFragment: PostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initFragment()

        startFragment(homeFragment)

        initGoogle()

        revealX = intent.getIntExtra("REVEAL_X", 0)
        revealY = intent.getIntExtra("REVEAL_Y", 0)
        val viewTreeObserver = layout_home.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    enterReveal() //reveal animation of FloatingActionButton in timeline
                    layout_home.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    toolbar.title = "Trang chủ"
                    startFragment(homeFragment)
                }
                R.id.item_saved -> {
                    toolbar.title = "Phòng trọ đã lưu"
                    startFragment(savedFragment)

                }
                R.id.item_profile -> {
                 //   toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material)
                    toolbar.title = "Thông tin cá nhân"
                    startFragment(profileFragment)
                }
//                R.id.item_post -> {
//                    toolbar.title = "Đăng bài"
//                    startFragment(postFragment)
//                }
                else -> true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }
    private fun initFragment() {
        homeFragment = HomeFragment()
        profileFragment = ProfileFragment()
        savedFragment = SavedFragment()
        postFragment = PostFragment()
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

    private fun signOut() {
        mGoogleSignInClient?.signOut()
                ?.addOnCompleteListener(this) {
                    // [START_EXCLUDE]
                    Toast.makeText(this,"logout", Toast.LENGTH_SHORT).show()
                    // [END_EXCLUDE]
                }
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
        exitReveal()
        signOut()
    }

    private fun exitReveal() {
//        val x = (reveal_position_normal.x + reveal_position_normal.measuredWidth / 2).toInt()
//        val y = (reveal_position_normal.y + reveal_position_normal.measuredHeight / 2).toInt()
        val startRadius = Math.max(layout_home.width, layout_home.height)
        val circularReveal = ViewAnimationUtils.createCircularReveal(layout_home, revealX, revealY, startRadius.toFloat(), 0f)//fab.getMeasuredWidth() / 2);
        circularReveal.duration = 500
        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                layout_home.visibility = View.INVISIBLE
                finish()
                overridePendingTransition(0, 0)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        circularReveal.start()
    }

    override fun onPagerNumberChanged() {
        (homeFragment as OnPagerNumberChangeListener).onPagerNumberChanged()
    }
}
