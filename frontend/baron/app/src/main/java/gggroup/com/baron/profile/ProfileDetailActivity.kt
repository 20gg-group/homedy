package gggroup.com.baron.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import gggroup.com.baron.R
import gggroup.com.baron.entities.ResultGetUser
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File

class ProfileDetailActivity : AppCompatActivity(),ProfileDetailContract.View {
    private var presenter: ProfileDetailContract.Presenter? = null
    lateinit var  file:File
    override fun setPresenter(presenter: ProfileDetailContract.Presenter) {
        this.presenter=presenter
    }

    override fun onResponse(resultGetUser: ResultGetUser) {
        cat_title.setText(resultGetUser.user!!.full_name)
        subtitle.setText(resultGetUser.user!!.email)
        Glide.with(this).load(resultGetUser.user!!.avatar).into(cat_avatar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        presenter = ProfileDetailPresenter(this)
        (presenter as ProfileDetailPresenter).getUser("cd9f944152c1a8095fa9")
        //(presenter as ProfileDetailPresenter).updateUser("cd9f944152c1a8095fa9","chính","0393939393",file  )
    }
}
