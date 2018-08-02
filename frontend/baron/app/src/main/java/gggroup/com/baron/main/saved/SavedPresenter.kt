package gggroup.com.baron.main.saved

import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.Vote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SavedPresenter(internal var view: SavedContract.View):SavedContract.Presenter {
    override fun getVote(token: String) {
        CallAPI.createService().getVote(token)
                .enqueue(object : Callback<Vote>{
                    override fun onFailure(call: Call<Vote>?, t: Throwable?) {
                        view.onFailure(t?.message)
                    }

                    override fun onResponse(call: Call<Vote>?, response: Response<Vote>?) {
                        if (response != null) {
                            view.onResponse(response.body())
                        }
                    }

                })
    }
    init {
        view.setPresenter(this)
    }
}