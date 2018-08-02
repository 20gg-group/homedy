package gggroup.com.baron.posts

import gggroup.com.baron.entities.OverviewPost

interface ListPostContract {
    interface View {
        fun showNotification(message: String?)

        fun setPresenter(presenter: Presenter)

        fun onResponse(posts: ArrayList<OverviewPost>?)

        fun onFailure(message: String?)
    }

    interface Presenter {
        fun getAllPosts()
    }
}