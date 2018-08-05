package gggroup.com.baron.posts

import gggroup.com.baron.entities.OverviewPost

interface ListPostContract {
    interface View {
        fun showNotification(message: String?)

        fun setPresenter(presenter: Presenter)

        fun onResponse(posts: ArrayList<OverviewPost>?)

        fun onFailure(message: String?)

        fun showShimmerAnimation()

        fun hideShimmerAnimation()
    }

    interface Presenter {
        //fun getAllPosts(page: Int)
        fun getItemSearch(city: String?, district:String?, min_price:Float?, max_price:Float?, type:Int?)

        fun getItemByType(type: Int?)
    }
}