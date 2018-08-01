package gggroup.com.baron.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import gggroup.com.baron.R
import gggroup.com.baron.entities.DetailPost
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailContract.View {

    private var presenter: DetailContract.Presenter? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initToolbar()

        presenter = DetailPresenter(this)

//        val post = examplePost()

        //Header content
//        tv_type_header.text = post.type
//        tv_object_header.text = post.sex
//        Glide.with(this).load(post.image_url).into(img_main_header)
//        tv_title_header.text = post.title

        //Body content
//        Glide.with(this).load(post.user?.image_profile).into(img_avatar_body)
//        tv_username_body.text = post.user?.username
//        tv_email_body.text = post.user?.email
//        tv_phone_body.text = post.user?.phone
//        tv_phone_body.setOnClickListener {
//            val intent = Intent(Intent.ACTION_DIAL)
//            intent.data = Uri.parse("tel:${tv_phone_body.text}")
//            startActivity(intent)
//        }
//        tv_saved_body.setOnClickListener {
//            Toast.makeText(this, "Chức năng chưa hiện thực nha ♥", Toast.LENGTH_SHORT).show()
//        }
//        tv_area_body.text = post.area
//        tv_price_body.text = "${post.price} triệu/tháng"
//        tv_address_body.text = post.address
//        tv_direct_body.setOnClickListener {
//            val gmmIntentUri = Uri.parse("geo:0,0?q=${tv_address_body.text}")
//            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//            mapIntent.`package` = "com.google.android.apps.maps"
//            if (mapIntent.resolveActivity(packageManager) != null) {
//               startActivity(mapIntent)
//            }
//        }
//        tv_description_body.text = post.description

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

    override fun onResponse(post: DetailPost?) {

    }

    override fun onFailure(message: String?) {
        showNotification(message)
    }
}