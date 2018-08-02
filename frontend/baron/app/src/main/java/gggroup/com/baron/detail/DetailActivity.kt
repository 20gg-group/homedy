package gggroup.com.baron.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import gggroup.com.baron.R
import gggroup.com.baron.entities.DetailPost
import gggroup.com.baron.utils.HashMapUtils
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailContract.View {

    private var presenter: DetailContract.Presenter? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initToolbar()

        presenter = DetailPresenter(this)

        val postId = intent.getIntExtra("post_id", -1)

        if (postId == -1)
            showNotification("Vui lòng thử lại sau")

        presenter?.getDetailPost(postId)

    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun showNotification(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: DetailContract.Presenter) {
        this.presenter = presenter
    }

    @SuppressLint("SetTextI18n")
    override fun onResponse(post: DetailPost?) {
        val overviewPost = post?.post
        val user = post?.user

        Glide.with(this).load("https:${overviewPost?.image?.image}").into(img_main)
        tv_title.text = overviewPost?.title
        tv_time.text = "Một nghìn năm trước" // haven't make
        tv_saved.setOnClickListener {
            showNotification("Chức năng chưa hiện thực nha ♥") // haven't make
        }
        Glide.with(this).load(user?.avatar).into(img_avatar)
        tv_username.text = user?.full_name
        tv_email.text = user?.email
        tv_phone.text = user?.phone_number
        tv_phone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${tv_phone.text}")
            startActivity(intent)
        }
        tv_type.text = HashMapUtils.typeHouse[overviewPost?.type_house]
        tv_object.text = HashMapUtils.sex[overviewPost?.sex]
        tv_area.text = "${overviewPost?.area}m²"
        tv_price.text = "${overviewPost?.price} triệu/tháng"
        tv_address.text = "${overviewPost?.address?.add_detail}, ${overviewPost?.address?.district}, ${overviewPost?.address?.city}"
        tv_direct.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${tv_address.text}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.`package` = "com.google.android.apps.maps"
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            }
        }
        tv_description.text = overviewPost?.description
    }

    override fun onFailure(message: String?) {
        showNotification(message)
    }
}