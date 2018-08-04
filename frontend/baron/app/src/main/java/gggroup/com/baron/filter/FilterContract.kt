package gggroup.com.baron.filter

import java.util.*

interface FilterContract {
    interface View {
//        fun showNotification(message: String)

        fun setPresenter(presenter: Presenter)

//        fun onResponse(posts: ArrayList<OverviewPost>?)

//        fun onFailure(message: String)

        fun setSpinnerDistrict(districts: LinkedList<String>)

        fun onClick()

        fun show(isShow: Boolean)

        fun actionSearch()
    }

    interface Presenter {
        fun getDistrict(id: Int)

        fun getAllDistrict()

        //fun actionSearch(city: String, district:String, min_price:Float, max_price:Float, type:Int)
    }
}