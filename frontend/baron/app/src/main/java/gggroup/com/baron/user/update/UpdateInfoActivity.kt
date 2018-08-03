package gggroup.com.baron.user.update

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import gggroup.com.baron.R
import gggroup.com.baron.user.info.UserInfoActivity
import kotlinx.android.synthetic.main.activity_update_info.*

class UpdateInfoActivity : AppCompatActivity(),UpdateInfoContract.View {
    override fun onResponse(status: String?) {
        if(status=="true")
        {
            Toast.makeText(this,"Cập nhật thành công",Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this,"Cập nhật thất bại",Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, UserInfoActivity::class.java))
    }

    private var presenter: UpdateInfoContract.Presenter? = null
    override fun setPresenter(presenter: UpdateInfoContract.Presenter) {
        this.presenter=presenter
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_info)
        initToolbar()
        presenter=UpdateInfoPresenter(this)
        btn_update.setOnClickListener {
            (presenter as UpdateInfoPresenter).updateUser("1eb8fbe559ca23cec88c",edt_name.text.toString(),edt_phone.text.toString())
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
