package gggroup.com.baron.main.saved

import gggroup.com.baron.entities.Vote

interface SavedContract {
    interface View{
        fun setPresenter(presenter: Presenter)
        fun onResponse(vote: Vote?)
        fun onFailure(message: String?)
    }
    interface Presenter{
        fun getVote(token:String)
    }
}