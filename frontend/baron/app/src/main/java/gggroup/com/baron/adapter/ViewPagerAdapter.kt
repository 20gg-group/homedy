package gggroup.com.baron.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import gggroup.com.baron.R
import gggroup.com.baron.entities.Post
import gggroup.com.baron.utils.StringProcess

class ViewPagerAdapter(private val context: Context?,private val posts: MutableList<Post>) : PagerAdapter() {

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image_card, container, false)

        val post = posts[position]


        val imgCard = view.findViewById<ImageView>(R.id.img_card)
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val tvPrice = view.findViewById<TextView>(R.id.tv_price)
        val tvArea = view.findViewById<TextView>(R.id.tv_area)
        val tvAddress = view.findViewById<TextView>(R.id.tv_address)

        Glide.with(context!!).load(post.image_url).into(imgCard)

        tvTitle.text = post.title
        tvPrice.text = "${post.price} triệu/tháng"
        tvArea.text = post.area
        tvAddress.text = StringProcess.getDistrictCity(post.address?:"")

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = posts.size

}