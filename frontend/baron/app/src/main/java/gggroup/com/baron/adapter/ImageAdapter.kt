package gggroup.com.baron.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.esafirm.imagepicker.model.Image
import gggroup.com.baron.R
import java.io.File


class ImageAdapter(private val images: List<Image>) : RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.imageView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.image.setImageURI(Uri.fromFile(File(images[position].path)))
    }

    override fun getItemCount(): Int {
        return images.size
    }
}