package gggroup.com.baron.updateinfo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import gggroup.com.baron.R
import gggroup.com.baron.entities.ResultGetUser
import kotlinx.android.synthetic.main.activity_update_info.*

class UpdateInfoActivity : AppCompatActivity(),UpdateInfoContract.View {
    private var presenter: UpdateInfoContract.Presenter? = null
    override fun setPresenter(presenter: UpdateInfoContract.Presenter) {
        this.presenter=presenter
    }

    override fun onResponse(resultGetUser: ResultGetUser) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_info)
       // initToolbar()
        presenter=UpdateInfoPresenter(this)
        btn_update.setOnClickListener {
            (presenter as UpdateInfoPresenter).updateUser("",edt_name.text.toString(),edt_phone.text.toString())
        }
    }
//    private fun initToolbar() {
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        toolbar.setNavigationOnClickListener {
//            onBackPressed()
//        }
//    }
}
