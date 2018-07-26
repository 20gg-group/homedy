package gggroup.com.baron.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.ramotion.foldingcell.FoldingCell
import gggroup.com.baron.R
import gggroup.com.baron.entities.Post
import gggroup.com.baron.utils.StringProcess
import kotlinx.android.synthetic.main.activity_map.*
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

        //Title
        holder.tvAddressDistrict.text = StringProcess.getDistrict(post.address?:"")
        holder.tvAddressStreet.text = StringProcess.getStreet(post.address?:"")
        holder.tvPrice.text = "${post.price.toString()} triệu/tháng"
        holder.tvUtils.text = "Máy lạnh, máy giặt,..." // .....................
        holder.tvObject.text = post.sex
        holder.tvType.text = post.type
        holder.tvArea.text = post.area
        Glide.with(context).load(post.image_url).into(holder.imgMain)

        //Header content
        holder.tvTypeHeader.text = post.type
        holder.tvObjectHeader.text = post.sex
        Glide.with(context).load(post.image_url).into(holder.imgMainHeader)
        holder.tvTitleHeader.text = post.title

        //Body content
        Glide.with(context).load(post.user?.image_profile).into(holder.imgAvatarBody)
        holder.tvUsernameBody.text = post.user?.username
        holder.tvEmailBody.text = post.user?.email
        holder.tvPhoneBody.text = post.user?.phone
        holder.tvPhoneBody.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${holder.tvPhoneBody.text}")
            context.startActivity(intent)
        }
        holder.tvSavedBody.setOnClickListener {
            Toast.makeText(context, "Chức năng chưa hiện thực nha ♥", Toast.LENGTH_SHORT).show()
        }
        holder.tvAreaBody.text = post.area
        holder.tvPriceBody.text = "${post.price} triệu/tháng"
        holder.tvAddressBody.text = post.address
        holder.tvDirectBody.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${holder.tvAddressBody.text}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.`package` = "com.google.android.apps.maps"
            if (mapIntent.resolveActivity(context.packageManager) != null) {
               context.startActivity(mapIntent)
            }
          
        }
        holder.tvDescriptionBody.text = post.description
//        holder.btnCollapseBody.setOnClickListener {
//            (holder.itemView as FoldingCell).toggle(false)
//            registerToggle(position)
//        }
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

        val tvAddressDistrict = view.tv_address_district as TextView
        val tvAddressStreet = view.tv_address_street as TextView
        val tvPrice = view.tv_price as TextView
        val tvUtils = view.tv_utils as TextView
        val tvObject = view.tv_object as TextView
        val tvType = view.tv_type as TextView
        val tvArea = view.tv_area as TextView
        val imgMain = view.img_main as ImageView

        val tvTypeHeader = view.tv_type_header as TextView
        val tvObjectHeader = view.tv_object_header as TextView
        val imgMainHeader = view.img_main_header as ImageView
        val tvTitleHeader = view.tv_title_header as TextView
        val imgAvatarBody = view.img_avatar_body as ImageView
        val tvUsernameBody = view.tv_username_body as TextView
        val tvEmailBody = view.tv_email_body as TextView
        val tvPhoneBody = view.tv_phone_body as TextView
        val tvSavedBody = view.tv_saved_body as TextView
        val tvAreaBody = view.tv_area_body as TextView
        val tvPriceBody = view.tv_price_body as TextView
        val tvAddressBody = view.tv_address_body as TextView
        val tvDirectBody = view.tv_direct_body as TextView
        val tvDescriptionBody = view.tv_description_body as TextView
//        val btnCollapseBody = view.btn_collapse_body as Button

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            itemClickListener?.onClickItem(view, adapterPosition)
        }
    }
}