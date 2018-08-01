package gggroup.com.baron.authentication.signin

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import gggroup.com.baron.api.CallAPI
import gggroup.com.baron.entities.AuthResponse
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
                        if (response?.body()?.status == "true"){
                            SignInActivity.TOKEN = response.body()?.access_token
                            view.onResponse()
                        }
                        else {
                            view.onFailure("Tài khoản hoặc mật khẩu không đúng")
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
            push(account)
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

    private fun push(account: GoogleSignInAccount) {
        CallAPI.createService()
                .push(account.displayName.toString(),account.email.toString(),"0")
                .enqueue(object : Callback<AuthResponse> {
                    override fun onFailure(call: Call<AuthResponse>?, t: Throwable?) {
                        view.onFailure(t?.message.toString())
                    }
                    override fun onResponse(call: Call<AuthResponse>?, response: Response<AuthResponse>?) {
                        view.onResponse()
                    }
                })
    }
}