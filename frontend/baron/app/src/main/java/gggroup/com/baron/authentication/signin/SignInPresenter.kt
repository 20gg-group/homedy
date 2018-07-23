package gggroup.com.baron.authentication.signin

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.AuthResponse
import gggroup.com.baron.entities.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInPresenter(internal var view: SignInContract.View) : SignInContract.Presenter {
    init {
        view.setPresenter(this)
    }
    override fun checkAccount(email: String, password: String) {
        CallAPI.createService()
                .checkUser(email, password)
                .enqueue(object : Callback<AuthResponse> {
                    override fun onFailure(call: Call<AuthResponse>?, t: Throwable?) {
                        view.onFailure(t?.message.toString())
                    }
                    override fun onResponse(call: Call<AuthResponse>?, response: Response<AuthResponse>?) {
                        if (response?.body()?.status != "true"){
                            view.onFailure("Tài khoản hoặc mật khẩu không đúng")
                        }
                        else {
                            view.onResponse()
                        }
                    }

                })
    }
    override fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken

            // TODO(developer): send ID Token to server and validate
//            // Signed in successfully, show authenticated UI.
            push(idToken)
            view.onResponse()
            Log.w("success", "Welcome " + account.displayName)
            //Toast.makeText(this, "Welcome " + account.displayName, Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("failed", "signInResult:failed code=" + e.statusCode)
            view.onFailure("signInResult:failed code=" + e.message.toString())
        }
    }

    private fun push(idToken: String?) {
        CallAPI.createService()
                .push(idToken)
                .enqueue(object : Callback<GoogleSignInAccount> {
                    override fun onFailure(call: Call<GoogleSignInAccount>?, t: Throwable?) {
                        view.onFailure(t?.message.toString())
                    }
                    override fun onResponse(call: Call<GoogleSignInAccount>?, response: Response<GoogleSignInAccount>?) {
                        view.onResponse()
                    }

                })
    }
}