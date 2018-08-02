package gggroup.com.baron.profile

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.bumptech.glide.Glide
import gggroup.com.baron.R
import gggroup.com.baron.adapter.IItemClickListener
import gggroup.com.baron.adapter.PostAdapter
import gggroup.com.baron.detail.DetailActivity
import gggroup.com.baron.entities.OverviewPost
import gggroup.com.baron.entities.ResultGetUser
import gggroup.com.baron.utils.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File
import gggroup.com.baron.UserInfo.UserInfoActivity


class ProfileDetailActivity : AppCompatActivity(),ProfileDetailContract.View {
    private var posts = ArrayList<OverviewPost>()
    private var adapter = PostAdapter(posts, this)
    private var presenter: ProfileDetailContract.Presenter? = null
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    lateinit var  file:File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        presenter = ProfileDetailPresenter(this)
        (presenter as ProfileDetailPresenter).getUser("1eb8fbe559ca23cec88c")
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        toolbar.inflateMenu(R.menu.profile_menu)
        toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
            //Change the ImageView image source depends on menu item click
                R.id.info -> {
                    startActivity(Intent(this@ProfileDetailActivity,UserInfoActivity::class.java))
                    return@OnMenuItemClickListener true
                }
                R.id.image -> {
                    return@OnMenuItemClickListener true
                }
            }
            //If above criteria does not meet then default is false;
            false
        })
        //(presenter as ProfileDetailPresenter).updateUser("cd9f944152c1a8095fa9","chính","0393939393",file  )
        initRecyclerView()
        initWaveSwipe()
    }
    override fun onResume() {
        super.onResume()
        presenter!!.getUserPosts("1eb8fbe559ca23cec88c",1)
    }
    override fun onResponseUserPosts(posts: ArrayList<OverviewPost>?) {
        if (posts != null) {
            adapter.setData(posts)
        }
        wave_swipe.isRefreshing = false
    }


    override fun setPresenter(presenter: ProfileDetailContract.Presenter) {
        this.presenter=presenter
    }

    override fun onResponse(resultGetUser: ResultGetUser) {
        cat_title.setText(resultGetUser.user!!.full_name)
        subtitle.setText(resultGetUser.user!!.email)
        Glide.with(this).load(resultGetUser.user!!.avatar).into(cat_avatar)
    }
    private fun initRecyclerView() {
        rv_profile.hasFixedSize()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_profile.layoutManager = layoutManager
        rv_profile.adapter = adapter
        adapter.setOnItemClickListener(object : IItemClickListener {
            override fun onClickItem(post: OverviewPost, animationView: ImageView) {
                val intent = Intent(this@ProfileDetailActivity, DetailActivity::class.java)
                intent.putExtra("post_id", post.id)
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@ProfileDetailActivity, animationView, getString(R.string.transition_image_detail))
                startActivity(intent, optionsCompat.toBundle())
            }
        })
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                presenter?.getUserPosts("1eb8fbe559ca23cec88c",page + 1)
            }
        }
        rv_profile.addOnScrollListener(scrollListener)

    }
    private fun refresh() {
        adapter.clearData()
        presenter?.getUserPosts("1eb8fbe559ca23cec88c",1)
    }
    private fun initWaveSwipe() {
        wave_swipe.setColorSchemeColors(Color.WHITE, Color.WHITE)
        wave_swipe.setWaveColor(Color.argb(200, 244, 67, 54))
        wave_swipe.setOnRefreshListener {
            refresh()
        }
    }
}
