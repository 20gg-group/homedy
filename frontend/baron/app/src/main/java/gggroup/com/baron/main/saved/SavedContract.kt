package gggroup.com.baron.main.saved

import gggroup.com.baron.entities.AllPosts

interface SavedContract {
    interface View{
        fun setPresenter(presenter: Presenter)
        fun onResponse(vote: AllPosts?)
        fun onFailure(message: String?)
    }
    interface Presenter{
        fun getVote(token:String)
    }
}