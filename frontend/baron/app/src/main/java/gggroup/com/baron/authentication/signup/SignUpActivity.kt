package gggroup.com.baron.authentication.signup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Toast
import gggroup.com.baron.R
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity(), SignUpContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

//        Rotate animation
//        val rotate = RotateAnimation(0f, 1800f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
//        rotate.duration = 1000
//        rotate.interpolator = LinearInterpolator()

        btn_sign_up.setOnClickListener {
            // Handle username
            when {
                edt_username.text.isEmpty() -> showNotification("Tên người dùng không được bỏ trống")
                edt_phone.text.isEmpty() -> showNotification("Số điện thoại không được bỏ trống")
                edt_email.text.isEmpty() -> showNotification("Địa chỉ email không được bỏ trống")
                edt_password.text.isEmpty() -> showNotification("Mật khẩu không được bỏ trống")
                edt_check_password.text.isEmpty() -> showNotification("Xác nhận mật khẩu không được bỏ trống")
                edt_password.text.length < 6 -> showNotification("Mật khẩu phải dài hơn 6 ký tự")
                edt_password.text.toString() != edt_check_password.text.toString() -> showNotification("Mật khẩu và mật khẩu xác nhận không giống nhau")
                else -> showNotification("Đăng ký thành công")
            }
        }
    }

    override fun showNotification(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}