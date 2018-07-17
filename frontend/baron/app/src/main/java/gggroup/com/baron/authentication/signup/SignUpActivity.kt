package gggroup.com.baron.authentication.signup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import gggroup.com.baron.R
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity(), SignUpContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btn_sign_up.setOnClickListener {
            // Handle username
            if (edt_username.text.isEmpty()) {
                showNotification("Tên người dùng không thể bỏ trống")
            }
        }
    }

    override fun showNotification(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}