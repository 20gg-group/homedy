package gggroup.com.baron.detail

import gggroup.com.baron.entities.DetailPost
import gggroup.com.baron.entities.OverviewPost

interface DetailContract {
    interface View {
        fun showNotification(message: String?)

        fun setPresenter(presenter: Presenter)

        fun onResponse(post: DetailPost?)

        fun onFailure(message: String?)

        fun showRecommend(posts: ArrayList<OverviewPost>)

        fun onResponseSavePost()

        fun onFailureSavePost(message: String?)

        fun onResponseUnSavePost()

        fun onFailureUnSavePost(message: String?)

        fun showShimmerAnimation()

        fun hideShimmerAnimation()
    }

    interface Presenter {
        fun getDetailPost(id: Int)

        fun recommend(city: String?, district: String?, min_price: Float?, max_price: Float?, type: Int?)

        fun savePost(token: String?, id: String?)

        fun unsavePost(token: String?, id: String?)
    }
}