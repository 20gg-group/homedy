package gggroup.com.baron.profile

import gggroup.com.baron.entities.ResultGetUser
import java.io.File

interface ProfileDetailContract {
    interface View{
        fun setPresenter(presenter: Presenter)
        fun onResponse(resultGetUser: ResultGetUser)
    }
    interface Presenter{
        fun getUser(token:String)
        fun updateUser(Access_Token:String,full_name: String,phone_number: String,avatar: File)
    }
}