package gggroup.com.baron.authentication.signin

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.widget.Toast
import gggroup.com.baron.R
import gggroup.com.baron.authentication.signup.SignUpActivity
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import gggroup.com.baron.HomeActivity
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.text.method.PasswordTransformationMethod
import android.view.View
import kotlinx.android.synthetic.main.activity_signin.*
import android.support.constraint.solver.widgets.WidgetContainer.getBounds
import android.view.MotionEvent
import android.view.View.OnTouchListener



class SignInActivity : AppCompatActivity(),SignInContract.View {
    private var presenter: SignInContract.Presenter? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val GOOGLE_SIGN_IN_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        presenter = SignInPresenter(this)
        init()
        btn_sign_in.setOnClickListener ({
            btn_sign_in.startAnimation()
            getAccount()
        })
        tv_sign_up.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        btn_google.setOnClickListener {
            signInWithGoogleSignIn()
        }
        edt_password.setOnTouchListener(OnTouchListener { _, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.x >= edt_password.width - edt_password.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    // your action here
                    if(edt_password.transformationMethod == null) {
                        edt_password.transformationMethod = PasswordTransformationMethod()
                        edt_password.isSelected = false
                    }
                    else{
                        edt_password.transformationMethod = null
                        edt_password.isSelected = true
                    }
                    return@OnTouchListener true
                }
            }
            false
        })

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

    override fun setPresenter(presenter: SignInContract.Presenter) {
        this.presenter = presenter
    }

    override fun init() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("459954702328-gfmo89cq7j1ig2ijrkn4tu8i30hjl5u8.apps.googleusercontent.com")
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun getAccount() {
        val email = edt_email.text.toString()
        val password = edt_password.text.toString()
        presenter?.checkAccount(email,password)
    }

    override fun onResponse() {
        resultLoading(btn_sign_in,
                ContextCompat.getColor(this@SignInActivity, R.color.green),
                BitmapFactory.decodeResource(resources, R.drawable.ic_done),"Success")
    }

    override fun onFailure(t: String) {
        resultLoading(btn_sign_in,
                ContextCompat.getColor(this@SignInActivity, R.color.colorAccent),
                BitmapFactory.decodeResource(resources, R.drawable.ic_error),"Đăng nhập thất bại. Lỗi: $t")
    }

    override fun showNotification(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun signInWithGoogleSignIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            presenter?.handleSignInResult(task)
        }
    }
    override fun resultLoading(circularProgressButton: CircularProgressButton,
                                       fillColor: Int, bitmap: Bitmap,
                                        messenger: String) {
        //background done
        val doneAnimationRunnable = {
            circularProgressButton.doneLoadingAnimation(
                    fillColor,
                    bitmap)
        }
        circularProgressButton.startAnimation()
        //done
        with(Handler()) {
            postDelayed(doneAnimationRunnable, 2000)
            //end animation
            postDelayed({showNotification(messenger)},2000)
            if(messenger == "Success") {
                postDelayed({ enterReveal(btn_sign_in) }, 3000)
                postDelayed({ circularProgressButton.revertAnimation() }, 3500)
            }
            else {
                postDelayed({ circularProgressButton.revertAnimation() }, 3000)
            }
        }
    }
    private fun enterReveal(view: View) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition")
        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
        ActivityCompat.startActivity(this, intent, options.toBundle()
        )
    }
}

