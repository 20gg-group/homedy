package gggroup.com.baron.authentication.login

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import android.widget.Toast
import gggroup.com.baron.R
import gggroup.com.baron.authentication.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import gggroup.com.baron.HomeActivity

class LoginActivity : AppCompatActivity() {
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val GOOGLE_SIGN_IN_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

        btn_login.setOnClickListener {
            animateButtonAndRevert(btn_login,
                    ContextCompat.getColor(this@LoginActivity, R.color.green),
                    BitmapFactory.decodeResource(resources, R.drawable.ic_done_white_48dp))
        }

        tv_sign_up.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        btn_google.setOnClickListener {
            signInWithGoogleSignIn()
        }
    }

    private fun init() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken("459954702328-gfmo89cq7j1ig2ijrkn4tu8i30hjl5u8.apps.googleusercontent.com")
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onStart() {
        super.onStart()
        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            Toast.makeText(this, "Welcome " + account.displayName, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signInWithGoogleSignIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
//            // Signed in successfully, show authenticated UI.
            animateButtonAndRevert(btn_login,
                    ContextCompat.getColor(this@LoginActivity, R.color.green),
                    BitmapFactory.decodeResource(resources, R.drawable.ic_done_white_48dp))
            Log.w("success", "Welcome " + account.displayName)
            //Toast.makeText(this, "Welcome " + account.displayName, Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("failed", "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this, "signInResult:failed code=" + e.statusCode, Toast.LENGTH_SHORT).show()
        }
    }

    private fun animateButtonAndRevert(circularProgressButton: CircularProgressButton,
                                       fillColor: Int,
                                       bitmap: Bitmap) {
        //background done
        val doneAnimationRunnable = {
            circularProgressButton.doneLoadingAnimation(
                    fillColor,
                    bitmap)
        }
        circularProgressButton.startAnimation()
        //done
        with(Handler()) {
            postDelayed(doneAnimationRunnable, 3000)
            //end animation
            postDelayed({ circularProgressButton.revertAnimation() }, 5000)
        }
        val runnable = {
            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
        }
        Handler().postDelayed(runnable, 4000)
    }
}

