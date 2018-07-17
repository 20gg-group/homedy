package gggroup.com.baron.authentication.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import gggroup.com.baron.R
import gggroup.com.baron.authentication.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_sign_up.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
