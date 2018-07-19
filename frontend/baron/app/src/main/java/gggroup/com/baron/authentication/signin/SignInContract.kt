package gggroup.com.baron.authentication.signin

import android.graphics.Bitmap
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import gggroup.com.baron.entities.User
import retrofit2.Response

interface SignInContract {
    interface View {
        fun init()

        fun setPresenter(presenter: Presenter)

        fun getAccount()

        fun showNotification(message: String)

        fun onResponse()

        fun onFailure(t: String)

        fun resultLoading(circularProgressButton: CircularProgressButton,
                          fillColor: Int, bitmap: Bitmap,
                          messenger: String)
    }

    interface Presenter {
        fun checkAccount(email: String, password: String)

        fun handleSignInResult(completedTask: Task<GoogleSignInAccount>)
    }
}