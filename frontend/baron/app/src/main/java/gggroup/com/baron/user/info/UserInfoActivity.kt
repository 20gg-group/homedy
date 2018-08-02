package gggroup.com.baron.user.info

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import gggroup.com.baron.R
import gggroup.com.baron.entities.ResultGetUser
import gggroup.com.baron.user.update.UpdateInfoActivity
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity(),UserInfoContract.View {
    private var presenter: UserInfoContract.Presenter? = null
    override fun setPresenter(presenter: UserInfoContract.Presenter) {
        this.presenter=presenter
    }

    override fun onResponse(resultGetUser: ResultGetUser) {
        tv_name.text = resultGetUser.user?.full_name
        tv_phone.text = resultGetUser.user?.phone_number
        Glide.with(this).load(resultGetUser.user?.avatar).apply(RequestOptions.circleCropTransform()).into(img_avatar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
       initToolbar()
        presenter=UserInfoPresenter(this)
        (presenter as UserInfoPresenter).getUser("1eb8fbe559ca23cec88c")
        btn_update.setOnClickListener {
            startActivity(Intent(this,UpdateInfoActivity::class.java))
        }
    }
    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
