package gggroup.com.baron.main.home

class HomePresenter(internal var view: HomeContract.View) : HomeContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun getHotPost() {

    }
}