package gggroup.com.baron.authentication.signup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import gggroup.com.baron.R
import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.User
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity(), SignUpContract.View {

    private var presenter: SignUpContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        presenter = SignUpPresenter(this)

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

                else -> presenter?.postUser(edt_username.text.toString(),
                        edt_phone.text.toString(),
                        edt_email.text.toString(),
                        edt_password.text.toString())
            }
        }
    }

    override fun onResponse(response: Response<User>?) {
        showLoading(false)
        showNotification("Đăng ký thành công")
        finish()
    }

    override fun onFailure(t: Throwable?) {
        showLoading(false)
        showNotification("Đăng ký thất bại. Lỗi: ${t.toString()}")
    }

    override fun showNotification(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: SignUpContract.Presenter) {
        this.presenter = presenter
    }

    override fun showLoading(isShow: Boolean) {
        loader.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}