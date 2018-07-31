package gggroup.com.baron.main.profile

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gggroup.com.baron.R
import gggroup.com.baron.entities.ResultGetUser
import gggroup.com.baron.entities.UserFrofile
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File

class ProfileFragment : Fragment(),ProfileContract.View {
    private var presenter: ProfileContract.Presenter? = null
    var avatar: File? = null
    override fun setPresenter(presenter: ProfileContract.Presenter) {
        this.presenter=presenter

    }

    override fun onResponse(resultGetUser: ResultGetUser) {
        cat_title.setText(resultGetUser.user!!.full_name)
        subtitle.setText(resultGetUser.user!!.email)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       var view=inflater.inflate(R.layout.fragment_profile, container,false)
        presenter=ProfilePresenter(this)
        (presenter as ProfilePresenter).getUser("f1973c4f845eb488c177")
        (presenter as ProfilePresenter).updateUser("f1973c4f845eb488c177","le","030939393")
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}