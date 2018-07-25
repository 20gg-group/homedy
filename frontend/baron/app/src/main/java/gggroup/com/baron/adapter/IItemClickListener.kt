package gggroup.com.baron.adapter

import android.view.View

interface IItemClickListener {
    fun onClickItem(view : View?, position: Int)
}