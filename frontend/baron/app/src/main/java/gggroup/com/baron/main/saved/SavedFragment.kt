package gggroup.com.baron.main.saved

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import gggroup.com.baron.R
import gggroup.com.baron.entities.Vote

class SavedFragment : Fragment(),SavedContract.View {

    private var presenter: SavedContract.Presenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view=inflater.inflate(R.layout.fragment_saved,container,false)
        presenter=SavedPresenter(this)
        (presenter as SavedPresenter).getVote("5cf2ef41dc83cb509c8e")
        return view
    }
    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        val item = menu?.findItem(R.id.action_filter)
        item?.isVisible = false
    }
    override fun setPresenter(presenter: SavedContract.Presenter) {
        this.presenter=presenter
    }

    override fun onResponse(vote: Vote?) {

    }

    override fun onFailure(message: String?) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

}