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
import gggroup.com.baron.adapter.ViewPagerAdapter
import gggroup.com.baron.entities.DetailPost
import gggroup.com.baron.entities.OverviewPost
import gggroup.com.baron.utils.HashMapUtils
import kotlinx.android.synthetic.main.activity_detail.*
import gggroup.com.baron.tensorflow.TensorFlowImageClassifier
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.os.Handler
import android.support.v4.app.ActivityOptionsCompat
import android.util.Log
import android.widget.ImageView
import gggroup.com.baron.adapter.IItemClickListener
import java.util.concurrent.Executors

import gggroup.com.baron.tensorflow.Classifier
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL
import java.util.concurrent.TimeUnit


class DetailActivity : AppCompatActivity(), DetailContract.View {
    private val INPUT_SIZE = 224
    private val IMAGE_MEAN = 128
    private val IMAGE_STD = 128.0f
    private val INPUT_NAME = "input"
    private val OUTPUT_NAME = "final_result"

    private val MODEL_FILE = "file:///android_asset/graph.pb"
    private val LABEL_FILE = "file:///android_asset/labels.txt"
    private var topResult: String? = "none"
    var secondResult: String? = "none"
    private var topResultConfidence: Float? = 0.0f
    var secondResultConfidence: Float? = 0.0f
    private var classifier: Classifier? = null
    private var executor = Executors.newSingleThreadExecutor()
    private var postId = -1

    private var presenter: DetailContract.Presenter? = null
    private var recommendAdapter: PostAdapter? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()

        presenter = DetailPresenter(this)

        postId = intent.getIntExtra("post_id", -1)

        if (postId == -1)
            showNotification("Vui lòng thử lại sau")

        val token = getSharedPreferences("_2life", Context.MODE_PRIVATE)
                .getString("TOKEN_USER", "")

        presenter?.getDetailPost(postId)
        presenter?.checkVoted(token, postId.toString())

        val mExecutor = Executors.newFixedThreadPool(3)
        mExecutor.execute({
            initTensorFlowAndLoadModel()
        })

        mExecutor.shutdown()
        mExecutor.awaitTermination(java.lang.Long.MAX_VALUE, TimeUnit.DAYS)

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
    override fun onResponseDetailPost(post: DetailPost?) {
        val overviewPost = post?.post
        val user = post?.user

        val pagerAdapter = ViewPagerAdapter(applicationContext, post?.images_url)
        view_pager.adapter = pagerAdapter
        pager_indicator.attachToViewPager(view_pager)
        val URL = "https:${post?.images_url?.get(0)?.image}"
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
            } else Toast.makeText(this,"Vui lòng cài đặt Google Maps",Toast.LENGTH_SHORT).show()
        }
        tv_description.text = overviewPost?.description

        val utilAdapter = UtilAdapter(post?.post?.detail_ids as ArrayList<String>, this)
        rv_utils.adapter = utilAdapter
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_utils.layoutManager = gridLayoutManager
        rv_utils.isNestedScrollingEnabled = false

        val mExecutor = Executors.newFixedThreadPool(4)
        mExecutor.execute({
//            presenter?.recommend(post.post?.address?.city, post.post?.address?.district,
//                    post.post?.price?.minus(1), post.post?.price?.plus(1), post.post?.type_house,URL)
            presenter?.recommendWithAI(post.post?.address?.city, post.post?.address?.district,
                    post.post?.price?.minus(1), post.post?.price?.plus(1), post.post?.type_house,URL,postId)
        })
        mExecutor.shutdown()
        mExecutor.awaitTermination(java.lang.Long.MAX_VALUE, TimeUnit.DAYS)
    }

    override fun onResponseRecommend(posts: ArrayList<OverviewPost>) {
        rv_recommend.hasFixedSize()
        rv_recommend.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recommendAdapter = PostAdapter(posts, this)
        recommendAdapter?.setType(1)
        rv_recommend.adapter = recommendAdapter
        recommendAdapter?.setOnItemClickListener(object : IItemClickListener {
            override fun onClickItem(post: OverviewPost, animationView: ImageView) {
                val intent = Intent(this@DetailActivity, DetailActivity::class.java)
                intent.putExtra("post_id", post.id)
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@DetailActivity, animationView, getString(R.string.transition_image_detail))
                btn_save.visibility = View.VISIBLE
                startActivity(intent, optionsCompat.toBundle())
                with(Handler()) {
                    postDelayed({finish()}, 500)
                }
            }
        })
    }

    override fun onResponseSavePost() {

    }

    override fun onResponseUnSavePost() {

    }


    override fun onResponseCheckVoted(status: String?) {
        if (status == "true")
            btn_save.isChecked = true
    }

    override fun onFailure(message: String?) {
        showNotification(message)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        btn_save.visibility = View.VISIBLE

        finish()
    }

    private fun initTensorFlowAndLoadModel() {
        executor.execute({
            try {
                classifier = TensorFlowImageClassifier.create(
                        this@DetailActivity.assets,
                        MODEL_FILE,
                        LABEL_FILE,
                        INPUT_SIZE,
                        IMAGE_MEAN,
                        IMAGE_STD,
                        INPUT_NAME,
                        OUTPUT_NAME)


            } catch (e: Exception) {
                throw RuntimeException("Error initializing TensorFlow!", e)
            }
        })
    }

    override fun getImage(URL: String):ArrayList<String>{
        val result: ArrayList<String> = ArrayList()
        var bitmap: Bitmap? = null
        val ins: InputStream?
        val bis: BufferedInputStream?
        try {
            val conn = URL("https://s3.amazonaws.com/salty-brushlands-19787/images/images/000/000/092/original/20160701085618-6558.jpg?1533484271").openConnection()
            conn.connect()
            ins = conn.getInputStream()
            bis = BufferedInputStream(ins, 8192)
            bitmap = BitmapFactory.decodeStream(bis)
        } catch (e: Exception) {
            e.printStackTrace()
        }

//        val mExecutor = Executors.newFixedThreadPool(3)
//        mExecutor.execute({
//            bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false)
//        })
//        mExecutor.shutdown()
//        mExecutor.awaitTermination(java.lang.Long.MAX_VALUE, TimeUnit.DAYS)

        val results: List<Classifier.Recognition>? = classifier?.recognizeImage(bitmap)


        if(results !=null) {
            topResult = results[0].title
            topResultConfidence = results[0].confidence
            val size = results.size - 1
            if (size >= 1) {
                secondResult = results[1].title
                secondResultConfidence = results[1].confidence
                if (secondResultConfidence!! < 0.5) {
                    secondResult = "none"
                }
            }
            if (topResultConfidence!! < 0.5) {
                topResult = "none"
            }
        }
        result.add(topResult.toString())
        result.add(secondResult.toString())
        return result
    }
    override fun checkImage(URL: String, top: String, second: String): Boolean {

        var bitmap: Bitmap? = null
        val ins: InputStream?
        val bis: BufferedInputStream?
        try {
            val conn = java.net.URL(URL).openConnection()
            conn.connect()
            ins = conn.getInputStream()
            bis = BufferedInputStream(ins, 8192)
            bitmap = BitmapFactory.decodeStream(bis)
        } catch (e: Exception) {
            e.printStackTrace()
        }

//        val mExecutor = Executors.newFixedThreadPool(3)
////        mExecutor.execute({
////            bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false)
////        })
//        mExecutor.shutdown()
//        mExecutor.awaitTermination(java.lang.Long.MAX_VALUE, TimeUnit.DAYS)

        val results: List<Classifier.Recognition>? = classifier?.recognizeImage(bitmap)


        if (results != null) {
            topResult = results[0].title
            topResultConfidence = results[0].confidence
            if (topResult == top || topResult == second) return true
            val size = results.size - 1
            if (size >= 1) {
                secondResult = results[1].title
                secondResultConfidence = results[1].confidence

                if (secondResultConfidence!! < 0.5) {
                    if (secondResult == top || secondResult == second) return true
                }
            }

        }
        return false
    }
    override fun onDestroy() {
        super.onDestroy()
        Runtime.getRuntime().gc()
        finish()
    }
}