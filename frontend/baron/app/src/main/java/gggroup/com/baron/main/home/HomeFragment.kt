package gggroup.com.baron.main.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator
import gggroup.com.baron.R
import gggroup.com.baron.adapter.ViewPagerAdapter
import gggroup.com.baron.utils.OnPagerNumberChangeListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), OnPagerNumberChangeListener {

    private var pagerAdapter: ViewPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, null)
        //Bind view
        val viewPager = view.findViewById<ViewPager>(R.id.view_pager)
        val pagerIndicator = view.findViewById<IndefinitePagerIndicator>(R.id.viewpager_pager_indicator)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_home)

        //Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            (activity as AppCompatActivity).onBackPressed()
        }

//        val posts = exampleData()
//
//        pagerAdapter = ViewPagerAdapter(context, posts)
//
//
//        viewPager.adapter = pagerAdapter
//        pagerIndicator.attachToViewPager(viewPager)

        return view
    }

    private fun bindView() {

    }
    override fun onPagerNumberChanged() {
        pagerAdapter?.notifyDataSetChanged()
    }

}