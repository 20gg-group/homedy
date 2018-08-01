package gggroup.com.baron.posts

import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.AllPosts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPostPresenter(internal var view: ListPostContract.View) : ListPostContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getAllPosts() {
        CallAPI.createService()
                .getAllPosts()
                .enqueue(object : Callback<AllPosts> {
                    override fun onResponse(call: Call<AllPosts>?, response: Response<AllPosts>?) {
                        if (response?.body() != null)
                         view.onResponse(response.body()?.posts?.post)

                    }

                    override fun onFailure(call: Call<AllPosts>?, t: Throwable?) {
                        view.onFailure(t?.message)
                    }
                })
    }
}