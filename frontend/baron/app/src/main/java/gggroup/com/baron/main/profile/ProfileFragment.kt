package gggroup.com.baron.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import gggroup.com.baron.R
import gggroup.com.baron.authentication.signin.SignInActivity
import gggroup.com.baron.entities.ResultGetUser
import gggroup.com.baron.user.profile.ProfileDetailActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), ProfileContract.View {
    private var presenter: ProfileContract.Presenter? = null
    private var profile_detail: ConstraintLayout? = null
    private var logout: ConstraintLayout? = null

    private lateinit var tv_phone: TextView
    private lateinit var tv_email: TextView
    private lateinit var tv_fullname: TextView
    private lateinit var avatar: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_profile, container, false)

        tv_email = view.findViewById(R.id.tv_email)
        tv_phone = view.findViewById(R.id.tv_phone)
        tv_fullname = view.findViewById(R.id.tv_full_name)
        avatar = view.findViewById(R.id.img_avatar)
        profile_detail = view.findViewById(R.id.profile_detail)
        logout = view.findViewById(R.id.logout)

        presenter = ProfilePresenter(this)

        val token = context?.getSharedPreferences("_2life", Context.MODE_PRIVATE)
                ?.getString("TOKEN_USER","")

        (presenter as ProfilePresenter).getUser(token!!)
        profile_detail?.setOnClickListener {
            startActivity(Intent(this.context, ProfileDetailActivity::class.java))
        }
        logout?.setOnClickListener {
            context?.getSharedPreferences("_2life", Context.MODE_PRIVATE)?.edit()
                    ?.putString("TOKEN_USER", "empty")?.apply()
            startActivity(Intent(this.context, SignInActivity::class.java))
        }
        return view
    }

    override fun setPresenter(presenter: ProfileContract.Presenter) {
        this.presenter = presenter
    }

    override fun onResponse(resultGetUser: ResultGetUser?) {
        if (resultGetUser != null) {
            tv_email.text = resultGetUser.user?.email
            tv_phone.text = resultGetUser.user?.phone_number
            tv_fullname.text = resultGetUser.user?.full_name
            this.context?.let { Glide.with(it).load(resultGetUser.user?.avatar).apply(RequestOptions.circleCropTransform()).into(img_avatar) }
        }
    }

    override fun showNotification(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(message: String?) {
        showNotification(message)
    }
}