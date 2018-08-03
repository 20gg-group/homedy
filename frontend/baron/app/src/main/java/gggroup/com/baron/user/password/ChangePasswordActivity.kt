package gggroup.com.baron.user.password

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import gggroup.com.baron.R
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity(),ChangePasswordContract.View {
    override fun showNotification(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    private var presenter: ChangePasswordContract.Presenter? = null
    override fun setPresenter(presenter: ChangePasswordContract.Presenter) {
        this.presenter=presenter
    }

    override fun onResponse(status: String?) {
        if(status=="true"){
            showNotification("Thay đổi mật khẩu thành công")
            finish()
        }
        else showNotification("Thay đổi mật khẩu thất bại")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        presenter=ChangePasswordPresenter(this)
        btn_changepass.setOnClickListener {
          when{
              edt_password.text.length < 6 -> showNotification("Mật khẩu phải dài hơn 6 ký tự")
              edt_newpassword.text.length<6->showNotification("Mật khẩu phải dài hơn 6 ký tự")
              else-> presenter?.changePassword("1eb8fbe559ca23cec88c",edt_password.text.toString(),edt_newpassword.text.toString())
          }
        }
    }
}
