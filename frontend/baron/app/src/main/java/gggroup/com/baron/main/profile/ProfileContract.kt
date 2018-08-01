package gggroup.com.baron.main.profile


import gggroup.com.baron.entities.ResultGetUser

interface ProfileContract {
    interface View{
        fun setPresenter(presenter: Presenter)
        fun onResponse(resultGetUser: ResultGetUser)
    }
    interface Presenter{
        fun getUser(token:String)
        fun updateUser(Access_Token:String,full_name: String,phone_number: String,avatar:File)
    }
}