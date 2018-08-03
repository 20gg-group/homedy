package gggroup.com.baron.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import gggroup.com.baron.R
import gggroup.com.baron.adapter.PostAdapter
import gggroup.com.baron.adapter.UtilAdapter
import gggroup.com.baron.entities.DetailPost
import gggroup.com.baron.entities.OverviewPost
import gggroup.com.baron.utils.HashMapUtils
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailContract.View {

    private var presenter: DetailContract.Presenter? = null
    private var utilAdapter: UtilAdapter? = null
    private var recommendAdapter: PostAdapter? = null

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
        hideShimmerAnimation()
        val overviewPost = post?.post
        val user = post?.user

        Glide.with(applicationContext).load("https:${overviewPost?.image?.image}").into(img_main)
        tv_title.text = overviewPost?.title
        tv_time.text = "Một nghìn năm trước" // haven't make
        btn_save.setOnClickListener {
            btn_save.isChecked = !btn_save.isChecked
            val token = getSharedPreferences("_2life", Context.MODE_PRIVATE)
                    .getString("TOKEN_USER", "")
            if (btn_save.isChecked) {
                btn_save.playAnimation()
                presenter?.savePost(token, overviewPost?.id.toString())
            } else {
                presenter?.unsavePost(token, overviewPost?.id.toString())
            }
        }
        Glide.with(applicationContext).load(user?.avatar).into(img_avatar)
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

        utilAdapter = UtilAdapter(post?.post?.detail_ids as ArrayList<String>, this)
        rv_utils.adapter = utilAdapter
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_utils.layoutManager = gridLayoutManager
        rv_utils.isNestedScrollingEnabled = false
        presenter?.recommend(post.post?.address?.city, post.post?.address?.district,
                post.post?.price?.minus(1), post.post?.price?.plus(1), post.post?.type_house)
    }

    override fun showRecommend(posts: ArrayList<OverviewPost>) {
        rv_recommend.hasFixedSize()
        rv_recommend.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recommendAdapter = PostAdapter(posts, this)
        recommendAdapter?.setType(1)
        rv_recommend.adapter = recommendAdapter
    }

    override fun onFailure(message: String?) {
        showNotification(message)
    }

    override fun onResponseSavePost() {

    }

    override fun onFailureSavePost(message: String?) {
        showNotification(message)
    }

    override fun onResponseUnSavePost() {

    }

    override fun onFailureUnSavePost(message: String?) {
        showNotification(message)
    }

    override fun showShimmerAnimation() {
        shimmer_layout.startShimmerAnimation()
    }

    override fun hideShimmerAnimation() {
        shimmer_layout.stopShimmerAnimation()
        shimmer_layout.visibility = View.GONE
    }
}