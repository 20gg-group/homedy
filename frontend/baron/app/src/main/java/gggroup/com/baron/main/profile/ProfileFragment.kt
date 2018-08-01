package gggroup.com.baron.main.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gggroup.com.baron.R
import gggroup.com.baron.adapter.ProfileAdapter
import gggroup.com.baron.authentication.signin.SignInActivity
import gggroup.com.baron.entities.ResultGetUser
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


    class ProfileFragment : Fragment(),ProfileContract.View {
    private lateinit var arrName: ArrayList<String>
    private lateinit var rv_profile:RecyclerView
    var ic_edit: FloatingActionButton? = null
    private var presenter: ProfileContract.Presenter? = null
    override fun setPresenter(presenter: ProfileContract.Presenter) {
        this.presenter = presenter
    }

    override fun onResponse(resultGetUser: ResultGetUser) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_profile, container, false)
        ic_edit = view.findViewById(R.id.ic_edit)
        rv_profile=view.findViewById(R.id.rv_profile)
        arrName= ArrayList()

        presenter = ProfilePresenter(this)
        (presenter as ProfilePresenter).getUser("5a1cb19814f6cb529864")
        setUp()
        return view
    }
    private fun setUp() {
        arrName.add(0,"Cập nhật ảnh đại diện")
        arrName.add(1,"Số điện thoại")
        arrName.add(2,"Đăng xuất")
        rv_profile.layoutManager = LinearLayoutManager(activity)
        rv_profile.setHasFixedSize(true)
        rv_profile.itemAnimator = DefaultItemAnimator()
        rv_profile.addItemDecoration(DividerItemDecoration(rv_profile.context, DividerItemDecoration.VERTICAL))
        rv_profile.adapter = ProfileAdapter(arrName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}