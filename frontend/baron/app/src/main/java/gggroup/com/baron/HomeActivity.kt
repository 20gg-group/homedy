package gggroup.com.baron

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.view.animation.AccelerateInterpolator
import android.view.ViewAnimationUtils
import android.animation.Animator
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity: AppCompatActivity(){
    private var mGoogleSignInClient: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
        val viewTreeObserver = layout_home.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    enterReveal() //reveal animation of FloatingActionButton in timeline
                    layout_home.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
        logout.setOnClickListener({
            signOut()
            finish()
        })
    }
    private fun init() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken("459954702328-gfmo89cq7j1ig2ijrkn4tu8i30hjl5u8.apps.googleusercontent.com")
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
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
        val x = (reveal_position_normal.x + reveal_position_normal.measuredWidth / 2).toInt()
        val y = (reveal_position_normal.y + reveal_position_normal.measuredHeight / 2).toInt()
        val circularReveal = ViewAnimationUtils.createCircularReveal(layout_home, x, y, 0f, finalRadius)
        circularReveal.duration = 300
        circularReveal.interpolator = AccelerateInterpolator()
        layout_home.visibility = View.VISIBLE
        circularReveal.start()
    }

    override fun onBackPressed() {
        exitReveal()
    }

    private fun exitReveal() {
        val x = (reveal_position_normal.x + reveal_position_normal.measuredWidth / 2).toInt()
        val y = (reveal_position_normal.y + reveal_position_normal.measuredHeight / 2).toInt()
        val startRadius = Math.max(layout_home.width, layout_home.height)
        val circularReveal = ViewAnimationUtils.createCircularReveal(layout_home, x, y, startRadius.toFloat(), 0f)//fab.getMeasuredWidth() / 2);
        circularReveal.duration = 300
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
}