package gggroup.com.baron.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import gggroup.com.baron.R
import gggroup.com.baron.entities.Post
import gggroup.com.baron.utils.StringProcess
import kotlinx.android.synthetic.main.item_rv_post_content.view.*
import kotlinx.android.synthetic.main.item_rv_post_title.view.*

class PostAdapter(private var posts: List<Post>, private val context: Context) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val unfoldedIndexes = HashSet<Int>()
    var itemClickListener : IItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_post, parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        holder.tvPrice.text = "₫ ${post.price.toString()} Triệu"
        holder.tvAddress.text = StringProcess.getDistrict(post.address?:"")
        holder.tvType.text = post.type
        Glide.with(context).load(post.image_url).into(holder.imgMain)

        holder.tvTypeHeader.text = post.type
        holder.tvObjectHeader.text = post.sex
        Glide.with(context).load(post.image_url).into(holder.imgMainHeader)
        holder.tvTitleHeader.text = post.title
        Glide.with(context).load(post.user?.image_profile).into(holder.imgAvatarBody)
        holder.tvUsernameBody.text = post.user?.username
        holder.tvEmailBody.text = post.user?.email
        holder.tvPhoneBody.text = post.user?.phone
        holder.btnSavedBody.setOnClickListener {
            Toast.makeText(context, "Chức năng chưa hiện thực nha ♥", Toast.LENGTH_SHORT).show()
        }
        holder.tvAreaBody.text = post.area
        holder.tvPriceBody.text = "${post.price} triệu/tháng"
        holder.tvAddressBody.text = post.address
        holder.tvDescriptionBody.text = post.description
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    // simple methods for register cell state changes
    fun registerToggle(position: Int) {
        if (unfoldedIndexes.contains(position))
            registerFold(position)
        else
            registerUnfold(position)
    }

    private fun registerFold(position: Int) {
        unfoldedIndexes.remove(position)
    }

    private fun registerUnfold(position: Int) {
        unfoldedIndexes.add(position)
    }

    fun setData(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(itemClickListener: IItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val tvPrice = view.tv_price as TextView
        val tvAddress = view.tv_address as TextView
        val tvType = view.tv_type as TextView
        val imgMain = view.img_main as ImageView

        val tvTypeHeader = view.tv_type_header as TextView
        val tvObjectHeader = view.tv_object_header as TextView
        val imgMainHeader = view.img_main_header as ImageView
        val tvTitleHeader = view.tv_title_header as TextView
        val imgAvatarBody = view.img_avatar_body as ImageView
        val tvUsernameBody = view.tv_username_body as TextView
        val tvEmailBody = view.tv_email_body as TextView
        val tvPhoneBody = view.tv_phone_body as TextView
        val btnSavedBody = view.btn_saved_body as TextView
        val tvAreaBody = view.tv_area_body as TextView
        val tvPriceBody = view.tv_price_body as TextView
        val tvAddressBody = view.tv_address_body as TextView
        val tvDescriptionBody = view.tv_description_body as TextView

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            itemClickListener?.onClickItem(view, adapterPosition)
        }
    }
}