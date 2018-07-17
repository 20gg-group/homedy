package gggroup.com.baron

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class HomeActivity: AppCompatActivity(){
    private var mGoogleSignInClient: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
        val logout : Button = findViewById(R.id.logout)
        logout.setOnClickListener({signOut()})
    }
    private fun init() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken("459954702328-gfmo89cq7j1ig2ijrkn4tu8i30hjl5u8.apps.googleusercontent.com")
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }
    private fun signOut() {
        mGoogleSignInClient?.signOut()
                ?.addOnCompleteListener(this) {
                    // [START_EXCLUDE]
                    Toast.makeText(this,"logout", Toast.LENGTH_SHORT).show()
                    // [END_EXCLUDE]
                }
    }
}