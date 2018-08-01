package gggroup.com.baron.authentication.signup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import gggroup.com.baron.R
import gggroup.com.baron.entities.AuthResponse
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity(), SignUpContract.View {

    private var presenter: SignUpContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        presenter = SignUpPresenter(this)

        setClick()
    }
    override fun setClick(){
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
                else -> {
                    btn_sign_up.startAnimation()
                    presenter?.postUser(edt_username.text.toString(),
                            edt_phone.text.toString(),
                            edt_email.text.toString(),
                            edt_password.text.toString())
                }
            }
        }
    }
    override fun onResponse(response: retrofit2.Response<AuthResponse>?) {
        result(btn_sign_up,
                ContextCompat.getColor(this@SignUpActivity, R.color.green),
                BitmapFactory.decodeResource(resources, R.drawable.ic_done),"Đăng ký thành công")
    }
    override fun onFailure(t: Throwable?) {
        result(btn_sign_up,
                ContextCompat.getColor(this@SignUpActivity, R.color.colorAccent),
                BitmapFactory.decodeResource(resources, R.drawable.ic_error),"Đăng ký thất bại. Lỗi: ${t?.message.toString()}")
    }

    override fun showNotification(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: SignUpContract.Presenter) {
        this.presenter = presenter
    }
    private fun result(circularProgressButton: CircularProgressButton,
                       fillColor: Int,bitmap: Bitmap,messenger: String){
        val doneAnimationRunnable = {
            circularProgressButton.doneLoadingAnimation(
                    fillColor,
                    bitmap)
        }
        //done
        with(Handler()) {

            //end animation
            postDelayed(doneAnimationRunnable, 1000)
            postDelayed({showNotification(messenger)},1000)
            if(messenger == "Đăng ký thành công")
            postDelayed({finish()},2000)
            else
            postDelayed({circularProgressButton.revertAnimation()},2000)
        }
    }
}