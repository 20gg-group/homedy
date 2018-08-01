package gggroup.com.baron.detail

import gggroup.com.baron.entities.DetailPost

interface DetailContract {
    interface View {
        fun showNotification(message: String?)

        fun setPresenter(presenter: Presenter)

        fun onResponse(post: DetailPost?)

        fun onFailure(message: String?)
    }

    interface Presenter {
        fun getDetailPost(id: Int)
    }
}