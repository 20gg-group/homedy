package gggroup.com.baron.detail

import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.DetailPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter(internal var view: DetailContract.View) : DetailContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getDetailPost(id: Int) {
        CallAPI.createService()
                .getDetailPost(id)
                .enqueue(object : Callback<DetailPost> {
                    override fun onResponse(call: Call<DetailPost>?, response: Response<DetailPost>?) {
                        if (response?.body() != null)
                            view.onResponse(response.body())
                    }

                    override fun onFailure(call: Call<DetailPost>?, t: Throwable?) {
                        view.onFailure(t?.message)
                    }
                })
    }
}