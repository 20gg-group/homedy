package gggroup.com.baron.authentication.signin

import android.content.Context
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
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_signin.*
import gggroup.com.baron.main.MainActivity
import gggroup.com.baron.paper.PaperOnBoardingActivity

class SignInActivity : AppCompatActivity(),SignInContract.View {
    private var presenter: SignInContract.Presenter? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val GOOGLE_SIGN_IN_REQUEST_CODE = 1

    private lateinit var mInterstitialAd : InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        //AdMob
        MobileAds.initialize(this, getString(R.string.AdMob_id))
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.AdMob_interstitial_unit_id)
                //"ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        // If user uses app in the first time, start introduction activity

        val sharedPreferences = getSharedPreferences("_2life", Context.MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)

        if (isFirstTime) {
            sharedPreferences.edit().putBoolean("isFirstTime", false).apply()
            startActivity(Intent(this, PaperOnBoardingActivity::class.java))
        }

        presenter = SignInPresenter(this)
        init()
        btn_sign_in.setOnClickListener {
            btn_sign_in.startAnimation()
            getAccount()
        }

        tv_sign_up.setOnClickListener {
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition")
//            ActivityCompat.startActivity(this,Intent(this, SignUpActivity::class.java),options.toBundle())
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        btn_google.setOnClickListener {
            signInWithGoogleSignIn()
        }

        login_temp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

       // BlurImage.with(this).load(R.drawable.background_login).intensity(5F).Async(true).into(background)
    }

    override fun onStart() {
        super.onStart()
        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val sharedPreferences = getSharedPreferences("_2life", Context.MODE_PRIVATE)
        TOKEN = sharedPreferences.getString("TOKEN_USER", "empty")
        if (TOKEN != "empty") {
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition")
//            ActivityCompat.startActivity(
//                    this,
//                    Intent(this, MainActivity::class.java)
//                    , options.toBundle())
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            Toast.makeText(this, "Welcome" , Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun setPresenter(presenter: SignInContract.Presenter) {
        this.presenter = presenter
    }

    override fun init() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken("459954702328-gfmo89cq7j1ig2ijrkn4tu8i30hjl5u8.apps.googleusercontent.com")
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun getAccount() {
        val email = edt_email.text.toString()
        val password = edt_password.text.toString()
        presenter?.checkAccount(this,email,password)
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
            presenter?.handleSignInResult(this,task)
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
            postDelayed(doneAnimationRunnable, 1000)
            //end animation
            postDelayed({showNotification(messenger)},1000)
            if(messenger == "Success") {
                postDelayed({ enterReveal(btn_sign_in) }, 1500)

                postDelayed({
                    layout_signin.visibility=View.GONE
                    finish()
                }, 2222)
            }
            else {
                postDelayed({ circularProgressButton.revertAnimation() }, 2000)
            }
        }
    }
    private fun enterReveal(view: View) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "transition")
        val originalPos = IntArray(2)
        //get position of item
        view.getLocationInWindow(originalPos)
        val x = originalPos[0] + view.measuredWidth/2
        val y = originalPos[1]
        val intent = Intent(this@SignInActivity, MainActivity::class.java)
        intent.putExtra("REVEAL_X", x)
        intent.putExtra("REVEAL_Y", y)
        ActivityCompat.startActivity(this, intent, options.toBundle())
        mInterstitialAd.show()
    }
    companion object{
        var TOKEN : String? = null
    }
}