package gggroup.com.baron.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import gggroup.com.baron.R

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ProfileHolder> {
    private var arrName: ArrayList<String>
    private var context:Context?=null
    constructor(arrName: ArrayList<String>) : super() {
        this.arrName = arrName
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {
        return ProfileHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false))
    }

    override fun getItemCount(): Int {
       return arrName.size
    }

    override fun onBindViewHolder(holder: ProfileHolder, position: Int) {
        holder.tv_name.text=arrName[position]
        holder.tv_name.setOnClickListener {
        }
    }

    class ProfileHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var tv_name:TextView=itemView.findViewById(R.id.tv_name)
    }
}