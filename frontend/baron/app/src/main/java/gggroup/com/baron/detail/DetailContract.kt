package gggroup.com.baron.detail

import gggroup.com.baron.entities.DetailPost
import gggroup.com.baron.entities.OverviewPost

interface DetailContract {
    interface View {
        fun showNotification(message: String?)

        fun setPresenter(presenter: Presenter)

        fun onResponseDetailPost(post: DetailPost?)

        fun onResponseRecommend(posts: ArrayList<OverviewPost>)

        fun onResponseSavePost()

        fun onResponseUnSavePost()

        fun onResponseCheckVoted(status: String?)

        fun onFailure(message: String?)

        fun getImage(URL: String):ArrayList<String>

        fun checkImage(URL: String, top: String, second: String): Boolean
    }

    interface Presenter {
        fun getDetailPost(id: Int)

        fun recommend(city: String?, district: String?, min_price: Float?, max_price: Float?, type: Int?,URL: String)

        fun savePost(token: String?, id: String?)

        fun unsavePost(token: String?, id: String?)

        fun checkVoted(token: String?, id: String?)

        fun recommendWithAI(city: String?, district: String?, min_price: Float?, max_price: Float?, type: Int?,URL: String,ID: Int)
    }
}