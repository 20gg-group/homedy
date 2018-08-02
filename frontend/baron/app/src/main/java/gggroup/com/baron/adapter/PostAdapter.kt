package gggroup.com.baron.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import gggroup.com.baron.R
import gggroup.com.baron.entities.OverviewPost
import gggroup.com.baron.utils.HashMapUtils
import kotlinx.android.synthetic.main.item_rv_post.view.*

class PostAdapter(private var posts: ArrayList<OverviewPost>, private val context: Context) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var itemClickListener : IItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_post, parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        holder.tvAddressDistrict.text = post.address?.district
        holder.tvAddressStreet.text = post.address?.add_detail
        holder.tvPrice.text = "${post.price.toString()} triệu/tháng"
        holder.tvUtils.text = getUtilsRoom(post.detail_ids)
        holder.tvObject.text = HashMapUtils.sex[post.sex]
        holder.tvType.text = HashMapUtils.typeHouse[post.type_house]
        holder.tvArea.text = "${post.area}m²"
        Glide.with(context).load("https:${post.image?.image}").into(holder.imgMain)
    }

    private fun getUtilsRoom(utils : List<String>?) = when (utils?.size) {
        0 -> ""
        1 -> utils[0]
        2 -> "${utils[0]}, ${utils[1]}"
        else -> "${utils!![0]}, ${utils[1]},..."
    }


    override fun getItemCount(): Int {
        return posts.size
    }


    fun setData(posts: ArrayList<OverviewPost>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    fun clearData() {
        posts.clear()
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(itemClickListener: IItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val tvAddressDistrict = view.tv_address_district as TextView
        val tvAddressStreet = view.tv_address_street as TextView
        val tvPrice = view.tv_price as TextView
        val tvUtils = view.tv_utils as TextView
        val tvObject = view.tv_object as TextView
        val tvType = view.tv_type as TextView
        val tvArea = view.tv_area as TextView
        val imgMain = view.img_main as ImageView

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            itemClickListener?.onClickItem(posts[adapterPosition], imgMain)
        }
    }
}