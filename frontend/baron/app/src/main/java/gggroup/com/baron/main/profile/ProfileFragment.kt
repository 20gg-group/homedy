package gggroup.com.baron.main.profile


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.FrameLayout
import gggroup.com.baron.R
import gggroup.com.baron.entities.ResultGetUser
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File

class ProfileFragment : Fragment(),ProfileContract.View {
    private var presenter: ProfileContract.Presenter? = null
    var avatar: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun setPresenter(presenter: ProfileContract.Presenter) {
        this.presenter=presenter

    }
    override fun onResponse(resultGetUser: ResultGetUser) {
        cat_title.text = resultGetUser.user?.full_name
        subtitle.text = resultGetUser.user?.email
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view=inflater.inflate(R.layout.fragment_profile, container,false)
        presenter=ProfilePresenter(this)
        (presenter as ProfilePresenter).getUser()
        (presenter as ProfilePresenter).updateUser("le","030939393")
        return view
    }
    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        val item = menu?.findItem(R.id.action_filter)
        item?.isVisible = false
    }


}