package gggroup.com.baron.main.saved

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.facebook.shimmer.ShimmerFrameLayout
import gggroup.com.baron.R
import gggroup.com.baron.adapter.IItemClickListener
import gggroup.com.baron.adapter.PostAdapter
import gggroup.com.baron.detail.DetailActivity
import gggroup.com.baron.entities.OverviewPost

class SavedFragment : Fragment(), SavedContract.View {

    private var posts: ArrayList<OverviewPost>
    private var presenter: SavedContract.Presenter
    private var adapter: PostAdapter? = null
    private var shimmerLayout : ShimmerFrameLayout?= null

    companion object {
        fun newInstance(): SavedFragment {
            return SavedFragment()
        }
    }

    init {
        posts = ArrayList()
        presenter = SavedPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)

        //Bind View
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_saved)
        shimmerLayout = view.findViewById(R.id.shimmer_layout)

        //Set up toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }

        //Set up recycler view
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = PostAdapter(posts, context!!)
        adapter?.setType(2)
        recyclerView.adapter = adapter
        adapter?.setOnItemClickListener(object : IItemClickListener {
            override fun onClickItem(post: OverviewPost, animationView: ImageView) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("post_id", post.id)
                startActivity(intent)
            }
        })

        val token = context?.getSharedPreferences("_2life", Context.MODE_PRIVATE)?.getString("TOKEN_USER", "")
        if (posts.isEmpty())
            presenter.getVote(token)
        else hideShimmerAnimation()
        return view
    }

    override fun setPresenter(presenter: SavedContract.Presenter) {
        this.presenter = presenter
    }

    override fun onResponse(posts: ArrayList<OverviewPost>?) {
        hideShimmerAnimation()
        this.posts = posts!!
        adapter?.setData(posts)
    }

    override fun onFailure(message: String?) {
        showNotification(message)
    }

    override fun showNotification(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showShimmerAnimation() {
        shimmerLayout?.startShimmerAnimation()
    }

    override fun hideShimmerAnimation() {
        shimmerLayout?.stopShimmerAnimation()
        shimmerLayout?.visibility = View.GONE
    }
}