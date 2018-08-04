package gggroup.com.baron.posts

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import android.widget.Toast
import gggroup.com.baron.R
import gggroup.com.baron.adapter.IItemClickListener
import gggroup.com.baron.adapter.PostAdapter
import gggroup.com.baron.detail.DetailActivity
import gggroup.com.baron.entities.ItemSearch
import gggroup.com.baron.entities.OverviewPost
import kotlinx.android.synthetic.main.activity_list_post.*


class ListPostActivity : AppCompatActivity(), ListPostContract.View {

    private var posts = ArrayList<OverviewPost>()
    private var adapter = PostAdapter(posts, this)
    private var search: ItemSearch? = null
    private lateinit var presenter: ListPostContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_post)

        val bundle = intent.getBundleExtra("myBundle")
        search = bundle.getParcelable("search")
        initToolbar()

        initWaveSwipe()

        initRecyclerView()

        presenter = ListPostPresenter(this)

        presenter.getItemSearch(search?.city,search?.district,search?.minPrice,search?.maxPrice,search?.type_house)


    }

    private fun initRecyclerView() {
        recycler_view.hasFixedSize()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
//        adapter?.setData(postList)
        adapter.setOnItemClickListener(object : IItemClickListener {
            override fun onClickItem(post: OverviewPost, animationView: ImageView) {
                val intent = Intent(this@ListPostActivity, DetailActivity::class.java)
                intent.putExtra("post_id", post.id)
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@ListPostActivity, animationView, getString(R.string.transition_image_detail))
                startActivity(intent, optionsCompat.toBundle())
            }
        })
    }

    private fun initWaveSwipe() {
        wave_swipe.setColorSchemeColors(Color.WHITE, Color.WHITE)
        wave_swipe.setWaveColor(Color.argb(200, 244, 67, 54))
        wave_swipe.setOnRefreshListener {
            refresh()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
            this.overridePendingTransition(0,R.anim.back_right)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.overridePendingTransition(0,R.anim.back_right)
        finish()
    }
    private fun refresh() {
        adapter.clearData()
        presenter.getItemSearch(search?.city,search?.district,search?.minPrice,search?.maxPrice,search?.type_house)
    }

    override fun showNotification(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: ListPostContract.Presenter) {
        this.presenter = presenter
    }

    override fun onResponse(posts: ArrayList<OverviewPost>) {
        adapter.setData(posts)
        wave_swipe.isRefreshing = false
    }

    override fun onFailure(message: String?) {
        showNotification(message)
    }
}